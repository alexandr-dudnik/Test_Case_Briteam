package com.alexanderdudnik.briteam.models;

import com.alexanderdudnik.briteam.data.DataGenerator;
import com.alexanderdudnik.briteam.data.DataItem;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//****************************
//class for Barcode List Model
//contains an emitter of barcode data for list
//emits every 5 seconds a new value
//****************************
public class BarcodeListModel {
    private final Observable<DataItem> emitter;

    public BarcodeListModel() {
        //create RX observable running on IO and observing results on ui thread
        emitter = Observable.interval(5, TimeUnit.SECONDS)
                .map(aLong -> DataGenerator.INSTANCE.generateItem())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    //getter for emitter
    public Observable<DataItem> getEmitter() {
        return emitter;
    }
}
