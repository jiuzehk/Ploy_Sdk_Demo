package com.ad.demo.ads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.demo.R;
import com.poly.sdk.client.AdController;
import com.poly.sdk.client.AdError;
import com.poly.sdk.client.AdRequest;
import com.poly.sdk.client.BannerAdSize;
import com.poly.sdk.client.banner.BannerAdListener;
import com.unity3d.services.banners.api.Banner;

public class BannerAdActivity extends AppCompatActivity {
    private static final String TAG = BannerAdActivity.class.getName();
    public Context context;
    private AdRequest bannerRequest;
    private LinearLayout info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_ad);
        context = this;

        FrameLayout bannerView = this.findViewById(R.id.banner_view);
        Button load = this.findViewById(R.id.load_banner);
        Button show = this.findViewById(R.id.show_banner);
        info = this.findViewById(R.id.banner_request_info);
        bannerRequest = new AdRequest.Builder(this)
                .setCodeId("NCZ00000002")    // 广告位ID
                .setAdContainer(bannerView)  // 展示banner的View
                .setBannerSize(BannerAdSize.BANNER_W_320_H_50) // banner大小
                .build();
        BannerAdListener bannerAdListener = new BannerAdListener() {
            @Override
            public void onAdLoaded(AdController adController) {
                showAdInfo("onAdLoaded");
                Toast.makeText(context,"onAdLoaded",Toast.LENGTH_LONG).show();
                Log.i(TAG, "onAdLoaded: ");
                show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adController.isReady()){
                            adController.showAd(bannerView);
                        }
                    }
                });
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
            }

            @Override
            public void onAdError(AdError adError) {
                showAdInfo("onAdError: "+adError.getErrorCode()+" "+adError.getErrorMessage());
                Log.i(TAG, "onAdError: "+adError.getErrorCode()+" "+adError.getErrorMessage());
            }
        };
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bannerRequest.loadBannerAd(bannerAdListener);
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
        if (bannerRequest != null) {
            bannerRequest.recycle();
            bannerRequest = null;
        }
    }
}