package com.joeffison.jardimbotanico2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.joeffison.jardimbotanico2.util.monetization.MyAdManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private WebViewFragment webViewFragment;

    private UtilityFragment utilityFragment;

    private ShareActionProvider mShareActionProvider;
    private static final String URL_APP_SITE_HOME = "https://joeffison.github.io/jb2/";
    private static final String URL_APP_PLAY_STORE = "https://play.google.com/store/apps/details?id=com.joeffison.jardimbotanico2";
    private static final String URL_ABOUT_ME = "https://www.facebook.com/joeffison";
    private static final String URL_ABOUT_ME_PROFESSIONAL = "https://www.linkedin.com/in/joeffison-silv%C3%A9rio-de-andrade-3b1716a6/";
    private static final String GOOGLE_DRIVE = "https://drive.google.com/viewerng/viewer?embedded=true&url=";
    private static final String URL_PDF_RULES = GOOGLE_DRIVE + "https://github.com/Joeffison/jb2/raw/gh-pages/data/utils/00_Regimento%20Interno.pdf";
    private static final String URL_PDF_LAST_MEETING = GOOGLE_DRIVE + "https://github.com/Joeffison/jb2/raw/gh-pages/data/utils/01_Assembleia%20Resumo.pdf";
    private MyAdManager adManager;

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

            webViewFragment.goToUrl(URL_PDF_LAST_MEETING);
            this.utilityFragment = new UtilityFragment();
            this.utilityFragment.setArguments(getIntent().getExtras());
//            switchToFragment(this.utilityFragment);
        }

        startAdService();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adManager.showRewardedAd();
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
        this.adManager = MyAdManager.getInstance(this);

        this.adManager.addBanner(R.id.adView);
//        this.adManager.addBanner(R.id.adViewBanner2);
        this.adManager.addInterstitialAd();
        this.adManager.addRewardedAd();
    }

    @Override
    public void onPause() {
        if(this.adManager != null) {
            this.adManager.pause(this);
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if(this.adManager != null) {
            this.adManager.resume(this);
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if(this.adManager != null) {
            this.adManager.destroy(this);
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
        this.adManager.showInterstitialAd();

        if (id == R.id.nav_share) {
            setShareIntent(shareContent());
        } else if (id == R.id.nav_utilities) {
            if (!this.utilityFragment.isVisible()) {
                switchToFragment(this.utilityFragment);
                this.adManager.showInterstitialAd();
            }
        } else if (id == R.id.nav_home) {
            switchToFragment(this.webViewFragment);
            if(this.webViewFragment.isVisible() && !URL_APP_SITE_HOME.equals(this.webViewFragment.getCurrentUrl())){
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        if(MainActivity.this.webViewFragment.isVisible()) {
//                            MainActivity.this.webViewFragment.goHome();
//                            this.cancel();
//                        }
//                    }
//                }, 0, 500);

                if(MainActivity.this.webViewFragment.isVisible()) {
                    this.webViewFragment.goToUrl(URL_APP_SITE_HOME);
                }

                this.adManager.showInterstitialAd();
            }
        } else if (id == R.id.nav_condominium_rules) {
            switchToFragment(this.webViewFragment);
            if(this.webViewFragment.isVisible() && !URL_PDF_RULES.equals(this.webViewFragment.getCurrentUrl())){
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    if(MainActivity.this.webViewFragment.isVisible()) {
//                        MainActivity.this.webViewFragment.goToUrl(URL_PDF_RULES);
//                        this.cancel();
//                    }
//                }
//            }, 0, 500);
                if(MainActivity.this.webViewFragment.isVisible()) {
                    this.webViewFragment.goToUrl(URL_PDF_RULES);
                }

                this.adManager.showInterstitialAd();
            }
        } else if (id == R.id.nav_meetings) {
            switchToFragment(this.webViewFragment);
            if(this.webViewFragment.isVisible() && !URL_PDF_LAST_MEETING.equals(this.webViewFragment.getCurrentUrl())){
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    if(MainActivity.this.webViewFragment.isVisible()) {
//                        MainActivity.this.webViewFragment.goToUrl(URL_PDF_RULES);
//                        this.cancel();
//                    }
//                }
//            }, 0, 500);
                if(MainActivity.this.webViewFragment.isVisible()) {
                    this.webViewFragment.goToUrl(URL_PDF_LAST_MEETING);
                }

                this.adManager.showInterstitialAd();
            }
        } else if (id == R.id.nav_aboutme) {
            switchToFragment(this.webViewFragment);
            if(this.webViewFragment.isVisible() && !URL_ABOUT_ME.equals(this.webViewFragment.getCurrentUrl())){
//            new Timer().schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    if(MainActivity.this.webViewFragment.isVisible()) {
//                        MainActivity.this.webViewFragment.goToUrl(URL_ABOUT_ME);
//                        this.cancel();
//                    }
//                }
//            }, 0, 500);
                if(MainActivity.this.webViewFragment.isVisible()) {
                    this.webViewFragment.goToUrl(URL_ABOUT_ME);
                }

                this.adManager.showInterstitialAd();
            }
        } else if (id == R.id.nav_aboutme_professional) {
            switchToFragment(this.webViewFragment);
            if(this.webViewFragment.isVisible() && !URL_ABOUT_ME_PROFESSIONAL.equals(this.webViewFragment.getCurrentUrl())){
                if(MainActivity.this.webViewFragment.isVisible()) {
                    this.webViewFragment.goToUrl(URL_ABOUT_ME_PROFESSIONAL);
                }

                this.adManager.showInterstitialAd();
            }
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
