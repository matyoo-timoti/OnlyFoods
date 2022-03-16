package com.example.onlyfoods;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar progressBar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        //---show back button on action bar---
        actionBar.setDisplayHomeAsUpEnabled(true);

        //---image source---
        webView.loadUrl("https://source.unsplash.com/random/?food,cake,steak");
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view,int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });

        //---this will cause the WebView to be zoomed out initially---
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //---inflate menu---
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //---click handlers---
        switch (item.getItemId()) {

            //---prev button---
            case (R.id.goBack):
                if (webView.canGoBack()) {
                    webView.goBack();
                    toast("Previous");
                } else {
                    toast("Can't go back anymore");
                }
                break;

            //---forward button---
            case (R.id.forward):
                if (webView.canGoForward()) {
                    webView.goForward();
                    toast("Forward");
                } else {
                    toast("Can't go further!");
                }
                break;

            //---refresh button---
            case (R.id.refresh):
                webView.loadUrl("https://source.unsplash.com/random/?food,cake,steak");
                toast("Refresh");
                break;

            //---back button on title bar
            case (android.R.id.home):
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //---click handler for the android back button)---
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    private void toast(String msg) {
        Toast.makeText(this, msg,
                Toast.LENGTH_SHORT).show();
    }

}