package us.xingkong.jueqian.module.me.myanswer;

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
import us.xingkong.jueqian.adapter.MyAnswerAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.module.me.mycollection.MyCollectionActivity;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MyAnswerActivity extends BaseActivity<MyAnswerContract.Presenter> implements MyAnswerContract.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.framelayout)
    FrameLayout frameLayout;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<Answer> answers = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    for (Answer answer : answers) {
                        Question q = answer.getQuestion();
                        questions.add(q);
                    }
                    if (questions == null) {
                        showToast("回答列表为空");
                        break;
                    }
                    initRecyclerView();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }

        }
    };

    private MyAnswerAdapter myAnswerAdapter;

    @Override
    protected MyAnswerContract.Presenter createPresenter() {
        return new MyAnswerPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_myanswer;
    }

    @Override
    protected void prepareData() {
        getAnswer();
    }

    private void getAnswer() {
        mSwipeRefreshLayout.setRefreshing(true);
        BmobUser bmobUser = BmobUser.getCurrentUser(JueQianAPP.getAppContext());
        BmobQuery<Answer> query = new BmobQuery<Answer>();
        query.addWhereRelatedTo("answers", new BmobPointer(bmobUser));
        query.include("question");
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(JueQianAPP.getAppContext(), new FindListener<Answer>() {
            @Override
            public void onSuccess(List<Answer> list) {
                if (list.size() == 0) {
                    frameLayout.setVisibility(View.VISIBLE);
                    return;
                }
                answers = list;
                if (answers == null) {
                    return;
                }
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onError(int i, String s) {
                mSwipeRefreshLayout.setRefreshing(false);
                showToast("获取我的回答列表失败");
            }
        });
    }

    @Override
    protected void initView() {
        setToolbar();
//        initRecyclerView();
    }

    private void initRecyclerView() {
        myAnswerAdapter = new MyAnswerAdapter(questions, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(myAnswerAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MyAnswerActivity.this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setToolbar() {
        ActionBar acb = getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("我的回答");

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefreshLayout.setProgressViewEndTarget(true, 200);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAnswer();
            }
        });
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
