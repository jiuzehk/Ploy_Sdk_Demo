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
import com.poly.sdk.client.AdError;
import com.poly.sdk.client.AdRequest;
import com.poly.sdk.client.feedlist.AdView;
import com.poly.sdk.client.feedlist.AdViewListener;
import com.poly.sdk.client.feedlist.FeedListViewAdListener;

import java.util.List;

public class TemplateAdActivity extends AppCompatActivity {
    private static final String TAG = TemplateAdActivity.class.getName();
    private Context context;
    private AdRequest templateAdRequest;
    private LinearLayout info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_ad);
        context = this;
        Button load = this.findViewById(R.id.load_template);
        Button show = this.findViewById(R.id.show_template);
        info = this.findViewById(R.id.template_request_info);
        templateAdRequest = new AdRequest.Builder(this)
                .setCodeId("NCZ00000005") // 广告位ID
                .setAdRequestCount(1)     // 请求广告数量
                .build();
        FeedListViewAdListener feedListViewAdListener = new FeedListViewAdListener() {
            @Override
            public void onAdError(AdError adError) {
                Log.i(TAG, "onAdError: "+adError.getErrorCode()+" "+adError.getErrorMessage());
                showAdInfo("onAdError: "+adError.getErrorCode()+" "+adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(List<AdView> list) {
                Log.i(TAG, "onAdLoaded: ");
                showAdInfo("onAdLoaded");
                Toast.makeText(context,"onAdLoaded",Toast.LENGTH_LONG).show();
                show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showTemplateAd(list);
                    }
                });
            }
        };
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                templateAdRequest.loadFeedListAd(feedListViewAdListener);
                showAdInfo("Request...");
            }
        });
    }

    private void showTemplateAd(List<AdView> list) {
        if (list == null || list.size() == 0) return;
        for (AdView adView : list) {
            adView.attach(TemplateAdActivity.this);   // 绑定显示类型 activity / Dialog
            adView.render(new AdViewListener() {
                @Override
                public void onAdClicked() {
                    Log.i(TAG, "onAdClicked");
                    showAdInfo("onAdClicked");
                }
                @Override
                public void onAdDismissed() {
                    Log.i(TAG, "onAdDismissed");
                    showAdInfo("onAdDismissed");
                }
                @Override
                public void onAdExposed() {
                    Log.i(TAG, "onAdExposed");
                    showAdInfo("onAdExposed");
                }
                @Override
                public void onAdShowError(AdError adError) {
                    Log.i(TAG, "onAdShowError: ");
                    showAdInfo("onAdShowError");
                }
                @Override
                public void onRenderSuccess() {
                    Log.i(TAG, "onRenderSuccess");
                    showAdInfo("onRenderSuccess");
                    View view = adView.getView();
                    FrameLayout templateAdLayout = findViewById(R.id.template_ad_layout);
                    templateAdLayout.addView(view);// 加载到本地ViewGroup中
                }
                @Override
                public void onRenderFailed(AdError adError) {
                    Log.i(TAG, "onRenderFailed");
                    showAdInfo("onRenderFailed");
                }
            });
        }
    }
    private void showAdInfo(String s) {
        TextView textView = new TextView(this);
        textView.setText(s);
        info.addView(textView);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (templateAdRequest != null) {
            templateAdRequest.recycle();
            templateAdRequest = null;
        }
    }
}