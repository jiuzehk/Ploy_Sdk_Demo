package com.ad.demo.ads.bidding;

import android.app.Activity;

import com.poly.sdk.client.AdController;
import com.poly.sdk.client.AdError;
import com.poly.sdk.client.AdRequest;
import com.poly.sdk.client.interstitial.InterstitialAdListener;
import com.poly.sdk.client.interstitial.InterstitialPreLoader;
import com.poly.sdk.client.preloader.RewardVideoPreLoader;
import com.poly.sdk.client.video.RewardAdController;
import com.poly.sdk.client.video.RewardVideoAdListener;

public class PolyAdLoader {
    private static final String BANNERID = "CODEID";
    private static final String INTERSTITIALID = "CODEID";
    private static final String REWARDVIDEODI = "CODEID";
    private RewardVideoPreLoader rewardVideoPreLoader;
    private InterstitialPreLoader interstitialPreLoader;
    private RewardAdController rewardAdController;
    private AdController interstitialAdController;

    public static PolyAdLoader load(final Activity activity, BiddingListener biddingListener) {
        return new PolyAdLoader(activity,biddingListener);
    }

    private PolyAdLoader(Activity activity, BiddingListener biddingListener) {
        if (rewardVideoPreLoader == null) {
            rewardVideoPreLoader = loadRewardVideoAdImpl(activity,biddingListener);
        }
        if (interstitialPreLoader == null) {
            interstitialPreLoader = loadInterstitialAdImpl(activity,biddingListener);
        }
    }

    public boolean isRewardReady() {
        return rewardVideoPreLoader.isReady();
    }

    public boolean isInterstitialReady() {
        return interstitialPreLoader.isReady();
    }

    public void showReward(Activity activity) {
        if (rewardAdController != null){
            rewardAdController.showAd(activity);
        }
    }

    public void showInterstitial(Activity activity) {
        if (interstitialAdController != null){
            interstitialAdController.showAd(activity);
        }
    }

    public void sendRewardWin(Double price,String secBidder){
        if (rewardAdController != null){
            rewardAdController.sendWinNotification(price,secBidder);
        }
    }
    public void sendRewardLoss(Double firstPrice, String firstBidder, int lossReason){
        if (rewardAdController != null){
            rewardAdController.sendLossNotification(firstPrice,firstBidder,lossReason);
        }
    }
    public void sendInterstitialWin(Double price,String secBidder){
        if (interstitialAdController != null){
            interstitialAdController.sendWinNotification(price,secBidder);
        }
    }
    public void sendInterstitialLoss(Double firstPrice, String firstBidder, int lossReason){
        if (interstitialAdController != null){
            interstitialAdController.sendLossNotification(firstPrice,firstBidder,lossReason);
        }
    }


    public double getPolyRewardRevenue() {
        try {
            if (rewardAdController != null) {
                return Double.parseDouble(rewardAdController.getECPM());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double getPolyInterstitialRevenue() {
        try {
            if (interstitialAdController != null) {
                return Double.parseDouble(interstitialAdController.getECPM());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private RewardVideoPreLoader loadRewardVideoAdImpl(Activity activity, BiddingListener biddingListener) {

        RewardVideoPreLoader rewardVideoPreLoader = RewardVideoPreLoader.create(activity, REWARDVIDEODI);
        rewardVideoPreLoader.setRewardVideoAdListener(new RewardVideoAdListener() {

            @Override
            public void onAdLoaded(RewardAdController adController) {
                rewardAdController = adController;
                biddingListener.onAdLoaded(REWARDVIDEODI);
            }

            @Override
            public void onAdShowError(AdError adError) {
                biddingListener.onAdShowError(REWARDVIDEODI,adError.getErrorCode(),adError.getErrorMessage());
            }

            @Override
            public void onReward() {
                biddingListener.onReward(REWARDVIDEODI);
            }

            @Override
            public void onAdClicked() {
                biddingListener.onAdClicked(REWARDVIDEODI);
            }


            @Override
            public void onAdVideoCompleted() {
                biddingListener.onAdVideoCompleted(REWARDVIDEODI);

            }

            @Override
            public void onAdExposure() {
                AdRequest.reportRewardVideoDone();
                biddingListener.onAdExposure(REWARDVIDEODI);
            }

            @Override
            public void onAdDismissed() {
                rewardAdController = null;
                biddingListener.onAdDismissed(REWARDVIDEODI);
            }

            @Override
            public void onAdError(AdError adError) {
                biddingListener.onAdError(REWARDVIDEODI,adError.getErrorCode(),adError.getErrorMessage());
            }
        });
        rewardVideoPreLoader.startLoad();

        return rewardVideoPreLoader;
    }

    private InterstitialPreLoader loadInterstitialAdImpl(Activity activity, BiddingListener biddingListener) {
        InterstitialPreLoader interstitialPreLoader = InterstitialPreLoader.create(activity, INTERSTITIALID);
        interstitialPreLoader.setInterstitialAdListener(new InterstitialAdListener() {
            @Override
            public void onAdClicked() {
                biddingListener.onAdClicked(INTERSTITIALID);
            }

            @Override
            public void onAdShowError(AdError adError) {
                biddingListener.onAdShowError(INTERSTITIALID,adError.getErrorCode(),adError.getErrorMessage());
            }

            @Override
            public void onAdExposure() {
                AdRequest.reportInterstitialDone();
                biddingListener.onAdExposure(INTERSTITIALID);
            }

            @Override
            public void onAdDismissed() {
                interstitialAdController = null;
                biddingListener.onAdDismissed(INTERSTITIALID);

            }

            @Override
            public void onAdLoaded(AdController adController) {
                interstitialAdController = adController;
                biddingListener.onAdLoaded(INTERSTITIALID);
            }

            @Override
            public void onAdError(AdError adError) {
                biddingListener.onAdError(INTERSTITIALID,adError.getErrorCode(),adError.getErrorMessage());
            }
        });
        interstitialPreLoader.startLoad();

        return interstitialPreLoader;
    }
}
