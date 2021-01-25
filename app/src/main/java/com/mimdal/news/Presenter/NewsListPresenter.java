package com.mimdal.news.Presenter;

import com.mimdal.news.Interfaces.NewsListContract;
import com.mimdal.news.Interfaces.GetDataListener;
import com.mimdal.news.Model.Model;
import com.mimdal.news.Model.NewsItem;
import com.mimdal.news.Model.SpinnersData;
import com.mimdal.news.service.GetDataFromAPI;
import java.util.List;

public class NewsListPresenter implements NewsListContract.Presenter, GetDataListener {

    NewsListContract.View view;
    Model model;
    GetDataFromAPI getDataFromAPI;

    public NewsListPresenter(NewsListContract.View view, GetDataFromAPI getDataFromAPI) {
        this.view = view;
        this.getDataFromAPI = getDataFromAPI;
        this.model = new Model(getDataFromAPI);

    }

    @Override
    public void checkInternetConnectivity() {
        if(!view.onCheckInternetConnectivity()){
            view.manageViewsVisibility(4, 0);
        }
    }

    @Override
    public void initRecyclerView(){
        view.OnPrepareRecyclerView();
    }
    @Override
    public void initBottomSheet() {
        view.OnBottomSheetCreate();
    }
    @Override
    public void initSpinners() {
        view.OnSpinnerCreated(SpinnersData.SPINNER_TOPICS, SpinnersData.SPINNER_COUNTRIES, SpinnersData.SPINNER_LANGUAGES);
    }


    @Override
    public void initDisplayFilteredNews(CharSequence searchedQuery){
        view.OnDisplayFilteredNews(searchedQuery);
    }

    @Override
    public void getData(int topicSpinnerPosition, int countrySpinnerPosition, int languageSpinnerPosition) {
        view.progressBarVisibility(true); // visible
        model.getData(this, topicSpinnerPosition, countrySpinnerPosition, languageSpinnerPosition);
    }

    @Override
    public void swipeRefreshLayout() {
        view.onSwipeRefreshLayout();
    }


    @Override
    public void onSuccess(List<NewsItem> newsList) {
        view.progressBarVisibility(false); // inVisible
        view.OnUpdateRecyclerView(newsList);
    }

    @Override
    public void onError(String message) {
        view.progressBarVisibility(false); // inVisible
        view.OnShowErrorMessageInGetData(message);
    }


}




