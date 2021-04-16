package com.example.millennialnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class ArticleNewsActivity extends AppCompatActivity {

    TextView tvNewsTitleFull;
    TextView tvNewsAuthorFull;
    TextView tvNewsDateFull;
    TextView tvNewsContentFull;
    ImageView ivNewsImageFull;

    private Switch switchMode;
    SaveState saveState;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        saveState = new SaveState(this);
        if(saveState.getState() == true)
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_settings);

        switchMode = findViewById(R.id.switchMode);

        if(saveState.getState() == true)
            switchMode.setChecked(true);

        setContentView(R.layout.activity_article_news);

        tvNewsTitleFull = findViewById(R.id.tvNewsTitleFull);
        tvNewsAuthorFull = findViewById(R.id.tvNewsAuthorFull);
        tvNewsDateFull = findViewById(R.id.tvNewsDateFull);
        ivNewsImageFull = findViewById(R.id.ivNewsImageFull);
        tvNewsContentFull = findViewById(R.id.tvNewsContentFull);

        Intent intent = ((Activity) this).getIntent();
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String date = intent.getStringExtra("date");
        String content = intent.getStringExtra("content");
        String image = intent.getStringExtra("image");

        tvNewsTitleFull.setText(title);
        tvNewsAuthorFull.setText(author);
        tvNewsDateFull.setText(date);
        tvNewsContentFull.setText(content);
        LoadImageNews imageFull = new LoadImageNews(ivNewsImageFull);
        imageFull.execute(image);
    }
}