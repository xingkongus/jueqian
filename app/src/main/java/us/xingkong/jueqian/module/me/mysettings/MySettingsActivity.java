package us.xingkong.jueqian.module.me.mysettings;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MySettingsActivity extends BaseActivity<MySettingsContract.Presenter> implements MySettingsContract.View {
    @Override
    protected MySettingsContract.Presenter createPresenter() {
        return null;
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_mysettings;
    }

    @Override
    protected void prepareData() {

    }

    @Override
    protected void initView() {
        ActionBar acb = getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("个人设置");
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
