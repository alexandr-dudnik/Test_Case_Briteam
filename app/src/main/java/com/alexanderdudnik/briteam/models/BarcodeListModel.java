package com.alexanderdudnik.briteam.models;

import android.content.Intent;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.alexanderdudnik.briteam.App;
import com.alexanderdudnik.briteam.data.BarcodesList;
import com.alexanderdudnik.briteam.data.DataGenerator;
import com.alexanderdudnik.briteam.data.DataItem;
import com.alexanderdudnik.briteam.ui.activities.MainActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//****************************
//class for Barcode List Model
//contains an emitter of barcode data for list
//emits every 5 seconds a new value
//****************************
public class BarcodeListModel {
    private final Observable<DataItem> emitter;
    private Disposable stream;
    private final MutableLiveData<BarcodesList> dataItemList = new MutableLiveData<>();
    private Disposable observer;

    public BarcodeListModel() {
        //create RX observable running on IO and observing results on ui thread
        emitter = Observable.interval(5, TimeUnit.SECONDS)
                .map(aLong -> DataGenerator.INSTANCE.generateItem())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }


    public void subscribe(){
        if (observer == null || observer.isDisposed()){
            observer = emitter.doOnNext(dataItem -> {
                if (App.getCurrentActivity() != MainActivity.class){
                    Intent intent = new Intent(App.getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    App.getContext().startActivity(intent);
                }
                BarcodesList cur = dataItemList.getValue();
                if (cur == null) cur  = new BarcodesList();
                cur.addItem(dataItem);
                dataItemList.postValue(cur);
            })
            .subscribe();
        }

    }

    public void unsubscribe(){
        observer.dispose();
    }

    public void addDataChangeListener(Observer<? super BarcodesList> observer){
        dataItemList.observeForever(observer);
    }
}
