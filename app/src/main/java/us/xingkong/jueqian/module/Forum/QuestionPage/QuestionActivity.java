package us.xingkong.jueqian.module.Forum.QuestionPage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.QuestionRecyclerViewAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.module.Forum.NewAnswer.NewAnswerActivity;
import us.xingkong.jueqian.module.Forum.QuestionPage.Comment.CommentActivity;
import us.xingkong.jueqian.module.Login.LoginActivity;


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
    PopupWindow mpopupWindow;
    Button popupwindow_huida;
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
//                case 2:
//                    answers = (ArrayList<Answer>) msg.obj;
//                    break;
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
                    break;
                case 6:
                    final String answerID = msg.getData().getString("answerID");
                    View v = (View) msg.obj;
                    View contentview = getLayoutInflater().inflate(R.layout.activity_popupwindow, null);
                    contentview.setFocusableInTouchMode(true);
                    contentview.setFocusable(true);
                    mpopupWindow = new PopupWindow(contentview, RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT
                            , true);
                    mpopupWindow.setTouchable(true);
                    mpopupWindow.setOutsideTouchable(false);
                    mpopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
                    mpopupWindow.setAnimationStyle(R.style.anim_menu);
                    mpopupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                                if (mpopupWindow != null && mpopupWindow.isShowing()) {
                                    mpopupWindow.dismiss();
                                }
                                return true;
                            }
                            return false;
                        }
                    });

                    mpopupWindow.showAtLocation(v, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    popupwindow_huida = (Button) contentview.findViewById(R.id.huifu_popupwindow);
                    popupwindow_huida.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            _User user = BmobUser.getCurrentUser(mContext,_User.class);
                            if (user==null) {
                                showToast("请先登录");
                                Intent intent=new Intent(mContext, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(mContext, CommentActivity.class);
                                intent.putExtra("answerID", answerID);
                                intent.putExtra("questionID", questionID);
                                startActivity(intent);
                                mpopupWindow.dismiss();
                            }
                        }
                    });
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0) {
            if (mpopupWindow != null && !mpopupWindow.isShowing()) {
                mpopupWindow.showAtLocation(findViewById(R.id.item_question), Gravity.BOTTOM, 0, 0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


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
    }

    private void initSwipeRefreshLayout() {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setProgressViewEndTarget(true, 200);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "刷新", Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessage(5);
            }
        });
    }


    @Override
    protected void initView() {
        setToolbarBackEnable("问题详情");
        initSwipeRefreshLayout();
    }

    private void initRecyClerView() {
        recyclerViewAdapter = new QuestionRecyclerViewAdapter(mContext, getQuestion, answers, handler);
        recyclerviewQuestionpage.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerviewQuestionpage.setAdapter(recyclerViewAdapter);
        recyclerviewQuestionpage.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerviewQuestionpage.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    if (!recyclerView.canScrollVertically(1)) {
////                        Toast.makeText(getApplicationContext(), "到底啦", Toast.LENGTH_SHORT).show();
//                    }
//                    if (!recyclerView.canScrollVertically(-1)) {
//                        Toast.makeText(getApplicationContext(), "刷新", Toast.LENGTH_SHORT).show();
//                        refreshLayout.setRefreshing(true);
//                        handler.sendEmptyMessage(5);
//
//                    }
//                }
//            }

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
                _User user = BmobUser.getCurrentUser(mContext,_User.class);
                if (user==null) {
                    showToast("请先登录");
                    Intent intent=new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(mContext, NewAnswerActivity.class);// 把这个问题的objectid传过去
                    intent.putExtra("questionObjectid", questionID);
                    startActivity(intent);
                }
            }
        });

        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _User user = BmobUser.getCurrentUser(mContext,_User.class);
                if (user==null) {
                    showToast("请先登录");
                    Intent intent=new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }else {
                    mPresenter.zan(mContext, handler, questionID);
                }
            }
        });
        shoucan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _User user = BmobUser.getCurrentUser(mContext,_User.class);
                if (user==null) {
                    showToast("请先登录");
                    Intent intent=new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                }else {
                    mPresenter.shoucan(mContext, handler, questionID);
                }
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
