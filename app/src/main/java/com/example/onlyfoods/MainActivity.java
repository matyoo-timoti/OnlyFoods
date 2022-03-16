package com.example.onlyfoods;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //---Remove title bar---
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //---Remove title bar---
        Objects.requireNonNull(getSupportActionBar()).hide();

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // View Gallery Button
        Button btnGallery = findViewById(R.id.btnViewGallery);
        btnGallery.setOnClickListener(view -> openGalleryActivity());

        //View Web View
        Button btnViewMore = findViewById(R.id.btnViewMore);
        btnViewMore.setOnClickListener(view -> openViewMoreActivity());
    }

    public void openGalleryActivity() {
        Intent intent = new Intent(this, GridViewActivity.class);
        startActivity(intent);
    }

    public void openViewMoreActivity() {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }
}