package com.tapresearch.tapdemo.domain.model;

import androidx.annotation.Nullable;

import com.tapr.sdk.TRPlacement;

public class PlacementItemModel {
    private String placementId;
    private TRPlacement placement;
    private long placementTime;

    public PlacementItemModel(String placementId, TRPlacement placement, long placementTime) {
        this.placementId = placementId;
        this.placement = placement;
        this.placementTime = placementTime;
    }

    public String getPlacementId() {
        return placementId;
    }

    public TRPlacement getPlacement() {
        return placement;
    }

    public long getPlacementTime() {
        return placementTime;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        return placementId.equals(((PlacementItemModel) obj).getPlacementId());
    }
}
