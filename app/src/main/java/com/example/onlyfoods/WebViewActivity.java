package com.example.onlyfoods;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
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
        registerForContextMenu(webView);

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
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }
        });

        //---this will cause the WebView to be zoomed out initially---
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        //---zoom controls---
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
    }

    //---for the progress bar---
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

    //---click to save image---
    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        super.onCreateContextMenu(contextMenu, view, contextMenuInfo);

        final WebView.HitTestResult webViewHitTestResult = webView.getHitTestResult();

        if (webViewHitTestResult.getType() == WebView.HitTestResult.IMAGE_TYPE ||
                webViewHitTestResult.getType() == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {

            contextMenu.setHeaderTitle("Do you want to save image?");
            contextMenu.add(0, 1, 0, "Save Image")
                    .setOnMenuItemClickListener(menuItem -> {

                        String DownloadImageURL = webViewHitTestResult.getExtra();

                        if (URLUtil.isValidUrl(DownloadImageURL)) {

                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DownloadImageURL));
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                            downloadManager.enqueue(request);

                            Toast.makeText(WebViewActivity.this, "Download Starting", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(WebViewActivity.this, "Sorry, Something Went Wrong.", Toast.LENGTH_LONG).show();
                        }
                        return false;
                    });
            contextMenu.add(0, 1, 0, "Cancel");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //---click handlers---
        switch (item.getItemId()) {
            //---prev button---
            case (R.id.goBack):
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    toast("Can't go back anymore");
                }
                break;

            //---forward button---
            case (R.id.forward):
                if (webView.canGoForward()) {
                    webView.goForward();
                } else {
                    toast("Can't go further!");
                }
                break;

            //---refresh button---
            case (R.id.refresh):

                if (!item.isEnabled()) {
                    toast("Please wait");
                }

                //---disable button for 2 sec, to avoid spamming---
                item.setEnabled(false);
                new Handler().postDelayed(() -> item.setEnabled(true), 2000);

                toast("Refresh");
                webView.loadUrl("https://source.unsplash.com/random/?food,cake,steak");
                break;

            //---back button on title bar
            case (android.R.id.home):
                toast("Exit WebView");
                finish();
                break;
            default:
                webView.loadUrl("https://source.unsplash.com/random/?food,cake,steak");
        }
        return super.onOptionsItemSelected(item);
    }

    //---click handler for the android back button)---
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            toast("Previous");
        } else {
            toast("Exit WebView");
            finish();
        }
    }

    private void toast(String msg) {
        Toast.makeText(this, msg,
                Toast.LENGTH_SHORT).show();
    }

}