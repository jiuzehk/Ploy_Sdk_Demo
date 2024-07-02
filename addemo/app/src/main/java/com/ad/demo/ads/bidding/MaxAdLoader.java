package com.ad.demo.ads.bidding;

import android.app.Activity;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.sdk.AppLovinSdk;

import java.util.concurrent.TimeUnit;

public class MaxAdLoader {
    private static final String BANNERID = "APPLOVIN_CODE_ID";
    private static final String INTERSTITIALID = "APPLOVIN_CODE_ID";
    private static final String REWARDVIDEODI = "APPLOVIN_CODE_ID";
    private MaxRewardedAd maxRewardedAd;
    private MaxInterstitialAd maxInterstitialAd;
    private MaxAd rewardMaxAd;
    private MaxAd interstitialMaxAd;
    private int rewardRetryAttempt;
    private int interstitialRetryAttempt;

    public static MaxAdLoader load(final Activity activity, BiddingListener biddingListener){
        return new MaxAdLoader(activity,biddingListener);
    }
    private MaxAdLoader(Activity activity, BiddingListener biddingListener){
        if (maxRewardedAd == null){
            maxRewardedAd = loadRewardVideoAdImpl(activity,biddingListener);
        }
        if (maxInterstitialAd == null){
            maxInterstitialAd = loadInterstitialAdImpl(activity,biddingListener);
        }
    }

    public boolean isRewardReady() {
        return maxRewardedAd.isReady();
    }

    public boolean isInterstitialReady() {
        return maxInterstitialAd.isReady();
    }
    public void showReward(Activity activity) {
        if (maxRewardedAd != null && maxRewardedAd.isReady()){
            maxRewardedAd.showAd(activity);
        }
    }

    public void showInterstitial(Activity activity) {
        if (maxInterstitialAd != null && maxInterstitialAd.isReady()){
            maxInterstitialAd.showAd(activity);
        }
    }

    public double getMaxRewardRevenue(){
        if (rewardMaxAd != null){
            return rewardMaxAd.getRevenue();
        }
        return 0;
    }

    public double getMaxInterstitialRevenue(){
        if (interstitialMaxAd != null){
            return interstitialMaxAd.getRevenue();
        }
        return 0;
    }

    private MaxRewardedAd loadRewardVideoAdImpl(Activity activity, BiddingListener biddingListener) {
        initApplovin(activity);
        MaxRewardedAd rewardedAd = MaxRewardedAd.getInstance(REWARDVIDEODI, activity);
        rewardedAd.setListener(new MaxRewardedAdListener() {
            @Override
            public void onUserRewarded(@NonNull MaxAd maxAd, @NonNull MaxReward maxReward) {
                biddingListener.onReward(REWARDVIDEODI);
            }

            @Override
            public void onAdLoaded(@NonNull MaxAd maxAd) {
                rewardMaxAd = maxAd;
                rewardRetryAttempt = 0;
                biddingListener.onAdLoaded(REWARDVIDEODI);
            }

            @Override
            public void onAdDisplayed(@NonNull MaxAd maxAd) {

            }

            @Override
            public void onAdHidden(@NonNull MaxAd maxAd) {
                rewardMaxAd = null;
                rewardedAd.loadAd();
                biddingListener.onAdDismissed(REWARDVIDEODI);
            }

            @Override
            public void onAdClicked(@NonNull MaxAd maxAd) {
                biddingListener.onAdClicked(REWARDVIDEODI);

            }

            @Override
            public void onAdLoadFailed(@NonNull String s, @NonNull MaxError maxError) {
                rewardRetryAttempt++;
                long delayMillis = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, rewardRetryAttempt)));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rewardedAd.loadAd();
                    }
                }, delayMillis);
                biddingListener.onAdError(REWARDVIDEODI,maxError.getCode(),maxError.getMessage());
            }

            @Override
            public void onAdDisplayFailed(@NonNull MaxAd maxAd, @NonNull MaxError maxError) {
                rewardedAd.loadAd();
                biddingListener.onAdShowError(REWARDVIDEODI,maxError.getCode(),maxError.getMessage());
            }
        });
        rewardedAd.loadAd();
        return rewardedAd;
    }
    private MaxInterstitialAd loadInterstitialAdImpl(Activity activity, BiddingListener biddingListener) {
        initApplovin(activity);
        MaxInterstitialAd interstitialAd = new MaxInterstitialAd(INTERSTITIALID, activity);
        interstitialAd.setListener(new MaxAdListener() {
            @Override
            public void onAdLoaded(@NonNull MaxAd maxAd) {
                interstitialMaxAd = maxAd;
                interstitialRetryAttempt = 0;
                biddingListener.onAdLoaded(INTERSTITIALID);
            }

            @Override
            public void onAdDisplayed(@NonNull MaxAd maxAd) {

            }

            @Override
            public void onAdHidden(@NonNull MaxAd maxAd) {
                interstitialMaxAd = null;
                interstitialAd.loadAd();
                biddingListener.onAdDismissed(INTERSTITIALID);
            }

            @Override
            public void onAdClicked(@NonNull MaxAd maxAd) {
                biddingListener.onAdClicked(INTERSTITIALID);
            }

            @Override
            public void onAdLoadFailed(@NonNull String s, @NonNull MaxError maxError) {
                interstitialRetryAttempt++;
                long delayMillis = TimeUnit.SECONDS.toMillis((long)Math.pow(2, Math.min( 6, interstitialRetryAttempt)));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        interstitialAd.loadAd();
                    }
                }, delayMillis );
                biddingListener.onAdError(INTERSTITIALID,maxError.getCode(),maxError.getMessage());
            }

            @Override
            public void onAdDisplayFailed(@NonNull MaxAd maxAd, @NonNull MaxError maxError) {
                interstitialAd.loadAd();
                biddingListener.onAdShowError(INTERSTITIALID,maxError.getCode(),maxError.getMessage());
            }
        });
        interstitialAd.loadAd();
        return interstitialAd;
    }
    private void initApplovin(Activity activity){
        AppLovinSdk appLovinSdk = AppLovinSdk.getInstance("AppLovin_sdk_key",
                AppLovinSdk.getInstance(activity).getSettings(), activity);
    }
}
