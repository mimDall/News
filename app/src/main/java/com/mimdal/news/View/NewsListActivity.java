package com.mimdal.news.View;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.mimdal.news.Adapter.NewsAdapter;
import com.mimdal.news.Interfaces.NewsListContract;
import com.mimdal.news.Interfaces.OnItemClickRecyclerView;
import com.mimdal.news.Model.NewsItem;
import com.mimdal.news.Presenter.NewsListPresenter;
import com.mimdal.news.R;
import com.mimdal.news.service.GetDataFromAPI;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;

public class NewsListActivity extends AppCompatActivity implements NewsListContract.View, OnItemClickRecyclerView {
    private static final String TAG = "MainActivity";

    @BindView(R.id.tv_noNetwork)
    TextView tv_noNetwork;

    @BindView(R.id.loadNewsProgressBar)
    ProgressBar loadNewsProgressBar;

    @BindView(R.id.searchEditText)
    EditText searchEditText;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.newsListRecycler)
    RecyclerView newsListRecycler;

    @BindView(R.id.bottom_sheet_filter)
    ConstraintLayout bottom_sheet_filter;
//    bottom sheet views

    @BindView(R.id.bottom_sheet_topic_spinner)
    Spinner bottom_sheet_topic_spinner;

    @BindView(R.id.bottom_sheet_country_spinner)
    Spinner bottom_sheet_country_spinner;

    @BindView(R.id.bottom_sheet_language_spinner)
    Spinner bottom_sheet_language_spinner;

    @BindView(R.id.bottom_sheet_applyBtn)
    Button bottom_sheet_btn;

    @BindView(R.id.leftArrow)
    ImageView leftArrow;
    @BindView(R.id.rightArrow)
    ImageView rightArrow;

    NewsListPresenter newsListPresenter;
    NewsAdapter newsAdapter;
    BottomSheetBehavior bottomSheetBehavior;
    int topicSpinnerPosition = 0;
    int countrySpinnerPosition = 0;
    int languageSpinnerPosition = 0;
    private List<NewsItem> backUpData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        /*
            following line is written due to prevent open soft keyboard
            in app startup while search edittext has focus
         */
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        newsListPresenter = new NewsListPresenter(this, new GetDataFromAPI(this));
        newsListPresenter.checkInternetConnectivity();

        newsListPresenter.initRecyclerView();

        newsListPresenter.getData(topicSpinnerPosition, countrySpinnerPosition, languageSpinnerPosition);

        newsListPresenter.initBottomSheet();
        newsListPresenter.initSpinners();
        newsListPresenter.swipeRefreshLayout();

        //0 : visible
        //4 : invisible
    }

    @OnTextChanged(value = R.id.searchEditText, callback = OnTextChanged.Callback.TEXT_CHANGED)
    void onTextChange(CharSequence text) {
        newsListPresenter.initDisplayFilteredNews(text);

        if (searchEditText.getText().toString().equals("")) {
            Log.d(TAG, "back up data is called");
            newsAdapter.setData(backUpData);
        }
    }

    @OnItemSelected(R.id.bottom_sheet_topic_spinner)
    void onTopicSpinnerItemSelect(int position) {
        topicSpinnerPosition = position;
        Log.i(TAG, "topicSpinnerPosition: " + position);
    }

    @OnItemSelected(R.id.bottom_sheet_country_spinner)
    void onCountrySpinnerItemSelect(int position) {
        countrySpinnerPosition = position;
        Log.i(TAG, "countrySpinnerPosition: " + position);

    }

    @OnItemSelected(R.id.bottom_sheet_language_spinner)
    void onLanguageSpinnerItemSelect(int position) {
        languageSpinnerPosition = position;
        Log.i(TAG, "languageSpinnerPosition: " + position);

    }

    @OnClick(R.id.bottom_sheet_applyBtn)
    void onBottomSheetBtnClick() {


        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        newsListPresenter.getData(topicSpinnerPosition, countrySpinnerPosition, languageSpinnerPosition);
    }

    @Override
    public void OnPrepareRecyclerView() {

        newsAdapter = new NewsAdapter(this);
        newsListRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        newsListRecycler.setAdapter(newsAdapter);

    }

    @Override
    public void OnUpdateRecyclerView(List<NewsItem> newsItem) {
        newsAdapter.setData(newsItem);
        backUpData = newsItem;
    }

    @Override
    public void onSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (onCheckInternetConnectivity()) { // check internet connection
                    if (searchEditText.getVisibility() == View.INVISIBLE) {
                        manageViewsVisibility(0, 4);
                    }
                    newsListPresenter.getData(topicSpinnerPosition, countrySpinnerPosition, languageSpinnerPosition);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void manageViewsVisibility(int activityViewsVisibility, int noInternetTvVisibility) {
        searchEditText.setVisibility(activityViewsVisibility);
        newsListRecycler.setVisibility(activityViewsVisibility);
        bottom_sheet_filter.setVisibility(activityViewsVisibility);
        tv_noNetwork.setVisibility(noInternetTvVisibility);
    }


    @Override
    public void progressBarVisibility(int visibility) {
        loadNewsProgressBar.setVisibility(visibility);
    }

    @Override
    public void progressBarVisibility(boolean visibility) {
        swipeRefreshLayout.setRefreshing(visibility);
    }

    @Override
    public boolean onCheckInternetConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void OnDisplayFilteredNews(CharSequence searchedQuery) {

        newsAdapter.getFilter().filter(searchedQuery);
    }

    @Override
    public void OnShowErrorMessageInGetData(String errorMessage) {

        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void OnBottomSheetCreate() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_filter);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {

                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                Log.d(TAG, "onSlide is calling, slideOffset: " + slideOffset);
                if (slideOffset > 0) {

                    rotateBottomSheetArrows(slideOffset);
                }

            }
        });

    }

    private void rotateBottomSheetArrows(float slideOffset) {

        leftArrow.setRotation(slideOffset * 180);
        rightArrow.setRotation(slideOffset * -180);

    }

    @Override
    public void OnSpinnerCreated(String[] topics, String[] countries, String[] languages) {
        ArrayAdapter<String> topicAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, topics);
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        //apply adapter to spinners
        bottom_sheet_topic_spinner.setAdapter(topicAdapter);
        bottom_sheet_country_spinner.setAdapter(countryAdapter);
        bottom_sheet_language_spinner.setAdapter(languageAdapter);
    }


    @Override
    public void OnItemClick(String url, String imageURL, String title, String date) {
        Intent intent = new Intent(NewsListActivity.this, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("imageUrl", imageURL);
        intent.putExtra("title", title);
        intent.putExtra("date", date);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }
}



