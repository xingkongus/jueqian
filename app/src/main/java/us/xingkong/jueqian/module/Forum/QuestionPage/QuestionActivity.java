package us.xingkong.jueqian.module.Forum.QuestionPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.QuestionRecyclerViewAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.module.Forum.NewAnswer.NewAnswerActivity;


/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class QuestionActivity extends BaseActivity<QuestionContract.Presenter> implements QuestionContract.View {


    @BindView(R.id.recyclerview_questionpage)
    RecyclerView recyclerviewQuestionpage;
    private QuestionRecyclerViewAdapter recyclerViewAdapter;
    private Context mContext;

    @BindView(R.id.question_tab)
    RadioGroup tab;
    @BindView(R.id.tab_huida)
    RadioButton huida;
    String questionID;
    Question getQuestion = new Question();
    ArrayList<Answer> answers = new ArrayList<>();
    @BindView(R.id.refreshLayout_question)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tab_shoucan)
    Button shoucan;
    @BindView(R.id.tab_zan)
    Button zan;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    finish();
                    break;
                case 1:
                    getQuestion = (Question) msg.obj;
                    handler.sendEmptyMessage(3);
                    break;
                case 2:
                    answers = (ArrayList<Answer>) msg.obj;
                    break;
                case 3:
                    initRecyClerView();
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
                case 5:  //刷新数据
                    answers.clear();
                    mPresenter.getQuestionAnswer(mContext, handler, questionID, answers);
                    recyclerViewAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
            }
        }
    };


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
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        questionID = bundle.getString("questionid");
        refreshLayout.setRefreshing(true);
        mPresenter.getQuestionAnswer(mContext, handler, questionID, answers);
        mPresenter.getQuestion(mContext, questionID, handler);
        refreshLayout.setRefreshing(false);

//        mHandler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                switch (msg.what) {
//                    case 1:
//                        break;
//                    case 2:
//                        Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
//                        startActivity(intent);
//                        break;
//                    case 3:
//                        Intent intent2 = new Intent(getApplicationContext(), AnswerActivity.class);
//                        startActivity(intent2);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        };
    }

    private void initSwipeRefreshLayout() {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setProgressViewEndTarget(true, 200);
    }


    @Override
    protected void initView() {
        setToolbarBackEnable("问题详情");
        initSwipeRefreshLayout();
//        initRecyClerView();
    }

    private void initRecyClerView() {
        recyclerViewAdapter = new QuestionRecyclerViewAdapter(mContext, getQuestion, answers, handler);
        recyclerviewQuestionpage.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerviewQuestionpage.setAdapter(recyclerViewAdapter);
        recyclerviewQuestionpage.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerviewQuestionpage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {
//                        Toast.makeText(getApplicationContext(), "到底啦", Toast.LENGTH_SHORT).show();
                    }
                    if (!recyclerView.canScrollVertically(-1)) {
                        Toast.makeText(getApplicationContext(), "刷新", Toast.LENGTH_SHORT).show();
                        refreshLayout.setRefreshing(true);
                        handler.sendEmptyMessage(5);

                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    tab.setVisibility(View.GONE);//底部的tab隐藏和出现
                } else if (dy < 0) {
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
                Intent intent = new Intent(mContext, NewAnswerActivity.class);// 把这个问题的objectid传过去
                intent.putExtra("questionObjectid", questionID);
                startActivity(intent);
            }
        });

        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.zan(mContext, handler, questionID);
            }
        });
        shoucan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.shoucan(mContext,handler,questionID);
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
