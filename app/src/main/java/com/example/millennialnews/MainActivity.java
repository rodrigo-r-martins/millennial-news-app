package com.example.millennialnews;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = findViewById(R.id.btnSearch);
        etSearch = findViewById(R.id.etSearch);

        List<NewsArticle> articleList = getFreshNews();

        Log.d("MAIN_ACTIVITY", "initRecyclerView: init recyclerview");
        rvNews = findViewById(R.id.rvNews);
        NewsAdapter newsAdapter = new NewsAdapter(articleList);
        rvNews.setAdapter(newsAdapter);
        rvNews.setLayoutManager(new LinearLayoutManager(this));
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

    public List<NewsArticle> getFreshNews() {
        List<NewsArticle> articleList = new ArrayList<NewsArticle>();

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
                            String sourceName = source.getName();
                            String author = response.getArticles().get(i).getAuthor();
                            String title = response.getArticles().get(i).getTitle();
                            String description = response.getArticles().get(i).getDescription();
                            String date = response.getArticles().get(i).getPublishedAt();
                            String image = response.getArticles().get(i).getUrlToImage();

                            NewsArticle article = new NewsArticle(source, author, title, description, date, image);
                            articleList.add(article);
                            //System.out.println(response.getArticles().get(i).getTitle());
                            //System.out.println(response.getArticles().get(i).getAuthor());
                            //System.out.println(response.getArticles().get(i).getDescription());
                            //System.out.println(response.getArticles().get(i).getPublishedAt());
                            System.out.println(article.toString());
                            //Log.i("articlecount", String.valueOf(articleList.size()));
                            //Log.i("TEST", "SUCCESSFULLY ADDED TO LIST");
                        }
                        //Log.i("articlecount", String.valueOf(articleList.size()));
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }

                });

        return articleList;
    }



    public void getNews(View view) {
        String queryString = etSearch.getText().toString();

        List<NewsArticle> articleList = new ArrayList<NewsArticle>();

        NewsApiClient newsApiClient = new NewsApiClient("8b88ab2e81df4547abfc23f6fc69311c");

        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q(queryString)
                        .sortBy("popularity")
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {

                        for (int i = 0; i < response.getArticles().size(); i++) {

                            Source source = response.getArticles().get(i).getSource();
                            String sourceName = source.getName();
                            String author = response.getArticles().get(i).getAuthor();
                            String title = response.getArticles().get(i).getTitle();
                            String description = response.getArticles().get(i).getDescription();
                            String date = response.getArticles().get(i).getPublishedAt();
                            String image = response.getArticles().get(i).getUrlToImage();

                            NewsArticle article = new NewsArticle(source, author, title, description, date, image);
                            articleList.add(article);
                            //System.out.println(response.getArticles().get(i).getTitle());
                            //System.out.println(response.getArticles().get(i).getAuthor());
                            //System.out.println(response.getArticles().get(i).getDescription());
                            //System.out.println(response.getArticles().get(i).getPublishedAt());
                            System.out.println(article.toString());
                            Log.i("TEST", "SUCCESSFULLY ADDED TO LIST");
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );



        //return articleList;
    }



  /*  private void initRecyclerView() {

        List<NewsArticle> newsList = new ArrayList<>();

       newsList.add(new NewsArticle(
                ,
                "Brad Bennett",
                "Everything we know about Tesla’s Solar Roof tiles coming to Canada",
                "Elon Musk’s much-hyped Tesla Solar panels are expected to arrive in Canada this year, but many specifications still remain elusive. With that in mind, below is everything we know about the solar panels so far.",
                "2021-04-12T22:38:12Z",
                "https://mobilesyrup.com/wp-content/uploads/2020/09/tesla-solar-roof-scaled.jpg"
        ));
        newsList.add(new NewsArticle(
                "MobileSyrup",
                "Brad Bennett",
                "Everything we know about Tesla’s Solar Roof tiles coming to Canada",
                "Elon Musk’s much-hyped Tesla Solar panels are expected to arrive in Canada this year, but many specifications still remain elusive. With that in mind, below is everything we know about the solar panels so far.",
                "2021-04-12T22:38:12Z",
                "https://mobilesyrup.com/wp-content/uploads/2020/09/tesla-solar-roof-scaled.jpg"
        ));
        newsList.add(new NewsArticle(
                "MobileSyrup",
                "Brad Bennett",
                "Everything we know about Tesla’s Solar Roof tiles coming to Canada",
                "Elon Musk’s much-hyped Tesla Solar panels are expected to arrive in Canada this year, but many specifications still remain elusive. With that in mind, below is everything we know about the solar panels so far.",
                "2021-04-12T22:38:12Z",
                "https://mobilesyrup.com/wp-content/uploads/2020/09/tesla-solar-roof-scaled.jpg"
        ));
        newsList.add(new NewsArticle(
                "MobileSyrup",
                "Brad Bennett",
                "Everything we know about Tesla’s Solar Roof tiles coming to Canada",
                "Elon Musk’s much-hyped Tesla Solar panels are expected to arrive in Canada this year, but many specifications still remain elusive. With that in mind, below is everything we know about the solar panels so far.",
                "2021-04-12T22:38:12Z",
                "https://mobilesyrup.com/wp-content/uploads/2020/09/tesla-solar-roof-scaled.jpg"
        ));

        Log.d("MAIN_ACTIVITY", "initRecyclerView: init recyclerview");
        rvNews = findViewById(R.id.rvNews);
        NewsAdapter newsAdapter = new NewsAdapter(newsList);
        rvNews.setAdapter(newsAdapter);
        rvNews.setLayoutManager(new LinearLayoutManager(this));
    }
*/
}