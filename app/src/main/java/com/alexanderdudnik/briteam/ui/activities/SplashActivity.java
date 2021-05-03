package com.alexanderdudnik.briteam.ui.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alexanderdudnik.briteam.App;
import com.alexanderdudnik.briteam.R;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        App.setCurrentActivity(this.getClass());
    }
}