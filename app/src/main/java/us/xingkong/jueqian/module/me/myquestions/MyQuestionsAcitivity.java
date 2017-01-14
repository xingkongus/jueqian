package us.xingkong.jueqian.module.me.myquestions;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.MyQuestionsAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.module.me.mycollection.MyCollectionActivity;

/**
 * Created by PERFECTLIN on 2017/1/13 0013.
 */

public class MyQuestionsAcitivity extends BaseActivity<MyQuestionsContract.Presenter> implements MyQuestionsContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private ArrayList<String> mArrayList;
    private Handler handler = new Handler() {
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
    protected MyQuestionsContract.Presenter createPresenter() {
        return new MyQuestionPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_myquestions;
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
        mRecyclerView.setAdapter(new MyQuestionsAdapter(handler, mArrayList));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MyQuestionsAcitivity.this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void setToolbar() {
        ActionBar acb = getSupportActionBar();
        acb.setTitle(R.string.text_myquestions);
        acb.setDisplayHomeAsUpEnabled(true);
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
