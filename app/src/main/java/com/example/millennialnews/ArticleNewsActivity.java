package com.example.millennialnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ArticleNewsActivity extends AppCompatActivity {

    TextView tvNewsTitleFull;
    TextView tvNewsAuthorFull;
    TextView tvNewsDateFull;
    TextView tvNewsContentFull;
    ImageView ivNewsImageFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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