package us.xingkong.jueqian.module.me.mymessage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import me.iwf.photopicker.PhotoPicker;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.MyMessageRecyclerAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.NewMessage;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.module.Forum.NewPage.NewActivity;
import us.xingkong.jueqian.module.me.mymessage.allmessage.AllMessageActivity;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MyMessageActivity extends BaseActivity<MyMessageContract.Presenter> implements MyMessageContract.View {

    @BindView(R.id.mymessage_recyclerview)
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
                case 3: //
                    break;
            }
        }
    };

    @Override
    protected MyMessageContract.Presenter createPresenter() {
        return new MyMessagePresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_mymessage;
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
        query.addWhereNotEqualTo("isRead", 1);
        query.include("sender,messComment.question,messAnswer.question");
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(JueQianAPP.getAppContext(), new FindListener<NewMessage>() {
            @Override
            public void onSuccess(List<NewMessage> list) {
                if (list.size() == 0) {
                    if (frameLayout==null) return;
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
        mRecyclerView.addItemDecoration(new DividerItemDecoration(MyMessageActivity.this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void setToolbar() {
        ActionBar acb = getSupportActionBar();
        acb.setTitle("我的消息");
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
            case R.id.item_allmessage:
                Intent intent = new Intent(MyMessageActivity.this, AllMessageActivity.class);
                startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_mymessage, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
