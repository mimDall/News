package com.mimdal.news.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.mimdal.news.Interfaces.WebViewContract;
import com.mimdal.news.Presenter.WebViewPresenter;
import com.mimdal.news.R;
import com.mimdal.news.Utils.CustomWebViewClient;
import com.mimdal.news.Utils.DateUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity implements WebViewContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webViewProgressBar)
    ProgressBar webViewProgressBar;
    @BindView(R.id.webView)
    WebView webView;

    @BindView(R.id.collapsingImage)
    ImageView collapsingImage;

    @BindView(R.id.collapsingTitle)
    TextView collapsingTitle;

    @BindView(R.id.collapsingDate)
    TextView collapsingDate;

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;


    private static final String TAG = "WebViewActivity";

    String url = null;
    String imageUrl = null;
    String title = null;
    String date = null;


    WebViewPresenter webViewPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        webViewPresenter = new WebViewPresenter(this);
        webViewPresenter.getBundle();
        webViewPresenter.initToolbar();
        webViewPresenter.collapsingBarInit();
        webViewPresenter.webViewInit();

    }

    @Override
    public void onGetBundle() {
        url = getIntent().getStringExtra("url");
        imageUrl = getIntent().getStringExtra("imageUrl");
        title = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("date");

        Log.d(TAG, url);
    }


    @Override
    public void onToolbarInit() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(TAG, "offset: " + verticalOffset);
                Log.d(TAG, "scroll range: " + appBarLayout.getTotalScrollRange());
                if (appBarLayout.getTotalScrollRange() - Math.abs(verticalOffset) <= 10) {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(title);
                        getSupportActionBar().setSubtitle(url);
                    }
                } else {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle("");
                        getSupportActionBar().setSubtitle("");
                    }
                }

            }
        });


    }

    @Override
    public void onCollapsingBarInit() {
        Picasso.get().load(imageUrl).into(collapsingImage);
        collapsingTitle.setText(title);
        collapsingDate.setText(DateUtil.FormatDate(date));
    }

    @Override
    public void onOpenWithOtherBrowser(String webViewUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(webViewUrl));
        startActivity(intent);
    }

    @Override
    public void onShareUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(intent, "Share via..."));
    }

    @Override
    public void onCopyToClipBoard(String url) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(null, url);
        clipboard.setPrimaryClip(clipData);
    }


    @Override
    public void onShowMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWebViewInit() {

        webView.setWebViewClient(new CustomWebViewClient(this));
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(true);
        webView.loadUrl(url);

    }

    @Override
    public void progressBarVisibility(int visibility) {
        webViewProgressBar.setVisibility(visibility);

    }

    @Override
    public void progressBarVisibility(boolean visibility) {

    }

    /*
        go to previous activity by clicking on back button in toolbar
     */
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return super.onSupportNavigateUp();
//    }

    @Override
    public void onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.menuRefresh:
                webView.reload();
                break;
            case R.id.menuCopy:
                webViewPresenter.copyToClipBoard(webView.getUrl());
                break;
            case R.id.menuShare:
                webViewPresenter.shareUrl(webView.getUrl());
                break;
            case R.id.menuOpenWith:
                webViewPresenter.openWithOtherBrowser(webView.getUrl());
                break;
            case android.R.id.home:
                onBackPressed();
                break;

        }
        return true;
    }

}