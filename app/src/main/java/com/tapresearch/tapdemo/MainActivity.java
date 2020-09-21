package com.tapresearch.tapdemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tapr.sdk.PlacementListener;
import com.tapr.sdk.RewardListener;
import com.tapr.sdk.SurveyListener;
import com.tapr.sdk.TRPlacement;
import com.tapr.sdk.TRReward;
import com.tapr.sdk.TapResearch;

import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private ProgressDialog mProgressDialog;
    private Button mBtnSurveys;
    private TextView mTxtNoSurveys;

    private int mAnimationDuration;
    private TRPlacement mPlacement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setIcon(R.drawable.tap_logo);
        }
        mBtnSurveys = (Button) findViewById(R.id.btn_show_surveys);
        mTxtNoSurveys = (TextView) findViewById(R.id.txt_no_surveys);

        TapResearch.getInstance().setDebugMode(true);

        mAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
        showSearchForSurvey();
        TapResearch.getInstance().initPlacement("<PLACEMENT_IDENTIFIER>", new PlacementListener() {
            @Override
            public void onPlacementReady(TRPlacement placement) {
                mPlacement = placement;
                if (mPlacement.getPlacementCode() != TRPlacement.PLACEMENT_CODE_SDK_NOT_READY) {
                    if (mPlacement.isSurveyWallAvailable()) {
                        showSurveyAvailable();
                    } else {
                        showNoSurveysAvailable();
                    }
                }
            }
        });

        TapResearch.getInstance().setRewardListener(new RewardListener() {
            @Override
            public void onDidReceiveReward(TRReward reward) {
                Log.i(TAG, String.format(Locale.getDefault(), "reward amount - %d, identifier - %s currency - %s, payout event - %d placement identifier - %s",
                        reward.getRewardAmount(), reward.getTransactionIdentifier(), reward.getCurrencyName(), reward.getPayoutEvent(), reward.getPlacementIdentifier()));
            }
        });

    }

    private void showSearchForSurvey() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, getText(R.string.surveys),
                    getText(R.string.looking_for_surveys), true, false);
        }
        mBtnSurveys.setVisibility(GONE);
    }

    private void showSurveyAvailable() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        if (mBtnSurveys.getVisibility() == GONE) {
            mBtnSurveys.setAlpha(0f);
            mBtnSurveys.setVisibility(VISIBLE);
            mBtnSurveys.animate()
                    .alpha(1f)
                    .setDuration(mAnimationDuration)
                    .setListener(null);
        }
    }

    private void showNoSurveysAvailable() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
        mTxtNoSurveys.setAlpha(0f);
        mTxtNoSurveys.setVisibility(VISIBLE);
        mTxtNoSurveys.animate()
                .alpha(1f)
                .setDuration(mAnimationDuration)
                .setListener(null);
    }

    @SuppressWarnings("UnusedParameters")
    public void onSurveyClick(View view) {
        mPlacement.showSurveyWall(new SurveyListener() {
            @Override
            public void onSurveyWallOpened() {
                Log.d(TAG, "SurveyWall opened");
            }

            @Override
            public void onSurveyWallDismissed() {
                Log.d(TAG, "SurveyWall dismissed");
            }
        });
    }
}
