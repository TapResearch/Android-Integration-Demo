package com.tapresearch.tapdemo.viewmodels;

import androidx.core.util.Pair;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.tapresearch.tapdemo.domain.repository.RewardRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class RewardsViewModel extends ViewModel implements LifecycleObserver {
  private final MutableLiveData<String> rewardMutableLiveData = new MutableLiveData<>();
  private final RewardRepository rewardRepository;
  private final CompositeDisposable disposable;

  @Inject
  public RewardsViewModel(RewardRepository rewardRepository) {
    this.rewardRepository = rewardRepository;
    disposable = new CompositeDisposable();
  }

  public void getRewards() {
    disposable.add(
        rewardRepository
            .getRewards()
            .flatMapIterable(trReward -> trReward)
            .map(trReward -> new Pair<>(trReward.getRewardAmount(), trReward.getCurrencyName()))
            .reduce(
                (integerStringPair, integerStringPair2) ->
                    new Pair<>(
                        integerStringPair.first + integerStringPair2.first,
                        integerStringPair.second))
            .subscribe(
                integerStringPair ->
                    rewardMutableLiveData.postValue(
                        integerStringPair.first + " " + integerStringPair.second)));
  }

  public MutableLiveData<String> getRewardMutableLiveData() {
    return rewardMutableLiveData;
  }

  public void resetRewards() {
    rewardRepository.disposeRewardListener();
    disposable.clear();
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    disposable.dispose();
  }
}
