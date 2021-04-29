package com.alexanderdudnik.briteam;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.alexanderdudnik.briteam.data.BarcodesList;
import com.alexanderdudnik.briteam.data.DataGenerator;
import com.alexanderdudnik.briteam.data.DataItem;
import com.alexanderdudnik.briteam.models.BarcodeListModel;
import com.alexanderdudnik.briteam.presenters.BarcodeListPresenter;
import com.alexanderdudnik.briteam.views.BarcodeListAdapter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private BarcodeListPresenter presenter;
    private BarcodeListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //create presenter using this activity
        presenter = new BarcodeListPresenter(this);
        //create adapter for list and handle a presenter for it
        adapter = new BarcodeListAdapter(presenter);

        //find Recycler view  and setup it with layout and adapter
        RecyclerView rv_list = findViewById(R.id.rv_list);
        rv_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        rv_list.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    @Override
    protected void onResume() {
        super.onResume();

        //if application go foreground subscribe for data emitter and pass it to adapter and list
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
}