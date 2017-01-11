package us.xingkong.jueqian.module.me.mymessage;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.MyMessageRecyclerAdapter;
import us.xingkong.jueqian.base.BaseActivity;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MyMessageActivity extends BaseActivity<MyMessageContract.Presenter> implements MyMessageContract.View {

    @BindView(R.id.mymessage_recyclerview)
    RecyclerView mRecyclerView;

    private ArrayList<String> mArrayList;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(), "刷新成功", Toast.LENGTH_SHORT).show();
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
        mArrayList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
//            ArrayList arrayList = new ArrayList();
//            arrayList.add("我是消息" + i);
            mArrayList.add("我是消息" + i);
        }
    }

    @Override
    protected void initView() {
        setToolbar();
        initRecyclerView();

    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MyMessageRecyclerAdapter(mArrayList, mHandler));
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
