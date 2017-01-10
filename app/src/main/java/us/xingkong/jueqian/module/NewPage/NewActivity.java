package us.xingkong.jueqian.module.NewPage;

import android.os.Bundle;
import android.view.MenuItem;

import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;

/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class NewActivity extends BaseActivity<NewContract.Presenter> implements NewContract.View {


    @Override
    protected NewContract.Presenter createPresenter() {
        return new NewPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_new;
    }

    @Override
    protected void prepareData() {

    }

    @Override
    protected void initView() {
        setToolbarBackEnable("提问");

    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
