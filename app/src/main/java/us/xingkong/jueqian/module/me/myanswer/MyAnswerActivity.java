package us.xingkong.jueqian.module.me.myanswer;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import java.util.ArrayList;

import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MyAnswerActivity extends BaseActivity<MyAnswerContract.Presenter> implements MyAnswerContract.View {



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

    }

    @Override
    protected void initView() {
        setToolbar();
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
