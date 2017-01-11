package us.xingkong.jueqian.module.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.base.BasePresenter;
import us.xingkong.jueqian.utils.BrowserUtils;
import us.xingkong.jueqian.utils.LogUtils;
import us.xingkong.jueqian.utils.ShareUtils;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/11 15:33
 */

public class WebActivity extends BaseActivity {

    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.loadingview)
    FrameLayout mLoadingview;

    private String url;
    private String desc;

    public static final String EXTRA_WEB_URL = "url";
    public static final String EXTRA_WEB_DESC = "desc";

    public static Intent newIntent(Context context, String url, String desc) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(WebActivity.EXTRA_WEB_URL, url);
        intent.putExtra(WebActivity.EXTRA_WEB_DESC, desc);
        context.startActivity(intent);
        return intent;
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void prepareData() {
        url = getIntent().getStringExtra(WebActivity.EXTRA_WEB_URL);
        desc = getIntent().getStringExtra(WebActivity.EXTRA_WEB_DESC);
    }

    @Override
    protected void initView() {
        setToolbarBackEnable(desc);
        setupWebView();
        setupSwipeRefreshLayout();
    }

    private void setupWebView() {
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mLoadingview.setVisibility(View.GONE);
                    url = mWebView.getUrl();
                } else {
                    mLoadingview.setVisibility(View.VISIBLE);
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebView.getSettings().setAppCacheEnabled(true);

        mWebView.loadUrl(url);
    }

    private void setupSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.timer(2, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                mWebView.loadUrl(url);
                mLoadingview.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void setPresenter(Object presenter) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                ShareUtils.shareText(this, mWebView.getTitle() + " " + mWebView.getUrl() + " 来自「觉浅」APP");
                break;
            case R.id.action_refresh:
                mWebView.reload();
                break;
            case R.id.action_copy:
                BrowserUtils.copyToClipBoard(this, mWebView.getUrl());
                break;
            case R.id.action_open_in_browser:
                BrowserUtils.openInBrowser(this, mWebView.getUrl());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) mWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) mWebView.onResume();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if (parent != null) {
                parent.removeView(mWebView);
            }
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        super.onDestroy();
    }
}
