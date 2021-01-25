package com.mimdal.news.Presenter;

import com.mimdal.news.Interfaces.WebViewContract;

public class WebViewPresenter implements WebViewContract.Presenter {
    WebViewContract.View view;

    public WebViewPresenter(WebViewContract.View view) {
        this.view = view;
    }


    @Override
    public void initToolbar() {
        view.onToolbarInit();
    }

    @Override
    public void getBundle() {
        view.onGetBundle();

    }

    @Override
    public void webViewInit() {
        view.onWebViewInit();

    }

    @Override
    public void collapsingBarInit() {
        view.onCollapsingBarInit();
    }

    @Override
    public void openWithOtherBrowser(String webViewUrl) {
        view.onOpenWithOtherBrowser(webViewUrl);
    }

    @Override
    public void shareUrl(String url) {
        view.onShareUrl(url);
    }

    @Override
    public void copyToClipBoard(String url) {
        view.onCopyToClipBoard(url);
        if (url != null) {
            view.onShowMessage("Url copied to clipboard");
        } else {
            view.onShowMessage("Url not found!");
        }
    }
}
