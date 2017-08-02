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
    private static final String GOOGLE_DRIVE = "https://drive.google.com/viewerng/viewer?embedded=true&url=";
    private static final String URL_PDF_RULES = GOOGLE_DRIVE + "https://github.com/Joeffison/jb2/raw/gh-pages/data/utils/00_Regimento%20Interno.pdf";
    private static final String URL_PDF_LAST_MEETING = GOOGLE_DRIVE + "https://github.com/Joeffison/jb2/raw/gh-pages/data/utils/01_Assembleia%20Resumo.pdf";
    private MyWebViewHandler webViewHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        setUpWebView(view);

        return view;
    }

    public void setUpWebView(View view) {
        WebView webView = (WebView) view.findViewById(R.id.mainwebviewid);
        if(webView != null) {
            this.webViewHandler = new MyWebViewHandler(webView, URL_PDF_LAST_MEETING);
        }
    }

    public String getCurrentUrl() {
        Log.d("WebView", this.webViewHandler == null ? "" : ""+this.webViewHandler.getCurrentUrl());
        return this.webViewHandler == null ? null : this.webViewHandler.getCurrentUrl();
    }

    public void goHome() {
        this.webViewHandler.goHome();
    }

    public void goToUrl(String url) {
        if(this.webViewHandler != null) {
            this.webViewHandler.goToUrl(url);
        }
    }
}
