package us.xingkong.jueqian.module.me.mycollection;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.MyCollectionAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MyCollectionActivity extends BaseActivity<MyCollectionContract.Presenter> implements MyCollectionContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.framelayout)
    FrameLayout frameLayout;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private MyCollectionAdapter myCollectionAdapter;
    private List<Question> questions;
    private List<Question> questions_adapter = new ArrayList<>();
    private String intentUserID;
    private static boolean isInit = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (isInit) {
                        questions_adapter.clear();
                        questions = questions_adapter;
                        if (myCollectionAdapter == null)
                            myCollectionAdapter = new MyCollectionAdapter(mHandler, questions_adapter, MyCollectionActivity.this);
                        myCollectionAdapter.notifyDataSetChanged();
                    } else {
                        initRecyclerView();
                    }
                    if (mSwipeRefreshLayout == null)
                        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
                    mSwipeRefreshLayout.setRefreshing(false);
                case 2:
                    myCollectionAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    protected MyCollectionContract.Presenter createPresenter() {
        return new MyCollectionPresenter(this, mHandler);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_mycollection;
    }

    @Override
    protected void prepareData() {
        if (mSwipeRefreshLayout == null)
            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setRefreshing(true);
        Intent intent = getIntent();
        intentUserID = intent.getStringExtra("intentUserID");
        getCollection();
    }

    private void getCollection() {
        _User user = new _User();
        user.setObjectId(intentUserID);
        BmobQuery<Question> query = new BmobQuery<Question>();
        query.addWhereRelatedTo("collections", new BmobPointer(user));
        query.order("createdAt");
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(JueQianAPP.getAppContext(), new FindListener<Question>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(List<Question> list) {
                questions = new ArrayList<>();
                questions = list;
                if (questions.size() == 0) {
                    if (frameLayout == null)
                        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
                    frameLayout.setVisibility(View.VISIBLE);
                    return;
                }
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onError(int i, String s) {
                if (mSwipeRefreshLayout == null)
                    mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
                mSwipeRefreshLayout.setRefreshing(false);
                showToast("获取收藏表失败CASE:+" + s);
            }
        });
    }

    @Override
    protected void initView() {
        setToolbar();
    }

    private void initRecyclerView() {
        if (mRecyclerView == null) mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        myCollectionAdapter = new MyCollectionAdapter(mHandler, questions, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(myCollectionAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MyCollectionActivity.this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        isInit = true;

    }

    private void setToolbar() {

        ActionBar acb = getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("收藏");

    }

    @Override
    protected void initData(Bundle savedInstanceState) {


    }

    @Override
    protected void initEvent() {
        if (mSwipeRefreshLayout == null)
            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setProgressViewEndTarget(true, 200);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCollection();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                isInit=false;
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isInit = false;
    }
}
