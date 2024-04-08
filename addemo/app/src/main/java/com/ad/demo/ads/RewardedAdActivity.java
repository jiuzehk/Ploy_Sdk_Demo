package com.ad.demo.ads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.demo.R;
import com.poly.sdk.client.AdError;
import com.poly.sdk.client.AdRequest;
import com.poly.sdk.client.video.RewardAdController;
import com.poly.sdk.client.video.RewardVideoAdListener;

public class RewardedAdActivity extends AppCompatActivity {

    private static final String TAG = RewardedAdActivity.class.getName();
    private AdRequest rewardedRequest;
    private Context context;
    private LinearLayout info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_rewarded_ad);
        Button load = this.findViewById(R.id.load_reward);
        Button show = this.findViewById(R.id.show_reward);
        info = this.findViewById(R.id.reward_request_info);
        rewardedRequest = new AdRequest.Builder(this)
                .setCodeId("NCZ00000004")    // 广告位ID
                .build();
        RewardVideoAdListener rewardVideoAdListener = new RewardVideoAdListener() {
            @Override
            public void onAdError(AdError adError) {
                showAdInfo("onAdError: "+adError.getErrorCode()+" "+adError.getErrorMessage());
                Log.i(TAG, "onAdError: "+adError.getErrorCode()+" "+adError.getErrorMessage());
            }

            @Override
            public void onAdClicked() {
                Log.i(TAG, "onAdClicked: ");
                showAdInfo("onAdClicked");
            }

            @Override
            public void onAdLoaded(RewardAdController rewardAdController) {
                Toast.makeText(context,"onAdLoaded",Toast.LENGTH_LONG).show();
                Log.i(TAG, "onAdLoaded: ");
                showAdInfo("onAdLoaded");
                show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (rewardAdController.isReady()){
                            rewardAdController.showAd(RewardedAdActivity.this);
                        }
                    }
                });
            }

            @Override
            public void onReward() {
                Log.i(TAG, "onReward: ");
                showAdInfo("onReward");
            }

            @Override
            public void onAdVideoCompleted() {
                Log.i(TAG, "onAdVideoCompleted: ");
                showAdInfo("onAdVideoCompleted");
            }

            @Override
            public void onAdExposure() {
                Log.i(TAG, "onAdExposure: ");
                showAdInfo("onAdExposure");
            }

            @Override
            public void onAdDismissed() {
                Log.i(TAG, "onAdDismissed: ");
                showAdInfo("onAdDismissed");
            }
        };
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rewardedRequest.loadRewardVideoAd(rewardVideoAdListener);
                showAdInfo("Request...");
            }
        });
    }
    private void showAdInfo(String s) {
        TextView textView = new TextView(this);
        textView.setText(s);
        info.addView(textView);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (rewardedRequest != null) {
            rewardedRequest.recycle();
            rewardedRequest = null;
        }
    }
}