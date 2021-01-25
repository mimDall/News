package com.mimdal.news.Model;

import android.util.Log;

import com.mimdal.news.Interfaces.GetDataListener;
import com.mimdal.news.service.Endpoints;
import com.mimdal.news.service.GetDataFromAPI;

public class Model implements IModel {

    GetDataFromAPI getDataFromAPI;
    private static final String TAG = "Model";

    public Model(GetDataFromAPI getDataFromAPI) {
        this.getDataFromAPI = getDataFromAPI;
    }

    private String createURL(int topicSpinnerPosition, int countrySpinnerPosition, int languageSpinnerPosition){

        Log.d(TAG, SpinnersData.SPINNER_TOPICS[topicSpinnerPosition]);
        Log.d(TAG, SpinnersData.COUNTRIES[countrySpinnerPosition]);
        Log.d(TAG, SpinnersData.LANGUAGES[languageSpinnerPosition]);

        return new Endpoints
                .Builder()
                .setSelectedTopic(SpinnersData.SPINNER_TOPICS[topicSpinnerPosition])
                .setSelectedCountry(SpinnersData.COUNTRIES[countrySpinnerPosition])
                .setSelectedLanguage(SpinnersData.LANGUAGES[languageSpinnerPosition])
                .build()
                .getNewsRequestURL();

    }

    @Override
    public void getData(GetDataListener getDataListener, int topicSpinnerPosition, int countrySpinnerPosition, int languageSpinnerPosition) {

        if(getDataFromAPI!=null){

            getDataFromAPI.getData(getDataListener, createURL(topicSpinnerPosition, countrySpinnerPosition, languageSpinnerPosition));
        }
    }
}
