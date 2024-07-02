package com.ad.demo.ads.bidding;

import android.app.Activity;

import com.poly.sdk.network.ainterfaces.ads.INetworkBid;

public class BiddingProcessor {

    private static PolyAdLoader polyAdLoader;
    private static MaxAdLoader maxAdLoader;

    public static BiddingProcessor loader(Activity activity,BiddingListener biddingListener) {
        return new BiddingProcessor(activity,biddingListener);
    }
    private BiddingProcessor(Activity activity,BiddingListener biddingListener){
        if (polyAdLoader == null) {
            polyAdLoader = PolyAdLoader.load(activity,biddingListener);
        }
        if (maxAdLoader == null) {
            maxAdLoader = MaxAdLoader.load(activity,biddingListener);
        }
    }


    public boolean isReadyRewardVideo() {
        return polyAdLoader.isRewardReady() || maxAdLoader.isRewardReady();
    }
    public boolean isReadyInterstitial() {
        return polyAdLoader.isInterstitialReady() || maxAdLoader.isInterstitialReady();
    }
    public void showRewardBiddingWinAd(Activity activity){
        boolean polyReady = polyAdLoader.isRewardReady();
        boolean maxReady = maxAdLoader.isRewardReady();
        if (polyReady && maxReady){
            if (polyAdLoader.getPolyRewardRevenue() >= maxAdLoader.getMaxRewardRevenue()){
                polyAdLoader.showReward(activity);
                polyAdLoader.sendRewardWin(polyAdLoader.getPolyRewardRevenue(),"");
            } else {
                maxAdLoader.showReward(activity);
                polyAdLoader.sendRewardLoss(maxAdLoader.getMaxRewardRevenue(),"AppLovin", INetworkBid.REASON_LOW_PRICE);
            }
        } else {
            if (polyReady){
                polyAdLoader.showReward(activity);
            } else if (maxReady){
                maxAdLoader.showReward(activity);
            }
        }


    }
    public void showInterstitialBiddingWinAd(Activity activity){
        boolean polyReady = polyAdLoader.isInterstitialReady();
        boolean maxReady = maxAdLoader.isInterstitialReady();
        if (polyReady && maxReady){
            if (polyAdLoader.getPolyInterstitialRevenue() >= maxAdLoader.getMaxInterstitialRevenue()){
                polyAdLoader.showInterstitial(activity);
                polyAdLoader.sendInterstitialWin(polyAdLoader.getPolyRewardRevenue(),"");
            }else {
                maxAdLoader.showInterstitial(activity);
                polyAdLoader.sendInterstitialLoss(maxAdLoader.getMaxRewardRevenue(),"AppLovin", INetworkBid.REASON_LOW_PRICE);
            }
        }else {
            if (polyReady){
                polyAdLoader.showInterstitial(activity);
            }else if (maxReady){
                maxAdLoader.showInterstitial(activity);
            }
        }


    }


}
