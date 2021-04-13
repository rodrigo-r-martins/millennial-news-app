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
import android.widget.Button;
import android.widget.EditText;
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

        initRecyclerView();
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

    private void initRecyclerView() {
        List<News> newsList = new ArrayList<>();
        newsList.add(new News(
                "MobileSyrup",
                "Brad Bennett",
                "Everything we know about Tesla’s Solar Roof tiles coming to Canada",
                "Elon Musk’s much-hyped Tesla Solar panels are expected to arrive in Canada this year, but many specifications still remain elusive. With that in mind, below is everything we know about the solar panels so far.",
                "2021-04-12T22:38:12Z",
                "https://mobilesyrup.com/wp-content/uploads/2020/09/tesla-solar-roof-scaled.jpg"
        ));
        newsList.add(new News(
                "MobileSyrup",
                "Brad Bennett",
                "Everything we know about Tesla’s Solar Roof tiles coming to Canada",
                "Elon Musk’s much-hyped Tesla Solar panels are expected to arrive in Canada this year, but many specifications still remain elusive. With that in mind, below is everything we know about the solar panels so far.",
                "2021-04-12T22:38:12Z",
                "https://mobilesyrup.com/wp-content/uploads/2020/09/tesla-solar-roof-scaled.jpg"
        ));
        newsList.add(new News(
                "MobileSyrup",
                "Brad Bennett",
                "Everything we know about Tesla’s Solar Roof tiles coming to Canada",
                "Elon Musk’s much-hyped Tesla Solar panels are expected to arrive in Canada this year, but many specifications still remain elusive. With that in mind, below is everything we know about the solar panels so far.",
                "2021-04-12T22:38:12Z",
                "https://mobilesyrup.com/wp-content/uploads/2020/09/tesla-solar-roof-scaled.jpg"
        ));
        newsList.add(new News(
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
}