package com.tapresearch.tapdemo.ui.placements;

import static android.view.View.inflate;

import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tapr.sdk.PlacementCustomParameters;
import com.tapr.sdk.SurveyListener;
import com.tapr.sdk.TRPlacement;
import com.tapr.sdk.TapEventListener;
import com.tapresearch.tapdemo.R;
import com.tapresearch.tapdemo.domain.model.PlacementItemModel;

import java.util.Calendar;
import java.util.List;

public class PlacementListAdapter extends RecyclerView.Adapter<PlacementItemViewHolder> {

    private List<PlacementItemModel> placementItemModelList;
    private SharedPreferences preferences;

    public PlacementListAdapter(List<PlacementItemModel> placementItemModelList, SharedPreferences preferences) {
        this.placementItemModelList = placementItemModelList;
        this.preferences = preferences;

    }

    @NonNull
    @Override
    public PlacementItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflate(viewGroup.getContext(), R.layout.placement_list_item, null);
        return new PlacementItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacementItemViewHolder placementViewHolder, int position) {
        final PlacementItemModel placementItemModel = placementItemModelList.get(position);
        final TRPlacement placement = placementItemModel.getPlacement();
        String placementId = getPlacementId(placementItemModel, placement);

        Calendar calendar = getCalendar(placementItemModel);

        StringBuilder placementTextStringBuilder = new StringBuilder("placements: ")
                .append(placementId)
                .append("\n")
                .append("refreshed on: ")
                .append(calendar.getTime())
                .append("\n")
                .append("has offer: ");

        if (placement == null) {
            placementTextStringBuilder.append("false");
        } else {
            placementTextStringBuilder.append(placement.isSurveyWallAvailable());
        }

        if (placement != null && !placement.isSurveyWallAvailable()) {
            placementTextStringBuilder.append(placement.isSurveyWallAvailable())
                    .append("\n");

        }
        if (placement != null) {
            placementTextStringBuilder.append("\n")
                    .append("is event available: ")
                    .append(placement.isEventAvailable());
        }
        placementViewHolder.setText(placementTextStringBuilder.toString());
        placementViewHolder.itemView.setOnClickListener(v -> {
            if (placement == null) return;
            if (placement.isEventAvailable()) {
                PlacementCustomParameters.PlacementParameter parameter = null;
                try {
                    parameter = new PlacementCustomParameters.PlacementParameter.Builder().key("name").value("tap-android").build();
                } catch (PlacementCustomParameters.PlacementCustomParametersException e) {
                    e.printStackTrace();
                }
                PlacementCustomParameters parameters = new PlacementCustomParameters();
                try {
                    parameters.addParameter(parameter);
                } catch (PlacementCustomParameters.PlacementCustomParametersException e) {
                    e.printStackTrace();
                }


                placement.displayEvent(new TapEventListener() {
                    @Override
                    public void onTapEventOpened() {
                        Toast.makeText(v.getContext(), "event opened", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onTapEventDismissed() {

                    }
                });
            } else if (placement.isSurveyWallAvailable()) {
                placement.showSurveyWall(new SurveyListener() {
                    @Override
                    public void onSurveyWallOpened() {
                        Toast.makeText(v.getContext(), "survey opened", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSurveyWallDismissed() {
                        Toast.makeText(v.getContext(), "survey closed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private String getPlacementId(PlacementItemModel placementItemModel, TRPlacement placement) {
        String placementId;
        if (placement == null) {
            placementId = placementItemModel.getPlacementId();
        } else {
            placementId = placement.getPlacementIdentifier();
        }
        return placementId;
    }

    @NonNull
    private Calendar getCalendar(PlacementItemModel placementItemModel) {
        long placementTime = placementItemModel.getPlacementTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(placementTime);
        return calendar;
    }

    public void setPlacementItemModelList(List<PlacementItemModel> placementItemModelList) {
        this.placementItemModelList = placementItemModelList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return placementItemModelList.size();
    }
}
