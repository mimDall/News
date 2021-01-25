package com.mimdal.news.Model;

public class NewsItem {

    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private String publishedAt;
    private String sourceName;


    public NewsItem(String title, String description, String url, String image,String publishedAt, String sourceName) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = image;
        this.publishedAt = publishedAt;
        this.sourceName = sourceName;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
