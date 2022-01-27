package com.tapresearch.tapdemo.domain.repository;


import com.tapr.sdk.TRReward;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;

public interface RewardRepository {

  @NonNull
  Flowable<List<TRReward>> getRewards();

  void disposeRewardListener();
}
