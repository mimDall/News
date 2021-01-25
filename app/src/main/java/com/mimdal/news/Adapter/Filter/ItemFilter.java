package com.mimdal.news.Adapter.Filter;

import android.widget.Filter;

import com.mimdal.news.Adapter.NewsAdapter;
import com.mimdal.news.Model.NewsItem;

import java.util.ArrayList;
import java.util.List;

public class ItemFilter extends Filter {

    private NewsAdapter adapter;
    private List<NewsItem> newsListTotal;


    public ItemFilter(NewsAdapter adapter) {
        this.adapter = adapter;
        this.newsListTotal = new ArrayList<>();
    }

    public void setData(List<NewsItem> list){
        this.newsListTotal.clear();
        this.newsListTotal.addAll(list);
    }


    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        FilterResults filterResults = new FilterResults();
        //check input validity
        if(charSequence!=null && charSequence.length()>0){

            
            List<NewsItem> filteredNewsList = new ArrayList<>();

            for (int i = 0; i <newsListTotal.size() ; i++) {
                if(newsListTotal.get(i).getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())){

                    filteredNewsList.add(newsListTotal.get(i));
                }
            }

            filterResults.count=filteredNewsList.size();
            filterResults.values = filteredNewsList;

        }else{
            filterResults.count = newsListTotal.size();
            filterResults.values = newsListTotal;
        }

        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        adapter.setData((ArrayList<NewsItem>) filterResults.values);
        adapter.notifyDataSetChanged();

    }
}
