package us.xingkong.jueqian.module.me.following;

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
import us.xingkong.jueqian.adapter.FollowingAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class FollowingActivity extends BaseActivity<FollowingContract.Presenter> implements FollowingContract.View {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    private List<_User> followings = new ArrayList<>();
    private String intentUserID;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    initRecyclerView();
                case 2:
                    followingAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    private FollowingAdapter followingAdapter;

    @Override
    protected FollowingContract.Presenter createPresenter() {
        return new FollowingPresenter(this, mHandler);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_following;
    }

    @Override
    protected void prepareData() {
        Intent intent = getIntent();
        intentUserID = intent.getStringExtra("intentUserID");
        _User user = new _User();
        user.setObjectId(intentUserID);
        BmobQuery<_User> query = new BmobQuery<_User>();
        query.addWhereRelatedTo("following", new BmobPointer(user));
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(JueQianAPP.getAppContext(), new FindListener<_User>() {
            @Override
            public void onSuccess(List<_User> list) {
                showToast("获取关注的人成功");
                followings = list;
                mHandler.sendEmptyMessage(1);
            }

            @Override
            public void onError(int i, String s) {
                showToast("获取关注的人失败" + s);
            }
        });

    }

    @Override
    protected void initView() {
        setToolbar();
        //initRecyclerView();
    }

    private void initRecyclerView() {
        followingAdapter = new FollowingAdapter(mHandler, followings);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(followingAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(FollowingActivity.this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void setToolbar() {
        ActionBar acb = getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("关注的人");
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
