package com.joeffison.jardimbotanico2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.ShareActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
//import android.widget.ShareActionProvider;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.joeffison.jardimbotanico2.util.MyWebViewHandler;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private AdView mAdView;
    private AdView mAdViewBanner2;
    private InterstitialAd mAdViewInterstitial;

    private MyWebViewHandler webViewHandler;

    private ShareActionProvider mShareActionProvider;

//    private ShareActionProvider mShareActionProviderFB;
    //    private static final String ADMOB_APP_ID = "ca-app-pub-5566653721914999~9056708863";
    private static final String URL_APP_SITE_HOME = "https://joeffison.github.io/jb2/";
    private static final String URL_ABOUT_ME = "https://www.instagram.com/joeffison/";
    private static final String URL_ABOUT_ME_PROFESSIONAL = "https://github.com/Joeffison";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAdService();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                shareContent();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setUpWebView();
    }

    private void startAdService() {
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        this.mAdView = (AdView) findViewById(R.id.adView);
//        this.mAdViewBanner2 = (AdView) findViewById(R.id.adViewBanner2);
//
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

    private void startInterstitialRequest() {
        if (this.mAdViewInterstitial != null) {
            AdRequest adRequest = new AdRequest.Builder().build();
            // Load ads into Interstitial Ads
            mAdViewInterstitial.loadAd(adRequest);

            mAdViewInterstitial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
//                    Snackbar.make(MainActivity.this.mAdView, "onAdLoaded", Snackbar.LENGTH_LONG)
//                            .setAction("Ads", null).show();
                    showInterstitial();
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                    Snackbar.make(MainActivity.this.mAdView, "onAdFailedToLoad: " + errorCode, Snackbar.LENGTH_LONG)
                            .setAction("Ads", null).show();
                }

//                @Override
//                public void onAdOpened() {
//                    // Code to be executed when an ad opens an overlay that
//                    // covers the screen.
//                    Snackbar.make(MainActivity.this.mAdView, "onAdOpened", Snackbar.LENGTH_LONG)
//                            .setAction("Ads", null).show();
//                }
//
//                @Override
//                public void onAdLeftApplication() {
//                    // Code to be executed when the user has left the app.
//                    Snackbar.make(MainActivity.this.mAdView, "onAdLeftApplication", Snackbar.LENGTH_LONG)
//                            .setAction("Ads", null).show();
//                }
//
//                @Override
//                public void onAdClosed() {
//                    // Code to be executed when when the user is about to return
//                    // to the app after tapping on an ad.
//                    Snackbar.make(MainActivity.this.mAdView, "onAdClosed", Snackbar.LENGTH_LONG)
//                            .setAction("Ads", null).show();
//                }
            });
        }
    }

    private void setUpAd(final AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Snackbar.make(adView, "onAdLoaded", Snackbar.LENGTH_LONG)
                        .setAction("Ads", null).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Snackbar.make(adView, "onAdFailedToLoad: " + errorCode, Snackbar.LENGTH_LONG)
                        .setAction("Ads", null).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
                Snackbar.make(adView, "onAdOpened", Snackbar.LENGTH_LONG)
                        .setAction("Ads", null).show();
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Snackbar.make(adView, "onAdLeftApplication", Snackbar.LENGTH_LONG)
                        .setAction("Ads", null).show();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.
                Snackbar.make(adView, "onAdClosed", Snackbar.LENGTH_LONG)
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
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    private void setUpWebView() {
        this.webViewHandler = new MyWebViewHandler((WebView) findViewById(R.id.mainwebviewid),
                URL_APP_SITE_HOME);
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

        if (id == R.id.nav_home) {
//            new UtilityService(this).list();
            this.webViewHandler.goHome();
            startInterstitialRequest();
//            Intent intent = new Intent(this, AdsActivity.class);
//            startActivity(intent);
        } else if (id == R.id.nav_aboutme) {
            this.webViewHandler.goToUrl(URL_ABOUT_ME);
            startInterstitialRequest();
        } else if (id == R.id.nav_aboutme_professional) {
            this.webViewHandler.goToUrl(URL_ABOUT_ME_PROFESSIONAL);
            startInterstitialRequest();
//        } else if (id == R.id.nav_manage) {
//
        } else if (id == R.id.nav_share) {
            setShareIntent(shareContent());
//        } else if (id == R.id.nav_send) {
//
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @NonNull
    private Intent shareContent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, URL_APP_SITE_HOME);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
        return sendIntent;
    }

}
