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

    private String currentUrl;

    public MyWebViewHandler(WebView webView, String homeUrl) {
        this.webView = webView;
        this.homeUrl = homeUrl;

        // Enable Navigation within app
        this.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                goToUrl(url);
                view.loadUrl(url);
                return true;
            }
        });

        // Enable JavaScript
        WebSettings webSettings = this.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        goToUrl(homeUrl);
    }

    public String getCurrentUrl() {
        return this.currentUrl;
    }

    public void goHome() {
        goToUrl(this.homeUrl);
    }

    public void goToUrl(String url) {
        if(url != null && !url.equals(getCurrentUrl())) {
            this.webView.loadUrl(url);
            this.currentUrl = url;
        }
    }

}
