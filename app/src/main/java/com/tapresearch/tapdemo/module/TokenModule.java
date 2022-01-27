package com.tapresearch.tapdemo.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class TokenModule {

    @Provides
    @Named("api_token")
    String providesApiToken() {
        return "<API-TOKEN>";
    }

    @Provides
    @Named("user_identifier")
    String providesUserIdentifier() {
        return "<USER-IDENTIFIER>";
    }
}
