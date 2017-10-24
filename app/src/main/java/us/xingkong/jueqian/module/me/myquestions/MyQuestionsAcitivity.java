package us.xingkong.jueqian.module.me.myquestions;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.MyQuestionsAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;

/**
 * Created by PERFECTLIN on 2017/1/13 0013.
 */

public class MyQuestionsAcitivity extends BaseActivity<MyQuestionsContract.Presenter> implements MyQuestionsContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.framelayout)
    FrameLayout frameLayout;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    List<Question> questions = new ArrayList<>();
    private MyQuestionsAdapter myQuestionsAdapter;
    private static boolean isInit = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (!isInit)
                        initRecyclerView();
                    else {
                        if (myQuestionsAdapter == null)
                            myQuestionsAdapter = new MyQuestionsAdapter(mHandler, questions, MyQuestionsAcitivity.this);
                        myQuestionsAdapter.notifyDataSetChanged();
                    }
                    if (mSwipeRefreshLayout == null)
                        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
                    mSwipeRefreshLayout.setRefreshing(false);
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
        getQuestion();
    }

    private void getQuestion() {
        if (mSwipeRefreshLayout == null)
            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setRefreshing(true);
        BmobUser bmobUser = BmobUser.getCurrentUser(JueQianAPP.getAppContext());
        BmobQuery<Question> query = new BmobQuery<Question>();
        query.addWhereRelatedTo("questions", new BmobPointer(bmobUser));
        query.order("createdAt");
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(JueQianAPP.getAppContext(), new FindListener<Question>() {
            @Override
            public void onSuccess(List<Question> list) {
                if (list.size() == 0) {
                    if (frameLayout == null)
                        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
                    frameLayout.setVisibility(View.VISIBLE);
                    return;
                }
                if (isInit) questions.clear();
                questions = list;
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onError(int i, String s) {
                if (mSwipeRefreshLayout == null)
                    mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
                mSwipeRefreshLayout.setRefreshing(false);
                showToast("获取我的提问列表失败CASE:" + s);
            }
        });
    }

    @Override
    protected void initView() {
        setToolbar();
    }

    private void initRecyclerView() {
        if (mRecyclerView == null) mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myQuestionsAdapter = new MyQuestionsAdapter(mHandler, questions, this);
        mRecyclerView.setAdapter(myQuestionsAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MyQuestionsAcitivity.this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        isInit = true;

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
        if (mSwipeRefreshLayout == null)
            mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setProgressViewEndTarget(true, 200);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getQuestion();
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
