package com.tapresearch.tapdemo.domain.repository;

import com.tapresearch.tapdemo.domain.model.PlacementItemModel;

import java.util.Map;

import io.reactivex.rxjava3.core.Flowable;

public interface TapResearchRepository {
    void initialize();

    Flowable<Map<String, PlacementItemModel>> placementFlowable();

}
