package com.tapresearch.tapdemo.domain.repository;

import com.tapr.sdk.TRReward;
import com.tapr.sdk.TapResearch;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class RewardRepositoryImpl implements RewardRepository {

  private BehaviorSubject<List<TRReward>> rewardBehaviorSubject = BehaviorSubject.create();

  @Override
  public @NonNull Flowable<List<TRReward>> getRewards() {
    if (TapResearch.getInstance() != null) {
      rewardBehaviorSubject = BehaviorSubject.create();
      TapResearch.getInstance()
          .setRewardCollectionListener(
              rewards -> {
                rewardBehaviorSubject.onNext(rewards);
                rewardBehaviorSubject.onComplete();
              });
    }
    return rewardBehaviorSubject.toFlowable(BackpressureStrategy.LATEST);
  }

  @Override
  public void disposeRewardListener() {
    if (TapResearch.getInstance() != null)
      TapResearch.getInstance().setRewardCollectionListener(null);
  }
}
