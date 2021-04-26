package com.example.millennialnews;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final List<NewsArticle> newsList;
    private Intent intent;

    public NewsAdapter(List<NewsArticle> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        String fullTitle = newsList.get(position).getTitle();
//        int endPoint = fullTitle.lastIndexOf("-");
//        String title = fullTitle.substring(0, endPoint);
        holder.tvNewsTitle.setText(fullTitle);
        holder.tvNewsAuthor.setText(String.format(
                "%s: { %s }",
                newsList.get(position).getSource().getName(),
                newsList.get(position).getAuthor()));
        String fullDate = newsList.get(position).getDate();
        int endPointDate = fullDate.indexOf("T");
        String date = fullDate.substring(0, endPointDate);
        holder.tvNewsDate.setText(date);
        LoadImageNews image = new LoadImageNews(holder.ivNewsImage);
        image.execute(newsList.get(position).getImage());

        holder.newsItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(holder.itemView.getContext(), ArticleNewsActivity.class);
                intent.putExtra("title", fullTitle);
                intent.putExtra("author", newsList.get(position).getAuthor());
                intent.putExtra("date", date);
                intent.putExtra("content", newsList.get(position).getDescription());
                intent.putExtra("image", newsList.get(position).getImage());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView tvNewsTitle;
        TextView tvNewsAuthor;
        TextView tvNewsDate;
        ImageView ivNewsImage;
        ConstraintLayout newsItemLayout;

        public NewsViewHolder(View itemView) {
            super(itemView);
            tvNewsTitle = itemView.findViewById(R.id.tvNewsTitleFull);
            tvNewsAuthor = itemView.findViewById(R.id.tvNewsAuthorFull);
            tvNewsDate = itemView.findViewById(R.id.tvNewsDateFull);
            ivNewsImage = itemView.findViewById(R.id.ivNewsImageFull);
            newsItemLayout = itemView.findViewById(R.id.newsItemLayout);
        }
    }
}
