package us.xingkong.jueqian.module.Forum.QuestionPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.QuestionRecyclerViewAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.module.Forum.NewAnswer.NewAnswerActivity;
import us.xingkong.jueqian.module.Forum.QuestionPage.AnwserPage.AnswerActivity;
import us.xingkong.jueqian.module.Forum.QuestionPage.Comment.CommentActivity;


/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class QuestionActivity extends BaseActivity<QuestionContract.Presenter> implements QuestionContract.View {


    @BindView(R.id.recyclerview_questionpage)
    RecyclerView recyclerviewQuestionpage;
    private QuestionRecyclerViewAdapter recyclerViewAdapter;
    private ArrayList questionSets;
    private ArrayList<ArrayList> answerSetsArr;
    private Handler mHandler;
    private Context mContext;
    @BindView(R.id.question_tab)
    RadioGroup tab;
    @BindView(R.id.tab_huida)
    RadioButton huida;
    String questionID;

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
        mContext = this;

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        questionID=bundle.getString("questionid");

        questionSets = new ArrayList();
        questionSets.add("header");
        answerSetsArr = new ArrayList();
        for (int i = 0; i < 30; ++i) {
            ArrayList answerSets = new ArrayList();
            answerSets.add("position " + i);
            answerSetsArr.add(answerSets);
        }

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        break;
                    case 2:
                        Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        Intent intent2 = new Intent(getApplicationContext(), AnswerActivity.class);
                        startActivity(intent2);
                        break;
                    case 4:
                        new MaterialDialog.Builder(mContext)
                                .items(R.array.option_head)
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .show();
                        break;
                    default:
                        break;
                }
            }
        };




    }

    @Override
    protected void initView() {
        setToolbarBackEnable("问题详情");
        recyclerViewAdapter = new QuestionRecyclerViewAdapter(questionSets, answerSetsArr, mHandler);
        recyclerviewQuestionpage.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerviewQuestionpage.setAdapter(recyclerViewAdapter);
        recyclerviewQuestionpage.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerviewQuestionpage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {
                        Toast.makeText(getApplicationContext(), "到底啦", Toast.LENGTH_SHORT).show();
                    }
                    if (!recyclerView.canScrollVertically(-1)) {
                        Toast.makeText(getApplicationContext(), "到头啦", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    tab.setVisibility(View.GONE);
                }else if(dy<0){
                    tab.setVisibility(View.VISIBLE);
                }
            }
        });


    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        huida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, NewAnswerActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {

            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
