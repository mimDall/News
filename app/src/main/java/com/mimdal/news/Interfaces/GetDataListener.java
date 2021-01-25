package com.mimdal.news.Interfaces;

import com.mimdal.news.Model.NewsItem;

import java.util.List;

public interface GetDataListener {

    void onSuccess(List<NewsItem> newsList);
    void onError(String message);
}
