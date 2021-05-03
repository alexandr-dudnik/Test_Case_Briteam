package com.alexanderdudnik.briteam.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;

import com.alexanderdudnik.briteam.App;
import com.alexanderdudnik.briteam.R;
import com.alexanderdudnik.briteam.presenters.BarcodeListPresenter;
import com.alexanderdudnik.briteam.views.BarcodeListAdapter;

import static androidx.navigation.Navigation.findNavController;

public class MainActivity extends AppCompatActivity {
    private BarcodeListPresenter presenter;
    private BarcodeListAdapter adapter;
    private NavController navController;
    private boolean isDualMode;
    private FrameLayout detailsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.setCurrentActivity(this.getClass());

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        //create adapter for list and handle a presenter for it
        adapter = new BarcodeListAdapter();

        //create presenter using this activity
        presenter = new BarcodeListPresenter(this, adapter);

        navController = findNavController(this, R.id.nav_host_fragment);

        detailsFragment = findViewById(R.id.details);
        isDualMode = (detailsFragment != null);
        if (isDualMode){
            NavBackStackEntry mBack = navController.getPreviousBackStackEntry();
            if (mBack != null){
                navController.popBackStack();
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    @Override
    protected void onResume() {
        super.onResume();

        presenter.start(adapter);
    }

    @Override
    protected void onPause() {
        //if application go background unsubscribe from emitter
        presenter.stop();

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //stop receiving data on application destroy
        presenter.stop();
        presenter = null;
        adapter = null;

        super.onDestroy();
    }


    public BarcodeListPresenter getPresenter() {
        return presenter;
    }

    public BarcodeListAdapter getAdapter() {
        return adapter;
    }

    public NavController getNavController() {
        return navController;
    }

    public boolean isDualMode() {
        return isDualMode;
    }
}
