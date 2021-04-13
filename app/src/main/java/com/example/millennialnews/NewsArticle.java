package com.example.millennialnews;

import com.kwabenaberko.newsapilib.models.Source;

public class NewsArticle {

    private Source source;
    private String author;
    private String title;
    private String description;
    private String content;
    private String date;
    private String image;

    public NewsArticle(
            Source source,
            String author,
            String title,
            String description,
            String date,
            String image
    ) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.date = date;
        this.image = image;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "News {" +
                "source='" + source.getName() + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
