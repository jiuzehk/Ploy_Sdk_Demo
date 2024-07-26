package com.ad.demo.ads.perloader.advanced;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ad.demo.R;
import com.ad.demo.ads.perloader.advanced.helper.AdHelper;
import com.ad.demo.ads.perloader.advanced.helper.AdPlacement;

// 预加载高阶用法
public class PerLoaderAdvancedTestActivity extends AppCompatActivity {
    private static final String TAG = "PerLoaderTestActivity";
    private Button get_reward_video_adController;  //展示激励视频广告
    private Button get_interstitials_adController;  // 展示插屏广告

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader_advanced_test_ad);

        get_reward_video_adController = findViewById(R.id.get_reward_video_adController);
        get_interstitials_adController = findViewById(R.id.get_interstitials_adController);


        get_reward_video_adController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "get_reward_video_adController...");
                AdHelper.showRewardVideoAd(PerLoaderAdvancedTestActivity.this, AdPlacement.REWARDED_NCZ35400211);
            }
        });

        get_interstitials_adController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "get_interstitials_adController...");
                AdHelper.showInterstitialAd(PerLoaderAdvancedTestActivity.this, AdPlacement.INTERSTITIAL_NCZ35400210);
            }
        });
    }
}
