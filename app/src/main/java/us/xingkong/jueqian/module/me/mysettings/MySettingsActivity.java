package us.xingkong.jueqian.module.me.mysettings;

import android.os.Bundle;

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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }
}
