package com.joeffison.jardimbotanico2.util.monetization;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.joeffison.jardimbotanico2.R;
import com.joeffison.jardimbotanico2.util.monetization.interfaces.IRewardedAd;

/**
 * Created by Joeffison on 02/08/2017.
 */

public class AdColonyRewardedAd implements IRewardedAd {

    private static Context context;
    private static RewardedVideoAd adView;
    private static String adId;
    private int loadingTries = 0;

    private static int rewardedVideoCount;

    public AdColonyRewardedAd(Context context) {
        this.context = context;
        this.rewardedVideoCount = 0;

        this.adId = this.context.getString(R.string.admob_ad_unit_id_rewarded_video);
        this.adView = MobileAds.getRewardedVideoAdInstance(context);
        this.adView.setRewardedVideoAdListener(new RewardedVideoAdListener() {

            @Override
            public void onRewarded(RewardItem reward) {
                loadAd();
                rewardUser();
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                loadAd();
            }

            @Override
            public void onRewardedVideoAdClosed() {
                loadAd();
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int errorCode) {
                loadAd();
            }

            @Override
            public void onRewardedVideoAdLoaded() {
                loadingTries = 0;
//                showAd();
            }

            @Override
            public void onRewardedVideoAdOpened() {
                loadAd();
            }

            @Override
            public void onRewardedVideoStarted() {
                loadAd();
            }
        });
        loadAd();
    }

    @Override
    public synchronized void loadAd() {
        if(adView != null && !adView.isLoaded() && this.loadingTries < MyAdManager.getMaxLoadingTries()) {
            this.adView.loadAd(adId, MyAdManager.newAdRequest());
//            AdRequest request = new AdRequest.Builder()
//                    .addNetworkExtrasBundle(AdColonyAdapter.class, AdColonyBundleBuilder.build())
//                    .build();
//            this.mRewardedVideoAd.loadAd(getString(R.string.admob_ad_unit_id_rewarded_video), request);
        }
    }

    @Override
    public synchronized void showAd(){
        if (this.adView != null && this.adView.isLoaded()) {
            this.adView.show();
        }
        loadAd();
    }

    @Override
    public void pause(Context context) {
        if (this.adView != null){
            adView.pause(context);
        }
    }

    @Override
    public void resume(Context context) {
        if (this.adView != null){
            adView.resume(context);
        }
    }

    @Override
    public void destroy(Context context) {
        if (this.adView != null){
            adView.destroy(context);
        }
    }

    private synchronized void rewardUser() {
        this.rewardedVideoCount++;
    }

    @Override
    public synchronized int getRewards() {
        return this.rewardedVideoCount;
    }

}
