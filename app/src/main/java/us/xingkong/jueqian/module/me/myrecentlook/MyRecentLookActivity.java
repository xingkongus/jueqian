package us.xingkong.jueqian.module.me.myrecentlook;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.MyAnswerAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.module.me.myanswer.MyAnswerActivity;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MyRecentLookActivity extends BaseActivity<MyRecentLookContract.Presenter> implements MyRecentLookContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private ArrayList<String> mArrayList;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    break;
            }

        }
    };

    @Override
    protected MyRecentLookContract.Presenter createPresenter() {
        return new MyRecentLookPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_myrecentlook;
    }

    @Override
    protected void prepareData() {
        if (mArrayList != null) {
            mArrayList.clear();
        }
        mArrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mArrayList.add("世界上有没有傻逼?" + i);
        }
    }

    @Override
    protected void initView() {
        setToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MyAnswerAdapter(mArrayList, mHandler));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MyRecentLookActivity.this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setToolbar() {
        ActionBar acb = getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("最近浏览");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
