package us.xingkong.jueqian.module.me.follower;

import android.content.Intent;
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
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.FollowerAdapter;
import us.xingkong.jueqian.adapter.MyCollectionAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Follow;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class FollowerActivity extends BaseActivity<FollowerContract.Presenter> implements FollowerContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private FollowerAdapter followerAdapter;
    private List<Follow> followers = new ArrayList<>();
    private String intentUserID;
    private _User intentUser = new _User();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    initRecyclerView();
                case 2:
                    followerAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    @Override
    protected FollowerContract.Presenter createPresenter() {
        return new FollowerPresenter(this, mHandler);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_follower;
    }

    @Override
    protected void prepareData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        intentUserID = bundle.getString("intentUserID");
        intentUser.setObjectId(intentUserID);

        updateFans();
    }

    private void updateFans() {
        //更新粉丝
        BmobQuery<Follow> query_follower = new BmobQuery<Follow>();
        query_follower.addWhereEqualTo("followedUser", new BmobPointer(intentUser));
        query_follower.include("followUser");
        query_follower.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query_follower.findObjects(JueQianAPP.getAppContext(), new FindListener<Follow>() {
            @Override
            public void onSuccess(List<Follow> list) {
                if (list.size() == 0) return;
                followers = list;
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onError(int i, String s) {
                showToast("获取粉丝失败");
            }
        });
    }

    @Override
    protected void initView() {
        setToolbar();
        //initRecyclerView();
    }

    private void initRecyclerView() {
        followerAdapter = new FollowerAdapter(mHandler, followers);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(followerAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(FollowerActivity.this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void setToolbar() {
        ActionBar acb = getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("粉丝");
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
