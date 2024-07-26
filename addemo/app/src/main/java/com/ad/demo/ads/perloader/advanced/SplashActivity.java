package com.ad.demo.ads.perloader.advanced;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.ad.demo.MainActivity;
import com.ad.demo.R;
import com.ad.demo.ads.perloader.advanced.helper.AdHelper;
import com.poly.sdk.client.AdController;
import com.poly.sdk.client.AdError;
import com.poly.sdk.client.AdRequest;
import com.poly.sdk.client.splash.SplashAdListener;

public class SplashActivity extends Activity {
    private static final String TAG = "SPLASH";
    private static final int AD_REQUEST_TIMEOUT = 3000; // 5秒超时
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // 启动广告请求
        loadOpenAd();

        AdHelper.initPreLoaderAd(this);

        // 设置超时处理
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                // 超时或广告加载失败，跳转到主界面
                goToMainActivity();
            }
        };
        if (handler != null) handler.postDelayed(runnable, AD_REQUEST_TIMEOUT);
    }

    private AdRequest openAdRequest;

    private void loadOpenAd() {
        openAdRequest = new AdRequest.Builder(this).setCodeId("NCZ35400209").setTimeoutMs(5 * 1000).build();
        openAdRequest.loadSplashAd(new SplashAdListener() {
            @Override
            public void onAdLoaded(AdController adController) {
                Log.e(TAG, "onAdLoaded: " + adController);
                removeCallbacks();
                if (adController != null && adController.isReady())
                    adController.showAd(SplashActivity.this);
            }

            @Override
            public void onAdShowError(AdError adError) {
                Log.e(TAG, "onAdShowError: " + adError.getErrorMessage());
            }

            @Override
            public void onAdError(AdError adError) {
                Log.e(TAG, "onAdError: " + adError.getErrorMessage());
                removeCallbacks();
                goToMainActivity();
            }

            @Override
            public void onAdClicked() {
                Log.e(TAG, "onAdClicked: ");
            }

            @Override
            public void onAdExposure() {
                Log.e(TAG, "onAdExposure: ");
            }

            @Override
            public void onAdDismissed() {
                Log.e(TAG, "onAdDismissed: ");
                goToMainActivity();
            }
        });
    }

    private void removeCallbacks() {
        if (handler != null) {
            handler.removeCallbacks(runnable);
            handler = null;
        }
    }

    private void goToMainActivity() {
        Log.e(TAG, "goToMainActivity: ");
        // 跳转到主界面
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        // 取消超时处理
        removeCallbacks();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 确保在Activity销毁时取消所有回调
        removeCallbacks();
        if (openAdRequest != null) {
            openAdRequest.recycle();
            openAdRequest = null;
        }
    }
}
