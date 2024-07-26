package com.ad.demo.ads.perloader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ad.demo.R;
import com.ad.demo.ads.perloader.advanced.PerLoaderAdvancedTestActivity;
import com.ad.demo.ads.perloader.normal.PerLoaderTestActivity;

// 预加载普通用法
public class PerLoaderModeActivity extends AppCompatActivity {
    private Button normal_preloader;  // 普通用法
    private Button advanced_preloader;  // 高阶用法

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader_mode);

        normal_preloader = findViewById(R.id.normal_preloader);
        advanced_preloader = findViewById(R.id.advanced_preloader);


        normal_preloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerLoaderModeActivity.this, PerLoaderTestActivity.class);
                startActivity(intent);
            }
        });

        advanced_preloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerLoaderModeActivity.this, PerLoaderAdvancedTestActivity.class);
                startActivity(intent);
            }
        });
    }
}
