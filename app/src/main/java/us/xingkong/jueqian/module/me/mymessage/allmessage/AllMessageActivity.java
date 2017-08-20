package us.xingkong.jueqian.module.me.mymessage.allmessage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
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
import us.xingkong.jueqian.adapter.MyMessageRecyclerAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.NewMessage;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class AllMessageActivity extends BaseActivity<AllMessageContract.Presenter> implements AllMessageContract.View {

    @BindView(R.id.allmessage_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.framelayout)
    FrameLayout frameLayout;

    List<NewMessage> messages = new ArrayList<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case 2:
                    initRecyclerView();
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected AllMessageContract.Presenter createPresenter() {
        return new AllMessagePresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_allmessage;
    }

    @Override
    protected void prepareData() {
        getMessage();
    }

    private void getMessage() {
        mSwipeRefreshLayout.setRefreshing(true);
        _User bmobUser = BmobUser.getCurrentUser(JueQianAPP.getAppContext(), _User.class);
        BmobQuery<NewMessage> query = new BmobQuery<>();
        query.addWhereEqualTo("receiver", new BmobPointer(bmobUser));
        query.include("sender,messComment.question,messAnswer.question");
        query.order("-createdAt");
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(JueQianAPP.getAppContext(), new FindListener<NewMessage>() {
            @Override
            public void onSuccess(List<NewMessage> list) {
                if (list.size() == 0) {
                    frameLayout.setVisibility(View.VISIBLE);
                    return;
                }
                messages = list;
                mHandler.sendEmptyMessage(2);
            }

            @Override
            public void onError(int i, String s) {
                showToast("获取我的消息列表失败CASE:" + s);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initView() {
        setToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {
        if (mRecyclerView == null) return;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MyMessageRecyclerAdapter(mHandler, messages, this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(AllMessageActivity.this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void setToolbar() {
        ActionBar acb = getSupportActionBar();
        acb.setTitle("全部消息");
        acb.setDisplayHomeAsUpEnabled(true);
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
                getMessage();
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

    @Override
    public void loadSuccess() {

    }

    @Override
    public void loadFailure() {

    }

    @Override
    public void showRefresh(boolean isRefresh) {

    }

}
