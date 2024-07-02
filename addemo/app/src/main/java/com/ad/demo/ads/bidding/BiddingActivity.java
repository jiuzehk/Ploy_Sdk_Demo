package com.ad.demo.ads.bidding;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.ad.demo.R;

import java.util.Timer;
import java.util.TimerTask;

public class BiddingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bidding);
        BiddingProcessor loader = BiddingProcessor.loader(this, new BiddingListener() {
            @Override
            public void onAdClicked(String slotId) {

            }

            @Override
            public void onAdShowError(String slotId, int errorCode, String errorMsg) {

            }

            @Override
            public void onAdExposure(String slotId) {

            }

            @Override
            public void onAdDismissed(String slotId) {

            }

            @Override
            public void onAdLoaded(String slotId) {

            }

            @Override
            public void onAdError(String slotId, int errorCode, String errorMsg) {

            }

            @Override
            public void onReward(String slotId) {

            }

            @Override
            public void onAdVideoCompleted(String slotId) {

            }
        });
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (loader.isReadyRewardVideo()){
                    loader.showRewardBiddingWinAd(BiddingActivity.this);
                }
            }
        },5000);

    }
}