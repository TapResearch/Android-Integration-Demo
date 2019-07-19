package com.tapresearch.tapdemo;

import android.app.Application;

import com.tapr.sdk.TapResearch;

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TapResearch.configure("d425e0ebe93444912da2e121ae20c446", this);
        TapResearch.getInstance().setUniqueUserIdentifier("<USER_IDENTIFIER>");
    }
}

