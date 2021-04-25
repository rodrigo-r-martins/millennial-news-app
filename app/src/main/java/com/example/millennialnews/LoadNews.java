package com.example.millennialnews;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Source;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class LoadNews extends AsyncTask<String, Void, List<NewsArticle>> {
    @SuppressLint("StaticFieldLeak")
    NewsAdapter newsAdapter;
    List<NewsArticle> articleList;
    List<NewsArticle> articleListSearch;

    public LoadNews(List<NewsArticle> articleList, List<NewsArticle> articleListSearch, NewsAdapter newsAdapter) {
        this.articleList = articleList;
        this.newsAdapter = newsAdapter;
        this.articleListSearch = articleListSearch;
    }

    @Override
    protected List<NewsArticle> doInBackground(String... strings) {
        NewsApiClient newsApiClient = new NewsApiClient("8b88ab2e81df4547abfc23f6fc69311c");
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q(strings[0])
                        .sortBy("popularity")
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
                            Log.d("ON DO IN BACKGROUND", article.toString());
                        }
                    }
                    @Override
                    public void onFailure(Throwable throwable) {
                        System.out.println(throwable.getMessage());
                    }
                }
        );
        return articleListSearch;
//        Source source = new Source();
//        source.setName("tesla");

//        List<NewsArticle> list = new ArrayList<>();
//        NewsArticle article1 = new NewsArticle(
//                source,
//                "https://www.engadget.com/about/editors/steve-dent",
//                "Tesla customers say they've been double-charged for their cars",
//                "Tesla buyers have been reporting that they've been double-charged on cars for recent purchases and have had trouble contacting the company and getting their money back.",
//                "2021-03-30T08:25:15Z",
//                "https://s.yimg.com/os/creatr-uploaded-images/2021-03/734d2c60-912f-11eb-aecf-7d690fa16b7c");
//        NewsArticle article2 = new NewsArticle(
//                source,
//                "https://www.engadget.com/about/editors/steve-dent",
//                "Tesla customers say they've been double-charged for their cars",
//                "Tesla buyers have been reporting that they've been double-charged on cars for recent purchases and have had trouble contacting the company and getting their money back.",
//                "2021-03-30T08:25:15Z",
//                "https://s.yimg.com/os/creatr-uploaded-images/2021-03/734d2c60-912f-11eb-aecf-7d690fa16b7c");
//        list.add(article1);
//        list.add(article2);
//        return list;
    }

    @Override
    protected void onPostExecute(List<NewsArticle> newsArticles) {
        articleList.clear();
        Log.d("AFTER CLEAR", articleList.toString());
        articleList.addAll(newsArticles);
        Log.d("AFTER ADD ALL", articleList.toString());
        newsAdapter.notifyDataSetChanged();
    }
}

