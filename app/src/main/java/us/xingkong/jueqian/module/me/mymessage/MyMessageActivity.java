package us.xingkong.jueqian.module.me.mymessage;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MyMessageActivity extends BaseActivity<MyMessageContract.Presenter> implements MyMessageContract.View {


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

    }

    @Override
    protected void initView() {
        setToolbar();

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
