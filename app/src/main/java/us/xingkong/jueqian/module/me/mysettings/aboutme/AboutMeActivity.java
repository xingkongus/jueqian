package us.xingkong.jueqian.module.me.mysettings.aboutme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;

/**
 * Created by PERFECTLIN on 2017/1/11 0011.
 */

public class AboutMeActivity extends BaseActivity<AboutMeContract.Presenter> implements AboutMeContract.View {

    @Override
    protected AboutMeContract.Presenter createPresenter() {
        return new AboutMePresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_aboutme;
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
        acb.setTitle("关于我们");
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
