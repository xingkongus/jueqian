package us.xingkong.jueqian.module.me.mymainpage;

import android.os.Bundle;

import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;

/**
 * Created by PERFECTLIN on 2017/4/19 0019.
 */

public class MyMainPageAcitivity extends BaseActivity<MyMainPageContract.Presenter> implements MyMainPageContract.View {
    @Override
    protected MyMainPageContract.Presenter createPresenter() {
        return new MyMainPagePresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_mymainpage;
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
