package com.dampyocalculator.cvbuilder.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowMetrics;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.dampyocalculator.cvbuilder.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.util.Arrays;


public class AdsAdapter {
        Activity activity;
        Context context;

    private static final String AD_BANNER_ID = "ca-app-pub-3940256099942544/6300978111";
    private AdView adView;
    private FrameLayout adContainerView;
    private InterstitialAd mInterstitialAd;


    public AdsAdapter (Activity activity, Context ctx){
       this.activity = activity;
       this.context = ctx;
   }

   public void intent(Class cls, String id){
       Intent intent = new Intent(context, cls);
       intent.putExtra("id", id);
       context.startActivity(intent);
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
                MobileAds.initialize(context, new OnInitializationCompleteListener() {
                    @Override
                    public void onInitializationComplete(InitializationStatus initializationStatus) {
                        Log.d("Admob", "Initialize Admob Success");
                        loadbanner();
                    }
                });
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

    public void loadInter(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("interstitialAd", loadAdError.toString());
                mInterstitialAd = null;
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                mInterstitialAd = interstitialAd;
                Log.i("interstitialAd", "onAdLoaded");
            }
        });
    }
    private void InterstitialCallBack(Class cls, String id){
        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdClicked() {
                Log.d("interstitialAd", "Ad was clicked.");
                loadInter();
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d("interstitialAd", "Ad dismissed fullscreen content.");
                mInterstitialAd = null;
                loadInter();
                intent(cls, id);
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
                Log.e("interstitialAd", String.valueOf(adError));
                mInterstitialAd = null;
                loadInter();
                intent(cls, id);
            }

            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d("interstitialAd", "Ad recorded an impression.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                loadInter();
                Log.d("interstitialAd", "Ad showed fullscreen content.");
            }
        });
    }

    public void showInterstitial(Class cls, String id){
        if (mInterstitialAd != null) {
            InterstitialCallBack(cls, id);
            mInterstitialAd.show(activity);
        } else {
            intent(cls, id);
            Log.d("interstitialAd", "The interstitial ad wasn't ready yet.");
        }
    }
}
