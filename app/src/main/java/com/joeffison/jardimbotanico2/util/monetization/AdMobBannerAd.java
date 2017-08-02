package com.joeffison.jardimbotanico2.util.monetization;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.joeffison.jardimbotanico2.util.monetization.interfaces.IBannerAd;

/**
 * Created by Joeffison on 02/08/2017.
 */

public class AdMobBannerAd implements IBannerAd {

    private Context context;

    private AdView adView;
    private int rIdView;

    private int loadingTries = 0;

    public AdMobBannerAd(Activity context, int rIdView) {
        this.context = context;
        this.adView = (AdView) context.findViewById(rIdView);
        this.rIdView = rIdView;

        this.adView.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int errorCode) {
                loadAd();
            }

            @Override
            public void onAdImpression() {
                loadingTries = 0;
            }
        });
        loadAd();
    }

    @Override
    public void loadAd() {
        if(adView != null && this.loadingTries < MyAdManager.getMaxLoadingTries()) {
            this.loadingTries++;
            adView.loadAd(MyAdManager.newAdRequest());
        }
    }

    @Override
    public void showAd() {

    }

    @Override
    public void pause(Context context) {
        if (this.adView != null){
            adView.pause();
        }
    }

    @Override
    public void resume(Context context) {
        if (this.adView != null){
            adView.resume();
        }
    }

    @Override
    public void destroy(Context context) {
        if (this.adView != null){
            adView.destroy();
        }
    }

    private int getRIdView() {
        return this.rIdView;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && (obj instanceof AdMobBannerAd)) {
            return this.rIdView == ((AdMobBannerAd) obj).getRIdView();
        }
        return false;
    }
}
