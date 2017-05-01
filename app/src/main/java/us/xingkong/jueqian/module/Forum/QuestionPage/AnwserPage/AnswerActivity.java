package us.xingkong.jueqian.module.Forum.QuestionPage.AnwserPage;

import android.os.Bundle;
import android.view.MenuItem;

import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;

public class AnswerActivity extends BaseActivity<AnswerContract.Presenter> implements AnswerContract.View {


    @Override
    protected AnswerContract.Presenter createPresenter() {
        return new AnswerPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_answerpage;
    }

    @Override
    protected void prepareData() {

    }

    @Override
    protected void initView() {
        setToolbarBackEnable("回答");

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
