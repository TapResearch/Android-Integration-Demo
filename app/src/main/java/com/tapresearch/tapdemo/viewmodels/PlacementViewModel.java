package com.tapresearch.tapdemo.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tapresearch.tapdemo.domain.model.PlacementItemModel;
import com.tapresearch.tapdemo.domain.repository.TapResearchRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class PlacementViewModel extends ViewModel {

    private final TapResearchRepository tapResearchRepository;
    private final MutableLiveData<List<PlacementItemModel>> placementListLiveData = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public PlacementViewModel(TapResearchRepository tapResearchRepository) {
        this.tapResearchRepository = tapResearchRepository;

    }


    public MutableLiveData<List<PlacementItemModel>> receivePlacementListLiveData() {
        return placementListLiveData;
    }

    public void getRecentPlacements() {
        disposables.add(tapResearchRepository
                .placementFlowable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(value -> {
                    placementListLiveData.setValue(new ArrayList<>(value.values()));
                })
                .subscribe());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.dispose();
    }
}
