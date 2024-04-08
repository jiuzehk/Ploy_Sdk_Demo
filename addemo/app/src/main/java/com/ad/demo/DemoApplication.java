package com.ad.demo;

import android.app.Application;
import android.util.Log;

import com.poly.sdk.client.AdRequest;
import com.poly.sdk.client.SdkConfiguration;


public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("DemoApplication", "onCreate enter");
        SdkConfiguration sdkConfiguration = new SdkConfiguration.Builder()
                .setPrintLog(BuildConfig.DEBUG)
                .build();
        AdRequest.init(this,sdkConfiguration);

    }
}
