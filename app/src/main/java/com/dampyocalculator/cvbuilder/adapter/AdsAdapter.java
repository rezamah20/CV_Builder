package com.dampyocalculator.cvbuilder.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewTreeObserver;
import android.view.WindowMetrics;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.dampyocalculator.cvbuilder.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Arrays;


public class AdsAdapter {
        Activity activity;
        Context context;

    private static final String AD_BANNER_ID = "ca-app-pub-3940256099942544/6300978111";
    private AdView adView;
    private FrameLayout adContainerView;

   public AdsAdapter (Activity activity, Context ctx){
       this.activity = activity;
       this.context = ctx;
   }

   public void loadbanner(){
       MobileAds.setRequestConfiguration(new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("BCAD6EB3C56B8812C539F8C6D5CBC900")).build());
       adContainerView = activity.findViewById(R.id.adView);
       adView = new AdView(context);
       adContainerView.addView(adView);
       showBanner();
   }

   public void showBanner (){
       adView.setAdUnitId(AD_BANNER_ID);
       AdSize adSize = getAdSize();
       adView.setAdSize(adSize);
       AdRequest adRequest = new AdRequest.Builder().build();
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("TAG", "onAdFailedToLoad: "+loadAdError);
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdSwipeGestureClicked() {
                super.onAdSwipeGestureClicked();
            }
        });


       adView.loadAd(adRequest);
   }

    private AdSize getAdSize() {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);


        WindowMetrics windowMetrics = null;
        Rect bounds = null;
        int adWidth = 0;
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            windowMetrics = activity.getWindowManager().getCurrentWindowMetrics();
            bounds = windowMetrics.getBounds();
            float adWidthPixels = adContainerView.getWidth();
            if (adWidthPixels == 0f) {
                adWidthPixels = bounds.width();
            }
            float density = activity.getResources().getDisplayMetrics().density;
            adWidth = (int) (adWidthPixels / density);
        }else {
            float widthPixels = outMetrics.widthPixels;
            float density = outMetrics.density;
            adWidth = (int) (widthPixels / density);
        }

        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

}
