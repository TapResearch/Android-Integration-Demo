package com.tapresearch.tapdemo.domain.repository;

import android.app.Application;
import android.content.Context;

import com.tapr.sdk.PlacementEventListener;
import com.tapr.sdk.TRPlacement;
import com.tapr.sdk.TapResearch;
import com.tapresearch.tapdemo.domain.model.PlacementItemModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;


public class TapResearchRepositoryImpl implements TapResearchRepository {

    private final BehaviorSubject<Map<String, PlacementItemModel>> placementMapBehaviorSubject;
    private final Map<String, PlacementItemModel> placementItemModelMap = new HashMap<>();
    private final String apiToken;
    private final String userIdentifier;
    private final Context applicationContext;

    public TapResearchRepositoryImpl(@Named("api_token") String apiToken,
                                     @Named("user_identifier") String userIdentifier,
                                     @ApplicationContext Context applicationContext
    ) {
        this.apiToken = apiToken;
        this.userIdentifier = userIdentifier;
        this.applicationContext = applicationContext;
        placementMapBehaviorSubject = BehaviorSubject.create();
    }

    @Override
    public void initialize() {
        TapResearch.configure(apiToken, (Application) applicationContext, new PlacementEventListener() {
            @Override
            public void placementReady(TRPlacement placement) {
                String placementId = placement.getPlacementIdentifier();
                placementItemModelMap.put(placementId, new PlacementItemModel(placementId, placement, System.currentTimeMillis()));
                placementMapBehaviorSubject.onNext(placementItemModelMap);
            }

            @Override
            public void placementUnavailable(String placementId) {
                placementItemModelMap.put(placementId, new PlacementItemModel(placementId, null, System.currentTimeMillis()));
                placementMapBehaviorSubject.onNext(placementItemModelMap);
            }
        });
        TapResearch.getInstance().setUniqueUserIdentifier(userIdentifier);
    }

    @Override
    public Flowable<Map<String, PlacementItemModel>> placementFlowable() {
        if (!placementItemModelMap.isEmpty())
            placementMapBehaviorSubject.onNext(placementItemModelMap);
        return placementMapBehaviorSubject.toFlowable(BackpressureStrategy.LATEST);
    }


}
