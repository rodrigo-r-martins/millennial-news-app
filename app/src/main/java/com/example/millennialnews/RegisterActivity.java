package com.example.millennialnews;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private Switch switchMode;
    SaveState saveState;

    private EditText firstName, lastName, password, email, conf_passwd;
    private Button signUpBtn;
    private DatabaseReference db;

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

        setContentView(R.layout.activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        signUpBtn = (Button) findViewById(R.id.reg_button);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        conf_passwd = (EditText) findViewById(R.id.confirm_password);

        db = FirebaseDatabase.getInstance().getReference("users");
        Log.i("DATABASE", db.toString());

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), LoginActivity.class);

                db.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String userFirstName = firstName.getText().toString().trim();
                        String userLastName = lastName.getText().toString().trim();
                        String userEmail = email.getText().toString().trim();
                        String userPassword = password.getText().toString().trim();
//              String reg_conPassWd = conf_passwd.getText().toString().trim();
                        User user;
                        boolean doesUserExist = false;

                        int counter = 0;
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            user = ds.getValue(User.class);
                            if (user != null) {
                                if (user.getEmail().equals(userEmail)) {
                                    Toast.makeText(RegisterActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                                    doesUserExist = true;
                                }
                            }
                            counter++;
                        }
                        if (!doesUserExist) {
                            if (userFirstName.isEmpty()) {
                                firstName.setError("Please enter the First Name");
                                Log.w("DATABASE", "First name is empty");
                            } else if (userLastName.isEmpty()) {
                                lastName.setError("Please enter the Last Name");
                            } else if (userEmail.isEmpty()) {
                                email.setError("Please enter the Email");
                            } else if (userPassword.isEmpty()) {
                                password.setError("Please enter a Password");
                            } else {
                                // If none of the values are empty,
                                // add user to the Database and show a Toast with confirmation
                                User newUser = new User(
                                        userFirstName,
                                        userLastName,
                                        userPassword,
                                        userEmail
                                );
                                db.child(String.valueOf(counter)).setValue(newUser);
                                firstName.setText("");
                                lastName.setText("");
                                email.setText("");
                                password.setText("");
                                Toast.makeText(RegisterActivity.this, "Successfully registered!", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            }
                        }
                        Toast.makeText(RegisterActivity.this, "Successfully registered! outter 1", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("DATABASE", "Failed to read value.", error.toException());
                    }
                });
                Toast.makeText(RegisterActivity.this, "Successfully registered! outter", Toast.LENGTH_LONG).show();

            }
        });
    }
}

        // TO ADD IN REGISTER ACTIVITY
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






