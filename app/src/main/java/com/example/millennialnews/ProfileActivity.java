package com.example.millennialnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private TextView userName;
    private RecyclerView profileNews;
    SaveState saveState;
    private Switch switchMode;
    private Intent intentArticle;
    private ProfileAdapter profileAdapter;
    private List<NewsArticle> articleList  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // === SETTING UP NIGHT MODE ===
        saveState = new SaveState(this);
        if(saveState.getState())
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_settings);
        switchMode = findViewById(R.id.switchMode);
        if(saveState.getState())
            switchMode.setChecked(true);

        setContentView(R.layout.activity_profile);

        userName = findViewById(R.id.userName);
        profileNews = findViewById(R.id.profileNews);

        if (intentArticle == null) {
            profileAdapter = new ProfileAdapter(articleList, intentArticle);
            profileNews.setAdapter(profileAdapter);
            profileNews.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}