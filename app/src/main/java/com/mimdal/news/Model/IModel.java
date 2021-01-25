package com.mimdal.news.Model;

import com.mimdal.news.Interfaces.GetDataListener;

public interface IModel {

    void getData(GetDataListener getDataListener, int topicSpinnerPosition, int countrySpinnerPosition, int languageSpinnerPosition);
}
