package com.joeffison.jardimbotanico2.util.monetization;

import android.app.Activity;
import android.content.Context;

import com.adcolony.sdk.AdColony;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.jirbo.adcolony.AdColonyBundleBuilder;
import com.joeffison.jardimbotanico2.R;
import com.joeffison.jardimbotanico2.util.monetization.interfaces.IBannerAd;
import com.joeffison.jardimbotanico2.util.monetization.interfaces.IGenericAd;
import com.joeffison.jardimbotanico2.util.monetization.interfaces.IInterstitialAd;
import com.joeffison.jardimbotanico2.util.monetization.interfaces.IRewardedAd;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Joeffison on 02/08/2017.
 */

public class MyAdManager {
    private static final int MAX_LOADING_TRIES = 3;

    private static MyAdManager instance = null;
    private Activity context;

    private IRewardedAd rewardedAd;
    private IInterstitialAd interstitialAd;
    private Set<IBannerAd> banners;

    public static synchronized MyAdManager getInstance(Activity context) {
        if (instance == null){
            instance = new MyAdManager(context);
        }
        return instance;
    }

    private MyAdManager(Activity context) {
        this.context = context;
        this.banners = new HashSet<IBannerAd>();

        startAdMobAdService(context);
        startAdColonyAdService(context);
    }

    private void startAdMobAdService(Context context) {
        MobileAds.initialize(context, getString(R.string.admob_app_id));
    }

    private void startAdColonyAdService(Activity context) {
        AdColonyBundleBuilder.setUserId("JqOA7T9kyNfgyNx1tr7R"); // user id
        AdColonyBundleBuilder.setShowPrePopup(true);
        AdColonyBundleBuilder.setShowPostPopup(true);
        AdColony.configure(context, // activity context
                "app1143de485ea84e59b2", // app id
                "vzff813efe3ee5498ab9", "vz3b960ad6884e49a7a4");  // all zones on AdColony Dashboard
    }

    private String getString(int resId) {
        return this.context.getString(resId);
    }

    private List<IGenericAd> getAds() {
        List<IGenericAd> response = new ArrayList<IGenericAd>();

        if(this.rewardedAd != null) {
            response.add(this.rewardedAd);
        }

        if(this.interstitialAd != null) {
            response.add(this.interstitialAd);
        }

        if(banners != null) {
            for(IBannerAd banner: banners) {
                response.add(banner);
            }
        }

        return response;
    }

    public void addBanner(int adView) {
        banners.add(new AdMobBannerAd(this.context, R.id.adView));
    }

    public synchronized IInterstitialAd addInterstitialAd() {
        if(this.interstitialAd == null) {
            this.interstitialAd = new AdMobInterstitialAd(this.context);
        }
        return this.interstitialAd;
    }

    public synchronized IRewardedAd addRewardedAd() {
        if(this.rewardedAd == null) {
            this.rewardedAd = new AdColonyRewardedAd(this.context);
        }
        return this.rewardedAd;
    }

    public void showInterstitialAd() {
        if(this.interstitialAd == null) {
            addInterstitialAd();
        }
        this.interstitialAd.showAd();
    }

    public void showRewardedAd() {
        if(this.rewardedAd == null) {
            addRewardedAd();
        }
        this.rewardedAd.showAd();
    }

    public static AdRequest newAdRequest() {
        return new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
    }

    public void pause(Context context) {
        for (IGenericAd ad: getAds()) {
            ad.pause(context);
        }
    }

    public void resume(Context context) {
        for (IGenericAd ad: getAds()) {
            ad.resume(context);
        }
    }

    public void destroy(Context context) {
        for (IGenericAd ad: getAds()) {
            ad.destroy(context);
        }
    }

    public static int getMaxLoadingTries() {
        return MAX_LOADING_TRIES;
    }
}
