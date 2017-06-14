package com.tapresearch.tapdemo;

import android.app.Application;

import com.tapr.sdk.TapResearch;

public class DemoApplication extends Application {


    private static final String TAPRESEARCH_API_TOKEN = "aa901cb55a7ec436a8d1371429708df2";

    @Override
    public void onCreate() {
        super.onCreate();
        TapResearch.configure(TAPRESEARCH_API_TOKEN, this);
        TapResearch.getInstance().setUniqueUserIdentifier("userssdfsd@gmail.com");
    }
}

