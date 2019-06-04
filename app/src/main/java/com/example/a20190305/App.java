package com.example.a20190305;

import android.app.Application;

import com.example.a20190305.retrofit.Rest;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Rest.init();
        FlowManager.init(new FlowConfig.Builder(this).build());
    }
}
