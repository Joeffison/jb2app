package com.joeffison.jardimbotanico2;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.joeffison.jardimbotanico2.util.MyWebViewHandler;

public class WebViewFragment extends Fragment {

    private static final String URL_APP_SITE_HOME = "https://joeffison.github.io/jb2/";
    private MyWebViewHandler webViewHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_web_view, container, false);
        setUpWebView(v);
        return v;
    }

    public void setUpWebView(View v) {
        if((WebView) v.findViewById(R.id.mainwebviewid) == null) {
//            Snackbar.make(context.getCurrentFocus(), "WebView) findViewById(R.id.mainwebviewid) == null", Snackbar.LENGTH_LONG)
//                    .setAction("Ads", null).show();
            Log.e("WebViewFragment>WebView", "WebView findViewById(R.id.mainwebviewid) == null");
        } else {
//            Snackbar.make(getActivity().getCurrentFocus(), "WebViewFragment>WebView findViewById(R.id.mainwebviewid) != null", Snackbar.LENGTH_LONG)
//                    .setAction("Ads", null).show();
            this.webViewHandler = new MyWebViewHandler((WebView) v.findViewById(R.id.mainwebviewid),
                    URL_APP_SITE_HOME);
            Log.e("WebViewFragment>WebView", "WebView findViewById(R.id.mainwebviewid) IS NOT null");
        }
    }

    public void goHome() {
        this.webViewHandler.goHome();
    }

    public void goToUrl(String url) {
        this.webViewHandler.goToUrl(url);
    }
}
