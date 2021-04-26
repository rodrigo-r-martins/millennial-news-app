package com.example.millennialnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Source;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnSearch;
    private EditText etSearch;
    private RecyclerView rvNews;
    private LoadNews loadNews;
    private EditText etUserFirstname;
    private EditText etUserLastname;
    private EditText etUserId;
    private EditText etUserEmail;
    private EditText etUserPassword;
    private DatabaseReference db;
    private User currentUser;

    private Switch switchMode;
    SaveState saveState;

    private List<NewsArticle> articleList  = new ArrayList<>();
    private List<NewsArticle> articleListSearch  = new ArrayList<>();
    private NewsAdapter newsAdapter;

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

        setContentView(R.layout.activity_main);

        // === INITIALIZING VARIABLES ===
        btnSearch = findViewById(R.id.btnSearch);
        etSearch = findViewById(R.id.etSearch);
        rvNews = findViewById(R.id.rvNews);
//        etUserFirstname = findViewById(R.id.etUserFirstname);
//        etUserLastname = findViewById(R.id.etUserLastname);
//        etUserId = findViewById(R.id.etUserId);
//        etUserEmail = findViewById(R.id.etUserEmail);
//        etUserPassword = findViewById(R.id.etUserPassword);

        etSearch.requestFocus();
        // === LOADING ARTICLES TO THE MAIN PAGE ===
        articleList = getFreshNews();
        newsAdapter = new NewsAdapter(articleList);
        rvNews.setAdapter(newsAdapter);
        rvNews.setLayoutManager(new LinearLayoutManager(this));

        // === LOADING ARTICLES FROM THE SEARCH BAR ===
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryString = etSearch.getText().toString();
//                List<NewsArticle> articleListSearch = new ArrayList<>();
//                loadNews = new LoadNews(articleList, articleListSearch, newsAdapter);
//                loadNews.execute(queryString);
                getNews(queryString);
            }
        });

        // === DATABASE ===

        // TO ADD IN LOGIN ACTIVITY
//        db = FirebaseDatabase.getInstance().getReference("users");
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String userEmail = etUserEmail.getText().toString();
//                String userPassword = etUserPassword.getText().toString();
//                User user;
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    user = ds.getValue(User.class);
//                    if (user != null) {
//                        if (user.getEmail().equals(userEmail) && user.getPassword().equals(userPassword)) {
//                            currentUser = user;
//                        } else {
//                            Toast.makeText(MainActivity.this, "Wrong credentials. Try again", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("DATABASE", "Failed to read value.", error.toException());
//            }
//        });
//
//        // TO ADD IN REGISTER ACTIVITY
//        db = FirebaseDatabase.getInstance().getReference("users");
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String userEmail = etUserEmail.getText().toString();
//                String userPassword = etUserPassword.getText().toString();
//                String userFirstName = etUserFirstname.getText().toString();
//                String userLastName = etUserLastname.getText().toString();
//                User user;
//                boolean doesUserExist = false;
//                int counter = 0;
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    user = ds.getValue(User.class);
//                    if (user != null) {
//                        if (user.getEmail().equals(userEmail)) {
//                            Toast.makeText(MainActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
//                            doesUserExist = true;
//                        }
//                    }
//                    counter++;
//                }
//
//                if (!doesUserExist) {
//                    if (userFirstName.isEmpty()) {
//                        etUserFirstname.setError("Please enter the First Name");
//                    } else if (userLastName.isEmpty()) {
//                        etUserLastname.setError("Please enter the Last Name");
//                    } else if (userEmail.isEmpty()) {
//                        etUserEmail.setError("Please enter the Email");
//                    } else if (userPassword.isEmpty()) {
//                        etUserPassword.setError("Please enter a Password");
//                    } else {
//                        // If none of the values are empty,
//                        // add user to the Database and show a Toast with confirmation
//                        User newUser = new User(
//                                userFirstName,
//                                userLastName,
//                                userEmail,
//                                userPassword
//                        );
//                        db.child(String.valueOf(counter)).setValue(newUser);
//                        etUserFirstname.setText("");
//                        etUserLastname.setText("");
//                        etUserEmail.setText("");
//                        etUserPassword.setText("");
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("DATABASE", "Failed to read value.", error.toException());
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuSettings) {
            openSettingsActivity(item);
            return true;
        }
        if (id == R.id.menuLogIn) {
            openLogInActivity(item);
            return true;
        }
        if (id == R.id.menuRegister) {
            openLogInActivity(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openLogInActivity(MenuItem item) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openSettingsActivity(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

//    private void openRegisterActivity(MenuItem item) {
//        Intent intent = new Intent(this, RegisterActivity.class);
//        startActivity(intent);
//    }

    public List<NewsArticle> getFreshNews() {
        List<NewsArticle> articleList = new ArrayList<>();
        NewsApiClient newsApiClient = new NewsApiClient("8b88ab2e81df4547abfc23f6fc69311c");
        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .country("us")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        for (int i = 0; i < response.getArticles().size(); i++) {
                            Source source = response.getArticles().get(i).getSource();
                            String author = response.getArticles().get(i).getAuthor();
                            String title = response.getArticles().get(i).getTitle();
                            String description = response.getArticles().get(i).getDescription();
                            String date = response.getArticles().get(i).getPublishedAt();
                            String image = response.getArticles().get(i).getUrlToImage();
                            NewsArticle article = new NewsArticle(source, author, title, description, date, image);
                            articleList.add(article);
                        }
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                });
        return articleList;
    }

    public void getNews(String queryString) {
        if (!articleListSearch.isEmpty()) {
            articleListSearch.clear();
        }
        NewsApiClient newsApiClient = new NewsApiClient("8b88ab2e81df4547abfc23f6fc69311c");
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q(queryString)
                        .sortBy("relevancy")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        for (int i = 0; i < response.getArticles().size(); i++) {
                            Source source = response.getArticles().get(i).getSource();
                            String author = response.getArticles().get(i).getAuthor();
                            String title = response.getArticles().get(i).getTitle();
                            String description = response.getArticles().get(i).getDescription();
                            String date = response.getArticles().get(i).getPublishedAt();
                            String image = response.getArticles().get(i).getUrlToImage();
                            NewsArticle article = new NewsArticle(source, author, title, description, date, image);
                            articleListSearch.add(article);
                        }
                        Log.d("getNewsALS", articleListSearch.toString());
                        Log.d("getNewsAL", articleList.toString());
                        articleList.clear();
                        articleList.addAll(articleListSearch);
                        Log.d("getNewsAfterAddAll", articleList.toString());
                        newsAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );
    }
}