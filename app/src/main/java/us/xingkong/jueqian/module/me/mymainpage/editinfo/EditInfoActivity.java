package us.xingkong.jueqian.module.me.mymainpage.editinfo;

import android.os.Bundle;

import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.module.me.mycollection.MyCollectionContract;

/**
 * Created by PERFECTLIN on 2017/4/20 0020.
 */

public class EditInfoActivity extends BaseActivity<EditInfoContract.Presenter> implements EditInfoContract.View {
    @Override
    protected EditInfoContract.Presenter createPresenter() {
        return new EditInfoPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_editinfo;
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
