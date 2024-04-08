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
import com.poly.sdk.client.AdController;
import com.poly.sdk.client.AdError;
import com.poly.sdk.client.AdRequest;
import com.poly.sdk.client.interstitial.InterstitialAdListener;

public class InterstitialAdActivity extends AppCompatActivity {
    private static final String TAG = InterstitialAdActivity.class.getName();
    private Context context;
    private AdRequest interstitialRequest;
    private LinearLayout info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_ad);
        context = this;

        Button load = this.findViewById(R.id.load_inster);
        Button show = this.findViewById(R.id.show_inster);
        info = this.findViewById(R.id.inster_request_info);
        interstitialRequest = new AdRequest.Builder(this)
                .setCodeId("NCZ00000003")    // 广告位ID
                .build();
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onAdError(AdError adError) {
                Log.i(TAG, "onAdError: "+adError.getErrorCode() +" "+adError.getErrorMessage());
                showAdInfo("onAdError: "+adError.getErrorCode() +" "+adError.getErrorMessage());
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
            public void onAdLoaded(AdController adController) {
                Log.i(TAG, "onAdLoaded: ");
                showAdInfo("onAdLoaded");
                Toast.makeText(context,"onAdLoaded",Toast.LENGTH_LONG).show();
                show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adController.isReady()){
                            adController.showAd(InterstitialAdActivity.this);
                        }
                    }
                });
            }
        };
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interstitialRequest.loadInterstitialAd(interstitialAdListener);
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
        if (interstitialRequest != null) {
            interstitialRequest.recycle();
            interstitialRequest = null;

        }
    }
}