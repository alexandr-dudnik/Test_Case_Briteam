package com.alexanderdudnik.briteam.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.alexanderdudnik.briteam.R;
import com.alexanderdudnik.briteam.data.ListItem;
import com.alexanderdudnik.briteam.presenters.BarcodeListPresenter;
import com.alexanderdudnik.briteam.ui.activities.MainActivity;
import com.alexanderdudnik.briteam.views.BarcodeListAdapter;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMain#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMain extends Fragment {
    private MainActivity root;

    public FragmentMain() {
        // Required empty public constructor
    }

    public static FragmentMain newInstance() {
        return new FragmentMain();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = (MainActivity)getActivity();

        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_main, container, false);

        if (root!=null) {
            //find Recycler view  and setup it with layout and adapter
            RecyclerView rv_list = mView.findViewById(R.id.rv_list);
            rv_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            BarcodeListAdapter adapter = root.getAdapter();
            BarcodeListPresenter presenter = root.getPresenter();

            rv_list.setAdapter(adapter);
            rv_list.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
                @Override
                public void onTouchEvent(@NonNull @NotNull RecyclerView rv, @NonNull @NotNull MotionEvent e) {
                    View child = rv.findChildViewUnder(e.getX(), e.getY());
                    if (child != null) {
                        int pos = rv.getChildAdapterPosition(child);
                        ListItem item = adapter.getItem(adapter.getItemId(pos));
                        presenter.handleItemClick(item.getData());

                        if (root.isDualMode()){
                            root.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.details, FragmentScanner.newInstance(item.getData()))
                                    .commit();
                        }else {
                            Bundle params = new Bundle();
                            params.putString(FragmentScanner.ITEM_BARCODE, item.getData().getBarcode());
                            params.putString(FragmentScanner.ITEM_TYPE, item.getData().getType());

                            root.getNavController().navigate(R.id.scannerFragment, params);
                        }
                    }
                }

                @Override
                public boolean onInterceptTouchEvent(@NonNull @NotNull RecyclerView rv, @NonNull @NotNull MotionEvent e) {
                    return true;
                }
            });
        }

        return mView;
    }

}