package com.mimdal.news.Interfaces;

import com.mimdal.news.Model.NewsItem;
import com.mimdal.news.View.BaseView;
import com.mimdal.news.View.IViewNewsList;

import java.util.List;

public interface NewsListContract {

    interface View extends BaseView {

        boolean onCheckInternetConnectivity();
        void OnDisplayFilteredNews(CharSequence searchedQuery);

        void OnShowErrorMessageInGetData(String errorMessage);

        void OnBottomSheetCreate();

        void OnSpinnerCreated(String[] topics, String[] countries, String[] languages);

        void OnPrepareRecyclerView();

        void OnUpdateRecyclerView(List<NewsItem> newsItem);
        void onSwipeRefreshLayout();
        void manageViewsVisibility(int activityViewsVisibility, int noInternetTvVisibility);


    }

    interface Presenter {

        void checkInternetConnectivity();
        void initRecyclerView();

        void initBottomSheet();

        void initSpinners();

        void initDisplayFilteredNews(CharSequence searchedQuery);

        void getData(int topicSpinnerPosition, int countrySpinnerPosition, int languageSpinnerPosition);
        void swipeRefreshLayout();


    }
}
