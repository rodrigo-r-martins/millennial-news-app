package com.example.millennialnews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private final List<NewsArticle> newsList;

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
        String title = newsList.get(position).getTitle();
        int endPoint = title.lastIndexOf("-");
        String newTitle = title.substring(0, endPoint);
        holder.tvNewsTitle.setText(newTitle);
        holder.tvNewsAuthor.setText(String.format(
                "%s: { %s }",
                newsList.get(position).getSource().getName(),
                newsList.get(position).getAuthor()));
        holder.tvNewsDate.setText(newsList.get(position).getDate());
        LoadImageNews image = new LoadImageNews(holder.ivNewsImage);
        image.execute(newsList.get(position).getImage());

        holder.newsItemLayout.setOnClickListener(v ->
                Toast.makeText(v.getContext(), newsList.get(position).getTitle(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView tvNewsTitle;
        TextView tvNewsAuthor;
        TextView tvNewsDate;
        TextView tvNewsDescription;
        ImageView ivNewsImage;
        ConstraintLayout newsItemLayout;

        public NewsViewHolder(View itemView) {
            super(itemView);
            tvNewsTitle = itemView.findViewById(R.id.tvNewsTitle);
            tvNewsAuthor = itemView.findViewById(R.id.tvNewsAuthor);
            tvNewsDate = itemView.findViewById(R.id.tvNewsDate);
            ivNewsImage = itemView.findViewById(R.id.ivNewsImage);
            newsItemLayout = itemView.findViewById(R.id.newsItemLayout);
        }
    }
}
