package com.mimdal.news.Interfaces;

import com.mimdal.news.View.BaseView;

public interface WebViewContract {
    interface View extends BaseView {

        void onToolbarInit();
        void onGetBundle();
        void onShowMessage(String message);
        void onWebViewInit();
        void onCollapsingBarInit();
        void onOpenWithOtherBrowser(String webViewUrl);
        void onShareUrl(String url);
        void onCopyToClipBoard(String url);


    }

    interface Presenter{

        void initToolbar();
        void getBundle();
        void webViewInit();
        void collapsingBarInit();
        void openWithOtherBrowser(String webViewUrl);
        void shareUrl(String url);
        void copyToClipBoard(String url);


    }


}
