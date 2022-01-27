package com.tapresearch.tapdemo.module;


import com.tapresearch.tapdemo.domain.repository.RewardRepository;
import com.tapresearch.tapdemo.domain.repository.RewardRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RewardsModule {

    @Provides
    @Singleton
    public RewardRepository providesRewardRepository() {
        return new RewardRepositoryImpl();
    }
}
