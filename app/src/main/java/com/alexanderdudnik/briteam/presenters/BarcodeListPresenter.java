package com.alexanderdudnik.briteam.presenters;

import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alexanderdudnik.briteam.data.DataItem;
import com.alexanderdudnik.briteam.models.BarcodeListModel;
import com.alexanderdudnik.briteam.views.BarcodeListAdapter;

import io.reactivex.disposables.Disposable;

//****************************
//Presenter class for barcode list
//
//****************************
public class BarcodeListPresenter {
    private final BarcodeListModel model;
    final private AppCompatActivity rootView;
    private Disposable stream;
    private BarcodeListAdapter adapter;


    public BarcodeListPresenter(AppCompatActivity context) {
        model = new BarcodeListModel();
        rootView = context;
    }

    //start to observe an emitter and update data in given adapter.
    public void start(BarcodeListAdapter pAdapter) {
        adapter = pAdapter;
        if (stream == null)
                stream = model.getEmitter()
                        .doOnNext(item -> {
                            if (adapter !=null){
                                adapter.addItem(item);
                            }
                        })
                        .subscribe();
    }

    //stops receiving data - cancel subscription
    public void stop(){
        if (stream!=null && !stream.isDisposed()) {
            stream.dispose();
            stream = null;
            adapter = null;
        }
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
