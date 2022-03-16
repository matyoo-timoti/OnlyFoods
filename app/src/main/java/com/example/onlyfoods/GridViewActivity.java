package com.example.onlyfoods;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class GridViewActivity extends AppCompatActivity {
    //---the images to display---
    Integer[] imageIDs = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic7,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Gallery");
        setContentView(R.layout.activity_grid_view);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener((adapterView, view, position, id) -> {
            Intent intent = new Intent(GridViewActivity.this, ImageSwitcherActivity.class);
            intent.putExtra("currentIndex", position);
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //---inflate menu---
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //---menu bar buttons---
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //---menu item click handling---
        switch (id) {
            case (R.id.item1):
                viewImage(0, (String) item.getTitle());
                break;
            case (R.id.item2):
                viewImage(1, (String) item.getTitle());
                break;
            case (R.id.item3):
                viewImage(2, (String) item.getTitle());
                break;
            case (R.id.item4):
                viewImage(3, (String) item.getTitle());
                break;
            case (R.id.item5):
                viewImage(4, (String) item.getTitle());
                break;
            case (R.id.item6):
                viewImage(5, (String) item.getTitle());
                break;
            case (R.id.item7):
                viewImage(6, (String) item.getTitle());
                break;
            case (android.R.id.home):
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void viewImage(int pos, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ImageSwitcherActivity.class);
        intent.putExtra("currentIndex", pos);
        startActivity(intent);
    }

    //---image adapter---
    public class ImageAdapter extends BaseAdapter {

        private final Context context;

        public ImageAdapter(Context c) {
            context = c;
        }

        //---returns the number of images---
        @Override
        public int getCount() {
            return imageIDs.length;
        }

        //---returns the item---
        @Override
        public Object getItem(int position) {
            return position;
        }

        //---returns the ID of an item---
        @Override
        public long getItemId(int position) {
            return position;
        }

        //---returns an ImageView view---
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(200, 220));
                //---match fit image to column width---
                imageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                imageView.setScaleType(
                        ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(5, 5, 5, 5);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(imageIDs[position]);
            return imageView;
        }
    }
}