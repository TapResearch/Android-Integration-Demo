package com.tapresearch.tapdemo;

import android.app.Application;

import com.tapresearch.tapdemo.domain.repository.TapResearchRepository;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MainApplication extends Application {

    @Inject
    TapResearchRepository tapResearchRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        tapResearchRepository.initialize();
    }
}
