package com.joeffison.jardimbotanico2.util;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Joeffison on 14/06/2017.
 */

public class MyWebViewHandler {

    private WebView webView;
    private String homeUrl;

    public MyWebViewHandler(WebView webView, String homeUrl) {
        this.webView = webView;
        this.homeUrl = homeUrl;

        this.webView.loadUrl(homeUrl);

        // Enable Navigation within app
        this.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });

        // Enable JavaScript
        WebSettings webSettings = this.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    public void goHome() {
        goToUrl(this.homeUrl);
    }

    public void goToUrl(String url) {
        this.webView.loadUrl(url);
    }

}
