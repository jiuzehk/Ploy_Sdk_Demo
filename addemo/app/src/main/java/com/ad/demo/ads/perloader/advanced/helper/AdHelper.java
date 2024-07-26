package com.ad.demo.ads.perloader.advanced.helper;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.poly.sdk.client.AdController;
import com.poly.sdk.client.AdError;
import com.poly.sdk.client.interstitial.InterstitialAdListener;
import com.poly.sdk.client.interstitial.InterstitialPreLoader;
import com.poly.sdk.client.preloader.RewardVideoPreLoader;
import com.poly.sdk.client.video.RewardAdController;
import com.poly.sdk.client.video.RewardVideoAdListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// 广告帮助类
public class AdHelper {
    private static final String TAG = "AdHelper";

    // region 1.PreLoader 预加载逻辑====================================================================

    // 激励广告-预加载对象集合-key = 广告位 value = 激励广告预加载对象
    private static Map<String, RewardVideoPreLoader> rewardVideoPreLoaderMap = new ConcurrentHashMap<>();

    // 插屏广告-预加载对象集合-key = 广告位 value = 插屏广告预加载对象
    private static Map<String, InterstitialPreLoader> interstitialPreLoaderMap = new ConcurrentHashMap<>();

    // 初始化预加载
    public static void initPreLoaderAd(Activity activity) {
        Log.i(TAG, "initPreLoaderAd ent...");
        preloadInterstitial(activity, AdPlacement.INTERSTITIAL_NCZ35400210);
        preloadRewardVideo(activity, AdPlacement.REWARDED_NCZ35400211);
    }

    // 创建一个激励广告的预加载对象
    private static RewardVideoPreLoader createPreloadRewardVideo(Activity activity, String adPlacement, RewardVideoAdListener callback) {
        Log.i(TAG, "createPreloadRewardVideo ent...  adPlacement = " + adPlacement);
        RewardVideoPreLoader rewardVideoPreLoader = RewardVideoPreLoader.get(activity, adPlacement);
        if (rewardVideoPreLoader != null) {
            rewardVideoPreLoader.setRewardVideoAdListener(new RewardVideoAdListener() {
                @Override
                public void onAdLoaded(RewardAdController rewardAdController) {
                    if (callback != null) {
                        callback.onAdLoaded(rewardAdController);
                    }
                }

                @Override
                public void onAdError(AdError adError) {
                    if (callback != null) {
                        callback.onAdError(adError);
                    }
                }

                @Override
                public void onAdExposure() {
                    if (callback != null) {
                        callback.onAdExposure();
                    }
                }

                @Override
                public void onReward() {
                    if (callback != null) {
                        callback.onReward();
                    }
                }

                @Override
                public void onAdClicked() {
                    if (callback != null) {
                        callback.onAdClicked();
                    }
                }

                @Override
                public void onAdShowError(AdError adError) {
                    if (callback != null) {
                        callback.onAdShowError(adError);
                    }
                }

                @Override
                public void onAdVideoCompleted() {
                    if (callback != null) {
                        callback.onAdVideoCompleted();
                    }
                }

                @Override
                public void onAdDismissed() {
                    if (callback != null) {
                        callback.onAdDismissed();
                    }
                }
            });
            rewardVideoPreLoader.startLoad();
        }
        return rewardVideoPreLoader;
    }

    private static void preloadRewardVideo(Activity activity, String adPlacement) {
        RewardVideoPreLoader rewardVideoPreLoader = createPreloadRewardVideo(activity, adPlacement, new RewardVideoAdListener() {
            @Override
            public void onAdLoaded(RewardAdController rewardAdController) {
                // 自定义逻辑
                Log.i(TAG, "RewardVideo onAdLoaded");
            }

            @Override
            public void onAdError(AdError adError) {
                // 自定义逻辑
                Log.i(TAG, "RewardVideo onAdError msg = " + adError.getSimpleMessage());
            }

            @Override
            public void onAdExposure() {
                // 自定义逻辑
                Log.i(TAG, "RewardVideo onAdExposure");
            }

            @Override
            public void onReward() {
                // 自定义逻辑
                Log.i(TAG, "RewardVideo onReward");
            }

            @Override
            public void onAdClicked() {
                // 自定义逻辑
                Log.i(TAG, "RewardVideo onAdClicked");
            }

            @Override
            public void onAdShowError(AdError adError) {
                // 自定义逻辑
                Log.i(TAG, "RewardVideo onAdShowError msg = " + adError.getSimpleMessage());
            }

            @Override
            public void onAdVideoCompleted() {
                // 自定义逻辑
                Log.i(TAG, "RewardVideo onAdVideoCompleted");
            }

            @Override
            public void onAdDismissed() {
                // 自定义逻辑
                Log.i(TAG, "RewardVideo onAdDismissed");
            }
        });
        rewardVideoPreLoaderMap.put(adPlacement, rewardVideoPreLoader);
    }

    // 根据广告位获取激励广告预加载对象
    public static RewardVideoPreLoader getRewardVideoPreLoader(String adPlacement) {
        return rewardVideoPreLoaderMap.get(adPlacement);
    }

    // 判断预加载-插屏广告是否可用
    public static boolean isRewardVideoReady(String adPlacement) {
        RewardVideoPreLoader rewardVideoPreLoader = getRewardVideoPreLoader(adPlacement);
        if (rewardVideoPreLoader == null) return false;
        return rewardVideoPreLoader.isReady();
    }

    // 根据激励广告位-展示激励广告
    public static void showRewardVideoAd(Activity activity, String adPlacement) {
        Log.i(TAG, "showRewardVideoAd  ent adPlacement = " + adPlacement);
        RewardVideoPreLoader rewardVideoPreLoader = getRewardVideoPreLoader(adPlacement);
        if (rewardVideoPreLoader != null) {
            boolean isRewardVideoReady = rewardVideoPreLoader.isReady();
            if (isRewardVideoReady) {
                Log.i(TAG, "showRewardVideoAd show adPlacement = " + adPlacement);
                rewardVideoPreLoader.showAd(activity);
            }else {
                Toast.makeText(activity, "Ad not ready, please try again later.", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(activity, "Ad not ready, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }


    // 创建一个插屏广告的预加载对象
    private static InterstitialPreLoader createPreloadInterstitial(Activity activity, String adPlacement, InterstitialAdListener callback) {
        Log.i(TAG, "createPreloadInterstitial ent... adPlacement = " + adPlacement);
        InterstitialPreLoader interstitialPreLoader = InterstitialPreLoader.get(activity, adPlacement);
        if (interstitialPreLoader != null) {
            interstitialPreLoader.setInterstitialAdListener(new InterstitialAdListener() {

                @Override
                public void onAdLoaded(AdController adController) {
                    if (callback != null) {
                        callback.onAdLoaded(adController);
                    }
                }

                @Override
                public void onAdError(AdError adError) {
                    if (callback != null) {
                        callback.onAdError(adError);
                    }
                }

                @Override
                public void onAdExposure() {
                    if (callback != null) {
                        callback.onAdExposure();
                    }
                }

                @Override
                public void onAdDismissed() {
                    if (callback != null) {
                        callback.onAdDismissed();
                    }
                }

                @Override
                public void onAdClicked() {
                    if (callback != null) {
                        callback.onAdClicked();
                    }
                }

                @Override
                public void onAdShowError(AdError adError) {
                    if (callback != null) {
                        callback.onAdShowError(adError);
                    }
                }
            });
            interstitialPreLoader.startLoad();
        }
        return interstitialPreLoader;
    }

    private static void preloadInterstitial(Activity activity, String adPlacement) {
        InterstitialPreLoader interstitialPreLoader = createPreloadInterstitial(activity, adPlacement, new InterstitialAdListener() {
            @Override
            public void onAdLoaded(AdController adController) {
                Log.i(TAG, "Interstitial onAdLoaded");
            }

            @Override
            public void onAdError(AdError adError) {
                Log.i(TAG, "Interstitial onAdError msg = " + adError.getSimpleMessage());
            }

            @Override
            public void onAdExposure() {
                Log.i(TAG, "Interstitial onAdExposure");
            }

            @Override
            public void onAdClicked() {
                Log.i(TAG, "Interstitial onAdClicked");
            }

            @Override
            public void onAdShowError(AdError adError) {
                Log.i(TAG, "Interstitial onAdShowError msg = " + adError.getSimpleMessage());
            }

            @Override
            public void onAdDismissed() {
                Log.i(TAG, "Interstitial onAdDismissed");
            }
        });
        interstitialPreLoaderMap.put(adPlacement, interstitialPreLoader);
    }

    // 根据广告位获取插屏广告预加载对象
    public static InterstitialPreLoader getInterstitialPreLoader(String adPlacement) {
        return interstitialPreLoaderMap.get(adPlacement);
    }

    // 判断预加载-插屏广告是否可用
    public static boolean isInterstitialReady(String adPlacement) {
        InterstitialPreLoader interstitialPreLoader = getInterstitialPreLoader(adPlacement);
        if (interstitialPreLoader == null) return false;
        return interstitialPreLoader.isReady();
    }

    // 根据插屏广告位-展示插屏广告
    public static void showInterstitialAd(Activity activity, String adPlacement) {
        Log.i(TAG, "showInterstitialAd  ent adPlacement = " + adPlacement);
        InterstitialPreLoader interstitialPreLoader = getInterstitialPreLoader(adPlacement);
        if (interstitialPreLoader != null) {
            boolean isInterstitialReady = interstitialPreLoader.isReady();
            if (isInterstitialReady) {
                Log.i(TAG, "showInterstitialAd show adPlacement = " + adPlacement);
                interstitialPreLoader.showAd(activity);
            } else {
                Toast.makeText(activity, "Ad not ready, please try again later.", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(activity, "Ad not ready, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }


    // endregion 1.PreLoader 预加载逻辑===============================================================

}
