package us.xingkong.jueqian.module.me.myquestions;

import android.os.Bundle;

import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;

/**
 * Created by PERFECTLIN on 2017/1/13 0013.
 */

public class MyQuestionsAcitivity extends BaseActivity<MyQuestionsContract.Presenter> implements MyQuestionsContract.View {
    @Override
    protected MyQuestionsContract.Presenter createPresenter() {
        return new MyQuestionPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_myquestions;
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
