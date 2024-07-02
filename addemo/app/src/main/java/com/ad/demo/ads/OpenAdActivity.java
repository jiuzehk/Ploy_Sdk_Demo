package com.ad.demo.ads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.demo.MainActivity;
import com.ad.demo.R;
import com.poly.sdk.client.AdController;
import com.poly.sdk.client.AdError;
import com.poly.sdk.client.AdRequest;
import com.poly.sdk.client.splash.SplashAdListener;

public class OpenAdActivity extends AppCompatActivity {
    private static final String TAG = OpenAdActivity.class.getName();
    private Context context;
    private AdRequest splashAdRequest;
    private LinearLayout info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_ad);
        context = this;
        Button load = this.findViewById(R.id.load_splash);
        Button show = this.findViewById(R.id.show_splash);
        info = this.findViewById(R.id.splash_request_info);
        splashAdRequest = new AdRequest.Builder(this)
                .setCodeId("NCZ00000001") // 广告位ID
                .setTimeoutMs(5 * 1000)   // 设置显示秒数
                .build();
        SplashAdListener splashAdListener = new SplashAdListener() {
            @Override
            public void onAdLoaded(AdController adController) {
                Log.i(TAG, "onAdLoaded: ");
                showAdInfo("onAdLoaded");
                Toast.makeText(context,"onAdLoaded",Toast.LENGTH_LONG).show();
                show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adController.isReady()){
                            adController.showAd(OpenAdActivity.this);
                        }
                    }
                });
            }

            @Override
            public void onAdError(AdError adError) {
                Log.i(TAG, "onAdError: "+adError.getErrorCode()+" "+adError.getErrorMessage());
                showAdInfo("onAdError: "+adError.getErrorCode()+" "+adError.getErrorMessage());
            }
            @Override
            public void onAdShowError(AdError adError) {
                Log.i(TAG, "onAdShowError: ");
                showAdInfo("onAdShowError");
            }
            @Override
            public void onAdClicked() {
                Log.i(TAG, "onAdClicked: ");
                showAdInfo("onAdClicked");
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
                startActivity(new Intent(OpenAdActivity.this, MainActivity.class));
                finish();
            }
        };
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                splashAdRequest.loadSplashAd(splashAdListener);
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
        if (splashAdRequest != null) {
            splashAdRequest.recycle();
            splashAdRequest = null;

        }
    }
}