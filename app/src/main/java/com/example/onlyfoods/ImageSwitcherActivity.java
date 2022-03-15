package com.example.onlyfoods;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ImageSwitcherActivity extends AppCompatActivity {
    ImageButton btPrevious, btNext;
    ImageSwitcher imageSwitcher;
    TextView txtItemName;

    int[] imageList = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic7,
    };

    String[] itemNames = {
            "Afritada", "Menudo", "Caldereta", "Mechado", "Pochero", "Adobo", "Sisig"
    };

    int count = imageList.length;
    static int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_switcher);

        //---show action bar---
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        //---get the parameters from the gridview---
        Intent intent = this.getIntent();
        currentIndex = intent.getIntExtra("currentIndex", 0);

        btPrevious = findViewById(R.id.btnPrev);
        btNext = findViewById(R.id.btnNext);
        imageSwitcher = findViewById(R.id.imgSwitcher);
        txtItemName = findViewById(R.id.itemName);

        imageSwitcher.setFactory(() -> {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return imageView;
        });

        //---show the selected item---
        imageSwitcher.setImageResource(imageList[currentIndex]);

        //---show the name of the item
        txtItemName.setText(itemNames[currentIndex]);

        btPrevious.setOnClickListener(v -> {
            imageSwitcher.setInAnimation(ImageSwitcherActivity.this, R.anim.from_left);
            imageSwitcher.setOutAnimation(ImageSwitcherActivity.this, R.anim.to_right);
            --currentIndex;

            if (currentIndex < 0) {
                currentIndex = imageList.length - 1;
            }

            imageSwitcher.setImageResource((imageList[currentIndex]));
            txtItemName.setText(itemNames[currentIndex]);
        });

        btNext.setOnClickListener(v -> {
            imageSwitcher.setInAnimation(ImageSwitcherActivity.this, R.anim.from_right);
            imageSwitcher.setOutAnimation(ImageSwitcherActivity.this, R.anim.to_left);
            currentIndex++;

            if (currentIndex == count)
                currentIndex = 0;
            imageSwitcher.setImageResource(imageList[currentIndex]);
            txtItemName.setText(itemNames[currentIndex]);

        });
    }

    //---Back button on action bar---
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if ( id == android.R.id.home ) {
            finish();
            return true;
        }
        startActivity(new Intent(ImageSwitcherActivity.this,GridViewActivity.class));
        return super.onOptionsItemSelected(item);
    }
}