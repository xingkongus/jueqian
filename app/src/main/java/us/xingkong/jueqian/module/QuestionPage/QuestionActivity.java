package us.xingkong.jueqian.module.QuestionPage;

import android.os.Bundle;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.QuestionRecyclerViewAdapter;
import us.xingkong.jueqian.base.BaseActivity;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 10:50
 */

public class QuestionActivity extends BaseActivity<QuestionContract.Presenter> implements QuestionContract.View {


    @BindView(R.id.recyclerview_questionpage)
    RecyclerView recyclerviewQuestionpage;
    private QuestionRecyclerViewAdapter recyclerViewAdapter;
    private ArrayList questionSets;
    private ArrayList<ArrayList> answerSetsArr;


    @Override
    protected QuestionContract.Presenter createPresenter() {
        return new QuestionPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_question;
    }

    @Override
    protected void prepareData() {

        questionSets = new ArrayList();
        questionSets.add("header");
        answerSetsArr = new ArrayList();
        for (int i = 0; i < 30; ++i) {
            ArrayList answerSets = new ArrayList();
            answerSets.add("position " + i);
            answerSetsArr.add(answerSets);
        }
    }

    @Override
    protected void initView() {
        setToolbarBackEnable("问题详情");
        recyclerViewAdapter = new QuestionRecyclerViewAdapter(questionSets, answerSetsArr);
        recyclerviewQuestionpage.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerviewQuestionpage.setAdapter(recyclerViewAdapter);
        recyclerviewQuestionpage.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
            showToast("heihei");
        }
        return super.onOptionsItemSelected(item);
    }
}
