package com.joeffison.jardimbotanico2.util.monetization;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.joeffison.jardimbotanico2.R;
import com.joeffison.jardimbotanico2.util.monetization.interfaces.IInterstitialAd;

/**
 * Created by Joeffison on 02/08/2017.
 */

public class AdMobInterstitialAd implements IInterstitialAd {

    private static Context context;

    private static InterstitialAd adView;
    private int loadingTries = 0;

    public AdMobInterstitialAd(Context context) {
        this.context = context;

        adView = new InterstitialAd(context);
        adView.setAdUnitId(context.getString(R.string.admob_ad_unit_id_interstitial));
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                loadingTries = 0;
                showAd();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                loadAd();
            }
        });
        loadAd();
    }

    @Override
    public void loadAd() {
        if(adView != null && !adView.isLoaded() && this.loadingTries < MyAdManager.getMaxLoadingTries()) {
            //if (!Build.FINGERPRINT.startsWith("generic") && this.mAdViewInterstitial != null) {
            // Load ads into Interstitial Ads
            this.loadingTries++;
            adView.loadAd(MyAdManager.newAdRequest());
            //}
        }
    }

    @Override
    public void showAd() {
        if(adView != null && adView.isLoaded()) {
            adView.show();
        }
        loadAd();
    }

    @Override
    public void pause(Context context) {
        // pass
    }

    @Override
    public void resume(Context context) {
        // pass
    }

    @Override
    public void destroy(Context context) {
        // pass
    }

}
