package com.alexanderdudnik.briteam.presenters;

import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.alexanderdudnik.briteam.App;
import com.alexanderdudnik.briteam.data.BarcodesList;
import com.alexanderdudnik.briteam.data.DataItem;
import com.alexanderdudnik.briteam.models.BarcodeListModel;
import com.alexanderdudnik.briteam.views.BarcodeListAdapter;

import io.reactivex.disposables.Disposable;

//****************************
//Presenter class for barcode list
//
//****************************
public class BarcodeListPresenter {
    final private AppCompatActivity rootView;
    private BarcodeListAdapter adapter;
    private final BarcodeListModel mModel;


    public BarcodeListPresenter(AppCompatActivity context, BarcodeListAdapter pAdapter) {
        rootView = context;
        adapter = pAdapter;
        mModel = App.getModel();
        mModel.addDataChangeListener(barcodesList -> {
            adapter.setList(barcodesList);
        });
    }

    //start to observe an emitter and update data in given adapter.
    public void start(BarcodeListAdapter pAdapter) {
        adapter = pAdapter;
        mModel.subscribe();
    }

    //stops receiving data - cancel subscription
    public void stop(){
        mModel.unsubscribe();
    }

    //handler for list-item click - used from view holder
    //update title, subtitle and color of toolbar
    public void handleItemClick(DataItem data) {
        if (rootView!=null) {
            ActionBar toolbar = rootView.getSupportActionBar();
            if (toolbar!=null){
                toolbar.setTitle(data.getBarcode());
                toolbar.setSubtitle(data.getType());
                toolbar.setBackgroundDrawable(new ColorDrawable(data.getColorValue()));
            }
        }
    }
}
