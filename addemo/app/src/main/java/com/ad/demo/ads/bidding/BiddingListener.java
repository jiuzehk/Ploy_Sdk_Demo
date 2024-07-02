package com.ad.demo.ads.bidding;

public interface BiddingListener {
    void onAdClicked(String slotId);
    void onAdShowError(String slotId,int errorCode ,String errorMsg);
    void onAdExposure(String slotId);
    void onAdDismissed(String slotId);
    void onAdLoaded(String slotId);
    void onAdError(String slotId,int errorCode ,String errorMsg);
    void onReward(String slotId);
    void onAdVideoCompleted(String slotId);
}
