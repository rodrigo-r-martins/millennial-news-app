package com.example.millennialnews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.List;

public class ArticleNewsActivity extends AppCompatActivity {

    TextView tvNewsTitleFull;
    TextView tvNewsAuthorFull;
    TextView tvNewsDateFull;
    TextView tvNewsContentFull;
    ImageView ivNewsImageFull;
    Button btnFav;
    private DatabaseReference db;
    private Switch switchMode;
    private List<NewsArticle> favArticles = new ArrayList<>();
    private boolean viewingArticle;
    SaveState saveState;
    String userID;

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        userID = getIntent().getStringExtra("userID");
        viewingArticle = true;
        tvNewsTitleFull = findViewById(R.id.tvNewsTitleFull);
        tvNewsAuthorFull = findViewById(R.id.tvNewsAuthorFull);
        tvNewsDateFull = findViewById(R.id.tvNewsDateFull);
        ivNewsImageFull = findViewById(R.id.ivNewsImageFull);
        tvNewsContentFull = findViewById(R.id.tvNewsContentFull);
        btnFav = findViewById(R.id.btn_fav);

        Intent intent = this.getIntent();
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

        if (userID != null)
            Log.d("ArticleNewsActivity", userID);

        db = FirebaseDatabase.getInstance().getReference("users");

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query queryRef = db.orderByChild("users").equalTo(userID);
                    db.addValueEventListener(new ValueEventListener() {


                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (userID != null){
                                Log.d("ondatachange", userID);
                            }
                            Log.d("ondatachange", snapshot.child(userID).toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
            }
                                  });
    }
}

//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            dataSnapshot.child()
//
//                            int counter = 0;
//                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
//
//                                if (user != null) {
//                                    if (user.getEmail().equals(userEmail)) {
//                                        Toast.makeText(ArticleNewsActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
//                                        doesUserExist = true;
//                                    }
//                                }
//                                counter++;
//                            }
//                            if (!doesUserExist) {
//                                if (userFirstName.isEmpty()) {
//                                    firstName.setError("Please enter the First Name");
//                                    Log.w("DATABASE", "First name is empty");
//                                } else if (userLastName.isEmpty()) {
//                                    lastName.setError("Please enter the Last Name");
//                                } else if (userEmail.isEmpty()) {
//                                    email.setError("Please enter the Email");
//                                } else if (userPassword.isEmpty()) {
//                                    password.setError("Please enter a Password");
//                                } else {
//                                    // If none of the values are empty,
//                                    // add user to the Database and show a Toast with confirmation
//                                    User newUser = new User(
//                                            userFirstName,
//                                            userLastName,
//                                            userPassword,
//                                            userEmail
//                                    );
//                                    db.child(String.valueOf(counter)).setValue(newUser);
//                                    firstName.setText("");
//                                    lastName.setText("");
//                                    email.setText("");
//                                    password.setText("");
//                                    Toast.makeText(RegisterActivity.this, "Successfully registered!", Toast.LENGTH_LONG).show();
//                                    startActivity(intent);
//                                }
//                            }
//                            Toast.makeText(RegisterActivity.this, "Successfully registered! outter 1", Toast.LENGTH_LONG).show();
//                        }
//
//                            @Override
//                            public void onCancelled(DatabaseError error) {
//                                // Failed to read value
//                                Log.w("DATABASE", "Failed to read value.", error.toException());
//                            }
//                        });
//            }
//        });
//    }
//}











