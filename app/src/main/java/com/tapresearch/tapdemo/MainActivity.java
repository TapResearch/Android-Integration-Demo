package com.tapresearch.tapdemo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tapr.sdk.TapResearch;
import com.tapr.sdk.TapResearchOnRewardListener;
import com.tapr.sdk.TapResearchSurveyListener;

import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private ProgressDialog mProgressDialog;
    private Button mBtnSurveys;
    private TextView mTxtNoSurveys;

    private int mAnimationDuration;


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

        mAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        if (TapResearch.getInstance().isSurveyAvailable()) {
            showSurveyAvailable();
        } else {
            showSearchForSurvey();
        }


        TapResearch.getInstance().setOnRewardListener(new TapResearchOnRewardListener() {
            @Override
            public void onDidReceiveReward(int rewardAmount, String transactionIdentifier, String currencyName, int payoutEvent) {
                Log.i(TAG, String.format(Locale.getDefault(), "reward amount - %d, identifier - %s currency - %s, payout event - %d",
                        rewardAmount, transactionIdentifier, currencyName, payoutEvent));

            }

        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        TapResearch.getInstance().setSurveyListener(mTapResearchSurveyListener);
    }


    @Override
    protected void onPause() {
        super.onPause();
        TapResearch.getInstance().setSurveyListener(null);
    }

    private final TapResearchSurveyListener mTapResearchSurveyListener = new TapResearchSurveyListener() {
        @Override
        public void onSurveyAvailable() {
            showSurveyAvailable();
        }

        @Override
        public void onSurveyNotAvailable() {
            showNoSurveysAvailable();
        }

        @Override
        public void onSurveyModalOpened() {
            Log.i(TAG, "Surveys are visible");
        }

        @Override
        public void onSurveyModalClosed() {
            Log.i(TAG, "Surveys are closed");
        }
    };

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
        TapResearch.getInstance().showSurvey();
    }
}
