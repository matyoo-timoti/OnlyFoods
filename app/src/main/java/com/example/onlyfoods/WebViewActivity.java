package com.example.onlyfoods;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;

        //---show back button on action bar---
        actionBar.setDisplayHomeAsUpEnabled(true);

        //---image source---
        webView.loadUrl("https://source.unsplash.com/random/?food,cake,steak");

        //---this will cause the WebView to be zoomed out initially---
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
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
                webView.loadUrl("https://source.unsplash.com/random/?food,cake,steak");
                toast("Refresh");
                break;

            //---back button on title bar
            case (android.R.id.home) :
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //---click handler for the android back button)---
    @Override
    public void onBackPressed(){
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