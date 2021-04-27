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
    private Switch switchMode;
    SaveState saveState;
    boolean isLoggedIn;
    boolean viewingArticle;

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
                getNews(queryString);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (!isLoggedIn)
        inflater.inflate(R.menu.menu, menu);
        else
            inflater.inflate(R.menu.menu2, menu);

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
            if (isLoggedIn) {
                item.setTitle("Log Out");
            }
            openLogInActivity(item);
            return true;
        }
        if (id == R.id.menuRegister) {
            openLogInActivity(item);
            return true;
        }
        if (id == R.id.menuLogOut) {
        isLoggedIn = false;

        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
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

    private void openRegisterActivity(MenuItem item) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

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

    @Override
    public void onResume() {
        super.onResume();
        isLoggedIn = getIntent().getBooleanExtra("isLoggedIn", false);
        if (!viewingArticle)
        invalidateOptionsMenu();
    }


        }