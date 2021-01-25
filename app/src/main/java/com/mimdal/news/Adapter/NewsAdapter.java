package com.mimdal.news.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mimdal.news.Adapter.Filter.ItemFilter;
import com.mimdal.news.Interfaces.NewsListContract;
import com.mimdal.news.Interfaces.OnItemClickRecyclerView;
import com.mimdal.news.Model.NewsItem;
import com.mimdal.news.R;
import com.mimdal.news.Utils.DateUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> implements Filterable {

    private List<NewsItem> newsList;
    private ItemFilter filterNewsList;
    private OnItemClickRecyclerView onItemClickRecyclerView;
    private static final String TAG = "NewsAdapter";

    public NewsAdapter(OnItemClickRecyclerView onItemClickRecyclerView) {
        this.onItemClickRecyclerView = onItemClickRecyclerView;
        this.newsList = new ArrayList<>();
        this.filterNewsList = new ItemFilter(this);
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        NewsItem newsItem = newsList.get(position);
        String title = newsItem.getTitle();
        String description = newsItem.getDescription();
        String imageUrl = newsItem.getImageUrl();
        String publishedAt = newsItem.getPublishedAt();
        String sourceName = newsItem.getSourceName();

        Picasso.get().load(imageUrl).into(holder.itemImage);
        holder.itemTitle.setText(title);
        holder.itemDescription.setText(description);
        holder.itemDate.setText(DateUtil.FormatDate(publishedAt));
        holder.itemSourceName.setText(sourceName);

    }


    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public Filter getFilter() {
        return this.filterNewsList;
    }

    public void setData(List<NewsItem> newList) {
        newsList.clear();
        this.newsList.addAll(newList);
        notifyDataSetChanged();
        filterNewsList.setData(newList);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemView)
        View itemView;

        @BindView(R.id.itemImage)
        ImageView itemImage;

        @BindView(R.id.itemTitle)
        TextView itemTitle;

        @BindView(R.id.itemDescription)
        TextView itemDescription;

        @BindView(R.id.itemDate)
        TextView itemDate;

        @BindView(R.id.itemSourceName)
        TextView itemSourceName;


        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.itemView)
        void itemViewClicked() {

            onItemClickRecyclerView.OnItemClick(newsList.get(getAdapterPosition()).getUrl(),
                    newsList.get(getAdapterPosition()).getImageUrl(),
                    newsList.get(getAdapterPosition()).getTitle(),
                    newsList.get(getAdapterPosition()).getPublishedAt());

            // TODO:handle click

        }


    }

}
