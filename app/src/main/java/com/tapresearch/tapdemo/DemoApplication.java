package com.tapresearch.tapdemo;

import android.app.Application;

import com.tapr.sdk.TapResearch;

public class DemoApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        TapResearch.configure("<TAPRESEARCH_API_TOKEN>", this);
        TapResearch.getInstance().setUniqueUserIdentifier("<USER_IDENTIFIER>");
    }
}

