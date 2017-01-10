package us.xingkong.jueqian.module.AnwserPage;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.CommentRecyclerViewAdapter;
import us.xingkong.jueqian.base.BaseActivity;

/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

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
