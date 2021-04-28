package com.example.millennialnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText etUserEmail;
    private EditText etUserPassword;
    private Button btnLogin;
    private DatabaseReference db;
    private User currentUser;

    private Switch switchMode;
    SaveState saveState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        saveState = new SaveState(this);
        if (saveState.getState() == true)
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_settings);
        switchMode = findViewById(R.id.switchMode);

        if (saveState.getState() == true)
            switchMode.setChecked(true);

        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        // INITIALIZING VARIABLES
        etUserEmail = findViewById(R.id.etUserEmail);
        etUserPassword = findViewById(R.id.etUserPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // === DATABASE ===
        db = FirebaseDatabase.getInstance().getReference("users");
        Log.w("DATABASE", db.toString());

        // TO ADD IN LOGIN ACTIVITY
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String userEmail = etUserEmail.getText().toString();
                        String userPassword = etUserPassword.getText().toString();
                        User user;
                        boolean isLoggedIn = false;
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            user = ds.getValue(User.class);
                            Log.w("DATABASE", ds.getValue().toString());
                            if (user != null) {
                                Log.w("DATABASE", "Reading values");
                                if (user.getEmail().equals(userEmail) && user.getPassword().equals(userPassword)) {
                                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    currentUser = user;
                                    isLoggedIn = true;
                                    Log.w("DATABASE", currentUser.toString());
                                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                                    intent.putExtra("isLoggedIn", isLoggedIn);
                                    intent.putExtra("userEmail", currentUser.getEmail());
                                    Log.w("DATABASE", currentUser.toString());
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Wrong credentials. Try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("DATABASE", "Failed to read value.", error.toException());
                    }
                });
            }
        });
    }
}

//
//        // TO ADD IN REGISTER ACTIVITY
//        db = FirebaseDatabase.getInstance().getReference("users");
////        db.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                String userEmail = etUserEmail.getText().toString();
////                String userPassword = etUserPassword.getText().toString();
////                String userFirstName = etUserFirstname.getText().toString();
////                String userLastName = etUserLastname.getText().toString();
////                User user;
////                boolean doesUserExist = false;
////                int counter = 0;
////                for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                    user = ds.getValue(User.class);
////                    if (user != null) {
////                        if (user.getEmail().equals(userEmail)) {
////                            Toast.makeText(MainActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
////                            doesUserExist = true;
////                        }
////                    }
////                    counter++;
////                }
////
////                if (!doesUserExist) {
////                    if (userFirstName.isEmpty()) {
////                        etUserFirstname.setError("Please enter the First Name");
////                    } else if (userLastName.isEmpty()) {
////                        etUserLastname.setError("Please enter the Last Name");
////                    } else if (userEmail.isEmpty()) {
////                        etUserEmail.setError("Please enter the Email");
////                    } else if (userPassword.isEmpty()) {
////                        etUserPassword.setError("Please enter a Password");
////                    } else {
////                        // If none of the values are empty,
////                        // add user to the Database and show a Toast with confirmation
////                        User newUser = new User(
////                                userFirstName,
////                                userLastName,
////                                userEmail,
////                                userPassword
////                        );
////                        db.child(String.valueOf(counter)).setValue(newUser);
////                        etUserFirstname.setText("");
////                        etUserLastname.setText("");
////                        etUserEmail.setText("");
////                        etUserPassword.setText("");
////                    }
////                }
////            }
////
////            @Override
////            public void onCancelled(DatabaseError error) {
////                // Failed to read value
////                Log.w("DATABASE", "Failed to read value.", error.toException());
////            }
////        });