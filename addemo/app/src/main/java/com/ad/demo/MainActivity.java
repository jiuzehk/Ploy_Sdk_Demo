package com.ad.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.demo.ads.BannerAdActivity;
import com.ad.demo.ads.InterstitialAdActivity;
import com.ad.demo.ads.NativeAdActivity;
import com.ad.demo.ads.OpenAdActivity;
import com.ad.demo.ads.RewardedAdActivity;
import com.ad.demo.ads.TemplateAdActivity;
import com.ad.demo.ads.bidding.BiddingActivity;
import com.ad.demo.ads.perloader.PerLoaderTestActivity;
import com.ad.demo.ui.SimpleDividerItemDecoration;
import com.tools.sdk_debug.AdDebugHelp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView adTypeRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adTypeRecycler = this.findViewById(R.id.ad_type_recycler);
        List<String> dataList = new ArrayList<>();
        dataList.add("Open AD");
        dataList.add("Banner AD");
        dataList.add("Interstitial AD");
        dataList.add("Native AD");
        dataList.add("ExpressView AD");
        dataList.add("Rewarded AD");
        dataList.add("PerLoader MODE");
        dataList.add("Bidding");
        dataList.add("Open Test Tools");
        adTypeRecycler.setLayoutManager(new LinearLayoutManager(this));
        adTypeRecycler.setAdapter(new AdTypeAdapter(dataList, new AdTypeAdapter.OnItemClickListener() {

            private Intent intent;

            @Override
            public void onItemClick(String item) {
                switch (item) {
                    case "Open AD":
                        intent = new Intent(MainActivity.this, OpenAdActivity.class);
                        startActivity(intent);
                        break;
                    case "Banner AD":
                        intent = new Intent(MainActivity.this, BannerAdActivity.class);
                        startActivity(intent);
                        break;
                    case "Interstitial AD":
                        intent = new Intent(MainActivity.this, InterstitialAdActivity.class);
                        startActivity(intent);
                        break;
                    case "Native AD":
                        intent = new Intent(MainActivity.this, NativeAdActivity.class);
                        startActivity(intent);
                        break;
                    case "ExpressView AD":
                        intent = new Intent(MainActivity.this, TemplateAdActivity.class);
                        startActivity(intent);
                        break;
                    case "Rewarded AD":
                        intent = new Intent(MainActivity.this, RewardedAdActivity.class);
                        startActivity(intent);
                        break;
                    case "PerLoader MODE":
                        intent = new Intent(MainActivity.this, PerLoaderTestActivity.class);
                        startActivity(intent);
                        break;
                    case "Bidding":
                        intent = new Intent(MainActivity.this, BiddingActivity.class);
                        startActivity(intent);
                        break;
                    case "Open Test Tools":
                        AdDebugHelp.getInstance().init(MainActivity.this).startActivity(MainActivity.this);
                        break;
                    default:
                        break;
                }
            }
        }));
        adTypeRecycler.addItemDecoration(new SimpleDividerItemDecoration(this, android.R.color.black));


    }

}


class AdTypeAdapter extends RecyclerView.Adapter<AdTypeAdapter.AdViewHolder> {

    private List<String> dataList;
    private OnItemClickListener listener; // 点击事件监听器

    public interface OnItemClickListener {
        void onItemClick(String item);
    }
    public AdTypeAdapter(List<String> dataList, OnItemClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @Override
    public AdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ad_type_item_layout, parent, false);
        return new AdViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AdViewHolder holder, int position) {
        String item = dataList.get(position);
        holder.textView.setText(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class AdViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public AdViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.ad_type_name);
        }
    }
}