package com.joeffison.jardimbotanico2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.adcolony.sdk.AdColony;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.jirbo.adcolony.AdColonyBundleBuilder;

import java.util.Timer;
import java.util.TimerTask;

//import android.widget.ShareActionProvider;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private WebViewFragment webViewFragment;
    private static boolean onWebView;

    private AdView mAdView;
    private AdView mAdViewBanner2;
    private InterstitialAd mAdViewInterstitial;
    private RewardedVideoAd mRewardedVideoAd;

    private UtilityFragment utilityFragment;

    private ShareActionProvider mShareActionProvider;
    //    private ShareActionProvider mShareActionProviderFB;
    private static final String URL_APP_SITE_HOME = "https://joeffison.github.io/jb2/";
    private static final String URL_APP_PLAY_STORE = "https://play.google.com/store/apps/details?id=com.joeffison.jardimbotanico2";
    private static final String URL_ABOUT_ME = "https://github.com/Joeffison";//"https://instagram.com/joeffison/";
    private static final String URL_ABOUT_ME_PROFESSIONAL = "https://github.com/Joeffison";
    private static int rewardedVideoCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check that the activity is using the layout version with the fragment_container
        if (findViewById(R.id.main_fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            webViewFragment = new WebViewFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            webViewFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_fragment_container, webViewFragment).commit();
            onWebView = true;

            this.utilityFragment = new UtilityFragment();
            this.utilityFragment.setArguments(getIntent().getExtras());

            switchToFragment(this.utilityFragment);
            onWebView = false;
        }

        this.rewardedVideoCount = 0;
        startAdService();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                shareContent();
                showRewardedVideo();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void startAdService() {
        MobileAds.initialize(this, getString(R.string.admob_app_id));

        AdColonyBundleBuilder.setUserId("JqOA7T9kyNfgyNx1tr7R");
        AdColonyBundleBuilder.setShowPrePopup(true);
        AdColonyBundleBuilder.setShowPostPopup(true);
        AdColony.configure(this,            // activity context
                "app1143de485ea84e59b2",
                "vzff813efe3ee5498ab9", "vz3b960ad6884e49a7a4");  // list of all your zones set up on the AdColony Dashboard

        this.mAdView = (AdView) findViewById(R.id.adView);
//        this.mAdViewBanner2 = (AdView) findViewById(R.id.adViewBanner2);
//
        // Rewarded Video
        this.mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
//        this.mRewardedVideoAd.
        this.mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {

            @Override
            public void onRewarded(RewardItem reward) {
                Toast.makeText(MainActivity.this, "onRewarded! currency: " + reward.getType() + "  amount: " +
                        reward.getAmount(), Toast.LENGTH_SHORT).show();
                // Reward the user.
            }

            // The following listener methods are optional.
            @Override
            public void onRewardedVideoAdLeftApplication() {
//                Toast.makeText(MainActivity.this, "onRewardedVideoAdLeftApplication",
//                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdClosed() {
//                Toast.makeText(MainActivity.this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int errorCode) {
//                Toast.makeText(MainActivity.this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdLoaded() {
//                Toast.makeText(MainActivity.this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdOpened() {
//                Toast.makeText(MainActivity.this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoStarted() {
//                Toast.makeText(MainActivity.this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
            }
        });
        loadRewardedVideoAd();

        setUpAd(this.mAdView);
//        setUpAd(this.mAdViewBanner2);

        // Interstitial ads occupies full screen of the app.
        // Adding interstitial ad doesn’t require an AdView element to be added in the xml layout.
        // Rather we load the ad programatically from the activity.
        this.mAdViewInterstitial = new InterstitialAd(this);
        // set the ad unit ID
        mAdViewInterstitial.setAdUnitId(getString(R.string.admob_ad_unit_id_interstitial));

        startInterstitialRequest();
    }

    private void loadRewardedVideoAd() {
        if (this.mRewardedVideoAd != null) {
//            AdRequest request = new AdRequest.Builder()
//                    .addNetworkExtrasBundle(AdColonyAdapter.class, AdColonyBundleBuilder.build())
//                    .build();
//            this.mRewardedVideoAd.loadAd(getString(R.string.admob_ad_unit_id_rewarded_video), request);
            this.mRewardedVideoAd.loadAd(getString(R.string.admob_ad_unit_id_rewarded_video), new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
        }
        logRewardedVideoAd();
    }

    private void showRewardedVideo(){
        if (this.mRewardedVideoAd != null ) {
            this.mRewardedVideoAd.show();
            loadRewardedVideoAd();
        }
        logRewardedVideoAd();
    }

    private void logRewardedVideoAd() {
        if(this.mRewardedVideoAd == null) {
            Log.d("AD", "this.mRewardedVideoAd == null");
        } else {
            Log.d("AD", "this.mRewardedVideoAd.isLoaded:" + this.mRewardedVideoAd.isLoaded());
        }
    }

    private void startInterstitialRequest() {
//        if (!Build.FINGERPRINT.startsWith("generic") && this.mAdViewInterstitial != null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            // Load ads into Interstitial Ads
            mAdViewInterstitial.loadAd(adRequest);

            mAdViewInterstitial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    showInterstitial();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                    Snackbar.make(MainActivity.this.mAdView, "onAdFailedToLoad: " + errorCode, Snackbar.LENGTH_LONG)
                            .setAction("Ads", null).show();
                }
            });
//        }
    }

    private void setUpAd(final AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Snackbar.make(adView, "onAdFailedToLoad: " + errorCode, Snackbar.LENGTH_LONG)
                        .setAction("Ads", null).show();
            }
        });
        adView.loadAd(adRequest);
    }

    private void showInterstitial() {
        if (mAdViewInterstitial.isLoaded()) {
            mAdViewInterstitial.show();
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        if(this.mRewardedVideoAd != null) {
            this.mRewardedVideoAd.pause(this);
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (mAdView != null) {
            mAdView.resume();
        }
        if(this.mRewardedVideoAd != null) {
            this.mRewardedVideoAd.resume(this);
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        if(this.mRewardedVideoAd != null) {
            this.mRewardedVideoAd.destroy(this);
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        Settings Menu
//        getMenuInflater().inflate(R.menu.main, menu);
//        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.nav_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        // Return true to display menu
        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        showRewardedVideo();

        if (id == R.id.nav_share) {
            setShareIntent(shareContent());
        } else if (id == R.id.nav_utilities) {
            if (!this.utilityFragment.isVisible()) {
                switchToFragment(this.utilityFragment);
                onWebView = false;
                startInterstitialRequest();
            }
        } else if (id == R.id.nav_home) {
            switchToFragment(this.webViewFragment);
            if(!URL_APP_SITE_HOME.equals(this.webViewFragment.getCurrentUrl())){
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(MainActivity.this.webViewFragment.isVisible()) {
                            MainActivity.this.webViewFragment.goHome();
                            this.cancel();
                        }
                    }
                }, 0, 500);
                startInterstitialRequest();
            }
        } else if (id == R.id.nav_aboutme) {
            switchToFragment(this.webViewFragment);
            if(this.webViewFragment.isVisible() && URL_ABOUT_ME.equals(this.webViewFragment.getCurrentUrl())){
                return true;
            }

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if(MainActivity.this.webViewFragment.isVisible()) {
                        MainActivity.this.webViewFragment.goToUrl(URL_ABOUT_ME);
                        this.cancel();
                    }
                }
            }, 0, 500);
            startInterstitialRequest();
        } else if (id == R.id.nav_aboutme_professional) {
            if (!this.webViewFragment.isVisible()) {
                switchToFragment(this.webViewFragment);
            }

            this.webViewFragment.goToUrl(URL_ABOUT_ME_PROFESSIONAL);
            onWebView = true;
            startInterstitialRequest();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchToFragment(Fragment newFragment) {
        if(!newFragment.isVisible()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.main_fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }
    }

    @NonNull
    private Intent shareContent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, URL_APP_PLAY_STORE);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
        return sendIntent;
    }

}
