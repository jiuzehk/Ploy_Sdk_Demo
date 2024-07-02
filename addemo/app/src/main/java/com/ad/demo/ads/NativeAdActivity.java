package com.ad.demo.ads;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.demo.R;
import com.poly.sdk.client.AdError;
import com.poly.sdk.client.AdRequest;
import com.poly.sdk.client.NativeAdData;
import com.poly.sdk.client.NativeAdListener;
import com.poly.sdk.client.NativeAdViewBinder;
import com.poly.sdk.client.feedlist.FeedListNativeAdListener;

import java.util.List;

public class NativeAdActivity extends AppCompatActivity {
    private static final String TAG = NativeAdActivity.class.getName();
    private Context context;
    private AdRequest nativeAdRequest;
    private LinearLayout info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ad);
        context = this;
        Button load = this.findViewById(R.id.load_native);
        Button show = this.findViewById(R.id.show_native);
        info = this.findViewById(R.id.native_request_info);
        nativeAdRequest = new AdRequest.Builder(this)
                .setCodeId("NCZ00000006") // 广告位ID
                .setAdRequestCount(1)     // 请求广告数量
                .build();
        FeedListNativeAdListener feedListNativeAdListener = new FeedListNativeAdListener() {
            @Override
            public void onAdError(AdError adError) {
                Log.i(TAG, "onAdError: " + adError.getErrorCode() + " " + adError.getErrorMessage());
                showAdInfo("onAdError: " + adError.getErrorCode() + " " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(List<NativeAdData> list) {
                Log.i(TAG, "onAdLoaded: ");
                showAdInfo("onAdLoaded");
                Toast.makeText(context, "onAdLoaded", Toast.LENGTH_LONG).show();
                show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bindNativeView(list);

                    }
                });
            }
        };

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nativeAdRequest.loadFeedListNativeAd(feedListNativeAdListener);
                showAdInfo("Request...");
            }
        });
    }

    private void bindNativeView(List<NativeAdData> list) {
        if (list == null || list.isEmpty()) return;
        for (int i = 0; i < list.size(); i++) {
            NativeAdData nativeAdData = list.get(i);
            nativeAdData.attach(NativeAdActivity.this);     // 绑定显示类型 activity / Dialog
            NativeAdViewBinder jhNativeAdViewBinder = createNativeAdView(); // 广告渲染的ViewGroup
            FrameLayout nativeAdLayout = findViewById(R.id.native_ad_layout); // 本地容器
            // 设置LayoutParams
            ViewGroup.LayoutParams layoutParams =
                    new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            // 绑定
            View view = nativeAdData.bindView(nativeAdLayout, layoutParams, jhNativeAdViewBinder, new NativeAdListener() {
                @Override
                public void onADExposed() {
                    Log.i(TAG, "onADExposed: ");
                    showAdInfo("onADExposed");
                }

                @Override
                public void onADClicked() {
                    Log.i(TAG, "onADClicked: ");
                    showAdInfo("onADClicked");
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
            });
        }
    }

    private NativeAdViewBinder createNativeAdView() {
        NativeAdViewBinder binder = new NativeAdViewBinder.Builder(R.layout.native_custom_ad_view)
                .setTitleTextViewId(R.id.title_text_view)
                .setBodyTextViewId(R.id.body_text_view)
                .setAdvertiserTextViewId(R.id.advertiser_text_view)
                .setIconImageViewId(R.id.icon_image_view)
                .setMediaContentViewGroupId(R.id.media_view_container)
                .setOptionsContentViewGroupId(R.id.options_view)
                .setStarRatingContentViewGroupId(R.id.star_rating_view)
                .setCallToActionButtonId(R.id.cta_button)
                .build();
        return binder;

    }
    private void showAdInfo(String s) {
        TextView textView = new TextView(this);
        textView.setText(s);
        info.addView(textView);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (nativeAdRequest!= null) {
            nativeAdRequest.recycle();
            nativeAdRequest= null;

        }
    }
}