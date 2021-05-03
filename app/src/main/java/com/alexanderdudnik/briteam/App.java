package com.alexanderdudnik.briteam;

import android.app.Application;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.alexanderdudnik.briteam.models.BarcodeListModel;

public  class App extends Application {
    private static Context context;
    private static BarcodeListModel model;
    private static Class currentActivity;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        model = new BarcodeListModel();

        model.subscribe();


    }

    public static BarcodeListModel getModel() {
        return model;
    }

    public static Context getContext() {
        return context;
    }

    public static Class getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(Class currentActivity) {
        App.currentActivity = currentActivity;
    }
}
