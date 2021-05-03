package com.alexanderdudnik.briteam.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alexanderdudnik.briteam.R;
import com.alexanderdudnik.briteam.data.DataItem;
import com.alexanderdudnik.briteam.ui.activities.MainActivity;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentScanner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentScanner extends Fragment {
    MainActivity root;

    public final static String ITEM_BARCODE = "item_barcode";
    public final static String ITEM_TYPE = "item_type";

    private String barcode = "";
    private String type = "";
    private BarcodeDetector detector;
    private CameraSource cameraSource;
    private SurfaceView surfaceView;

    public FragmentScanner() {
        // Required empty public constructor
    }

    public static FragmentScanner newInstance(DataItem item) {
        FragmentScanner fragment = new FragmentScanner();
        Bundle args = new Bundle();
        args.putString(ITEM_BARCODE, item.getBarcode());
        args.putString(ITEM_TYPE, item.getType());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            barcode = getArguments().getString(ITEM_BARCODE);
            type = getArguments().getString(ITEM_TYPE);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = (MainActivity)getActivity();
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_scanner, container, false);

        surfaceView = mView.findViewById(R.id.sv_barcode);

        initCamera();
//        AppCompatButton mScanBtn = mView.findViewById(R.id.btn_scan_barcode);
//        mScanBtn.setOnClickListener(v -> {
//
//            if (root != null){
//                Toast.makeText(root, "Scanned barcode matches", Toast.LENGTH_SHORT).show();
//            }
//        });

        return mView;
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    public void onResume() {
        super.onResume();
        initCamera();
    }

    private void initCamera() {
        detector = new BarcodeDetector.Builder(root)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(root, detector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920, 1080)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(root, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        cameraSource.start(surfaceView.getHolder());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    ActivityCompat.requestPermissions(root, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
                }

            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(@NotNull Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0 && barcodes.valueAt(0).isRecognized) {
                    String mBarcode = barcodes.valueAt(0).displayValue;
                    if (mBarcode.equals(barcode)){
                        Toast.makeText(root, "Scanned barcode matches", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(root, "Scanned barcode doesn't match", Toast.LENGTH_LONG).show();
                    }
                    root.getSupportFragmentManager().popBackStack();
                }
            }
        });

    }
}