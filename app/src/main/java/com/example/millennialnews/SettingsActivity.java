package com.example.millennialnews;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchMode = findViewById(R.id.switchMode);

    }
}