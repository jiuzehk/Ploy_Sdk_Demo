package com.ad.demo.ads.perloader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ad.demo.R;
import com.poly.sdk.client.AdController;
import com.poly.sdk.client.AdError;
import com.poly.sdk.client.interstitial.InterstitialAdListener;
import com.poly.sdk.client.interstitial.InterstitialPreLoader;
import com.poly.sdk.client.preloader.PreLoader;
import com.poly.sdk.client.preloader.RewardVideoPreLoader;
import com.poly.sdk.client.video.RewardAdController;
import com.poly.sdk.client.video.RewardVideoAdListener;

public class PerLoaderTestActivity extends AppCompatActivity {
    private static final String TAG = "PerLoaderTestActivity";
    private Button start_preloader;  //开启预加载
    private Button get_reward_video_adController;  //展示激励视频广告
    private Button get_interstitials_adController;  // 展示插屏广告

    private RewardVideoPreLoader rewardVideoPreLoader;
    private InterstitialPreLoader interstitialPreLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader_test_ad);

        start_preloader = findViewById(R.id.start_preloader);
        get_reward_video_adController = findViewById(R.id.get_reward_video_adController);
        get_interstitials_adController = findViewById(R.id.get_interstitials_adController);


        start_preloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "start preload...");
                preloadRewardVideo();
                preloadInterstitial();
            }
        });

        get_reward_video_adController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isReady = rewardVideoPreLoader.isReady();

                Log.i(TAG, "get_reward_video_adController isReady = " + isReady);

                if (isReady) {
                    rewardVideoPreLoader.showAd(PerLoaderTestActivity.this);
                }

            }
        });

        get_interstitials_adController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "get_interstitials_adController...");

                boolean isReady = interstitialPreLoader.isReady();

                Log.i(TAG, "get_interstitials_adController isReady = " + isReady);

                if (isReady) {
                    interstitialPreLoader.showAd(PerLoaderTestActivity.this);
                }

            }
        });

    }


    private void preloadRewardVideo() {
        rewardVideoPreLoader = RewardVideoPreLoader.create(this, "NCZ00000004");
        rewardVideoPreLoader.setRewardVideoAdListener(new RewardVideoAdListener() {
            @Override
            public void onAdClicked() {
                Log.i(TAG,"onAdClicked");
            }

            @Override
            public void onAdLoaded(RewardAdController rewardAdController) {
                Log.i(TAG,"onAdLoaded = " + rewardAdController.getExtData().toString());
            }

            @Override
            public void onAdShowError(AdError adError) {
                Log.i(TAG,"onAdShowError msg = " + adError.getSimpleMessage());
            }

            @Override
            public void onReward() {
                Log.i(TAG,"onReward");
            }

            @Override
            public void onAdVideoCompleted() {
                Log.i(TAG,"onAdVideoCompleted");
            }

            @Override
            public void onAdExposure() {
                Log.i(TAG,"onAdExposure");
            }

            @Override
            public void onAdDismissed() {
                Log.i(TAG,"onAdDismissed");
            }

            @Override
            public void onAdError(AdError adError) {
                Log.i(TAG,"onAdError msg = " + adError.getSimpleMessage());
            }
        });
        rewardVideoPreLoader.startLoad();
    }

    private void preloadInterstitial() {
        interstitialPreLoader = InterstitialPreLoader.create(this, "NCZ00000003");
        interstitialPreLoader.setInterstitialAdListener(new InterstitialAdListener() {
            @Override
            public void onAdClicked() {
                Log.i(TAG,"onAdClicked");
            }

            @Override
            public void onAdShowError(AdError adError) {
                Log.i(TAG,"onAdShowError msg = " + adError.getSimpleMessage());
            }

            @Override
            public void onAdExposure() {
                Log.i(TAG,"onAdExposure");
            }

            @Override
            public void onAdDismissed() {
                Log.i(TAG,"onAdDismissed");
            }

            @Override
            public void onAdLoaded(AdController adController) {
                Log.i(TAG,"onAdLoaded");
            }

            @Override
            public void onAdError(AdError adError) {
                Log.i(TAG,"onAdError msg = " + adError.getSimpleMessage());
            }
        });

        interstitialPreLoader.startLoad();
    }

}
