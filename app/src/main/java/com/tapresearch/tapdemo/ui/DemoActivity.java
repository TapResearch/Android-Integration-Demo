package com.tapresearch.tapdemo.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tapresearch.tapdemo.R;
import com.tapresearch.tapdemo.ui.placements.PlacementListAdapter;
import com.tapresearch.tapdemo.viewmodels.PlacementViewModel;
import com.tapresearch.tapdemo.viewmodels.RewardsViewModel;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DemoActivity extends AppCompatActivity {

    private static final String TAG = DemoActivity.class.getSimpleName();
    private RewardsViewModel rewardsViewModel;

    @Override
    protected void onCreate(Bundle savedInstances) {
        super.onCreate(savedInstances);
        setContentView(R.layout.activity_demo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        final PlacementViewModel placementViewModel = initViewModels();
        final PlacementListAdapter placementListAdapter = initPlacementListAdapter(recyclerView);

        placementViewModel
                .receivePlacementListLiveData()
                .observe(this, placementListAdapter::setPlacementItemModelList);

        rewardsViewModel
                .getRewardMutableLiveData()
                .observe(
                        this,
                        rewardString -> {
                            new AlertDialog.Builder(DemoActivity.this)
                                    .setTitle("Rewards!")
                                    .setMessage(rewardString)
                                    .setNeutralButton("OK", (dialog, which) -> {
                                    })
                                    .show();
                        });

        placementViewModel.getRecentPlacements();
    }

    @NonNull
    private PlacementViewModel initViewModels() {
        ViewModelProvider viewModelProvider = new ViewModelProvider(this);
        PlacementViewModel placementViewModel = viewModelProvider.get(PlacementViewModel.class);
        rewardsViewModel = viewModelProvider.get(RewardsViewModel.class);
        return placementViewModel;
    }

    @NonNull
    private PlacementListAdapter initPlacementListAdapter(RecyclerView recyclerView) {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        final PlacementListAdapter placementListAdapter =
                new PlacementListAdapter(new ArrayList<>(), preferences);
        recyclerView.setAdapter(placementListAdapter);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        return placementListAdapter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        rewardsViewModel.getRewards();
    }

    @Override
    protected void onPause() {
        super.onPause();
        rewardsViewModel.resetRewards();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
