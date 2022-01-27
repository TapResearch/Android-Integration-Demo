package com.tapresearch.tapdemo.module;

import android.content.Context;


import com.tapresearch.tapdemo.domain.repository.TapResearchRepository;
import com.tapresearch.tapdemo.domain.repository.TapResearchRepositoryImpl;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class TapResearchModule {

    @Provides
    @Singleton
    public TapResearchRepository providesTapResearchInstance(
            @Named("api_token") String token,
            @Named("user_identifier") String userIdentifier,
            @ApplicationContext Context applicationContext) {
        return new TapResearchRepositoryImpl(token, userIdentifier, applicationContext);
    }
}
