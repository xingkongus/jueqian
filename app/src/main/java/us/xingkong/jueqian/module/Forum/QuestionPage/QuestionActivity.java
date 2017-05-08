package us.xingkong.jueqian.module.Forum.QuestionPage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.QuestionRecyclerViewAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Ding;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.module.Forum.NewAnswer.NewAnswerActivity;
import us.xingkong.jueqian.module.Forum.QuestionPage.Comment.CommentActivity;
import us.xingkong.jueqian.module.Login.LoginActivity;


public class QuestionActivity extends BaseActivity<QuestionContract.Presenter> implements QuestionContract.View {


    @BindView(R.id.recyclerview_questionpage)
    RecyclerView recyclerviewQuestionpage;
    private QuestionRecyclerViewAdapter recyclerViewAdapter;
    private Context mContext;

    @BindView(R.id.question_tab)
    RadioGroup tab;
    @BindView(R.id.tab_huida)
    RadioButton huida;
    private String questionID;
    Question getQuestion = new Question();
    ArrayList<Answer> answers = new ArrayList<>();
    @BindView(R.id.refreshLayout_question)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tab_shoucan)
    RadioButton shoucan;
    @BindView(R.id.tab_zan)
    RadioButton zan;
    PopupWindow mpopupWindow;
    Button popupwindow_huida;
    private Boolean isRolling = false;
    private String question_userID;
    private int zanFlag;
    private int shouocanFlag;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    finish();
                    break;
                case 1:
                    getQuestion = (Question) msg.obj;
                    initRecyClerView();
                    break;
                case 3:
                    recyclerViewAdapter.notifyDataSetChanged();
                    break;
//                case 4:
//                    new MaterialDialog.Builder(mContext)
//                            .items(R.array.option_head)
//                            .itemsCallback(new MaterialDialog.ListCallback() {
//                                @Override
//                                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
//                                    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
//                                }
//                            })
//                            .show();
//                    break;
                case 5:  //刷新数据
                    answers.clear();
                    mPresenter.getQuestionAnswer(mContext, handler, questionID, answers);
                    isRolling = true;
                    setRecyclewViewBug();
                    recyclerViewAdapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                    isRolling = false;
                    setRecyclewViewBug();
                    break;
                case 6:
                    backgroundAlpha(0.5f);
                    final String answerID = msg.getData().getString("answerID");
                    final String answer_userID = msg.getData().getString("answer_userID");
                    View v = (View) msg.obj;
                    View contentview = getLayoutInflater().inflate(R.layout.activity_popupwindow, null);
                    contentview.setFocusableInTouchMode(true);
                    contentview.setFocusable(true);
                    mpopupWindow = new PopupWindow(contentview, RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT
                            , true);
                    mpopupWindow.setTouchable(true);
                    mpopupWindow.setOutsideTouchable(false);
                    mpopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
                    mpopupWindow.setOnDismissListener(new poponDismissListener());
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
                            _User user = BmobUser.getCurrentUser(mContext, _User.class);
                            if (user == null) {
                                showToast("请先登录");
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                mpopupWindow.dismiss();
                            } else {
                                Intent intent = new Intent(mContext, CommentActivity.class);
                                intent.putExtra("answerID", answerID);
                                intent.putExtra("questionID", questionID);
                                intent.putExtra("answer_userID", answer_userID);
                                startActivity(intent);
                                mpopupWindow.dismiss();
                            }
                        }
                    });
                    break;
                case 7://设置点赞flag
                    String answer_ID = msg.getData().getString("answerID");
                    int zanFlag = msg.getData().getInt("flag");
                    if (zanFlag == 0) {
                        final int pos=msg.getData().getInt("pos");
                        final String dingID[]= (String[]) msg.obj;
                        _User user = BmobUser.getCurrentUser(mContext, _User.class);
                        Answer answer2 = new Answer();
                        answer2.setObjectId(answer_ID);
                        final Ding ding1 = new Ding();
                        ding1.setDing(user);
                        ding1.setDinged(answer2);
                        ding1.save(mContext, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                dingID[pos]=ding1.getObjectId();
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });
                    } else if (zanFlag == 1) {
                        String dingID=msg.getData().getString("dingID");
                        Ding ding1=new Ding();
                        ding1.setObjectId(dingID);
                        ding1.delete(mContext, new DeleteListener() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onFailure(int i, String s) {
                            }
                        });
                    }
                    break;
                case 8://问题回答数减一
                    String questionID1=msg.getData().getString("questionID");
                    if (questionID1 != null) {
                        Question question=new Question();
                        question.increment("answer_count",-1);
                        question.update(mContext, questionID1, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onFailure(int i, String s) {
                            }
                        });
                    }
                    break;
                case 9://连级删除问题下的回答
                    final List<Answer>list1=new ArrayList<>();
                    String questionID2=msg.getData().getString("questionID");
                    BmobQuery<Answer>query=new BmobQuery<>();
                    query.addWhereEqualTo("question",questionID2);
                    query.findObjects(mContext, new FindListener<Answer>() {
                        @Override
                        public void onSuccess(List<Answer> list) {
                            for (Answer answer:list){
                               answer.getObjectId();
                                list1.add(answer);
                            }
                            Message msg=new Message();
                            msg.obj=list1;
                            msg.what=10;
                            handler.sendMessage(msg);
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                    break;
                case 10:
                    List<Answer>list11= (List<Answer>) msg.obj;
                    List<BmobObject>list2=new ArrayList<>();
                    for (int i=0;i<list11.size();i++){
                        Answer answer=new Answer();
                        answer.setObjectId(list11.get(i).getObjectId());
                        list2.add(answer);
                    }
                    new BmobObject().deleteBatch(mContext, list2, new DeleteListener() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFailure(int i, String s) {
                        }
                    });
                    handler.sendEmptyMessage(0);
                    break;
            }
        }
    };

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }


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
        question_userID = bundle.getString("question_userID");
        _User user = BmobUser.getCurrentUser(mContext, _User.class);
        if (user != null && questionID != null && question_userID != null) {
            mPresenter.addRecentlook(mContext, questionID, question_userID, user);
        }

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
        refreshLayout.setRefreshing(true);
        mPresenter.getQuestion(mContext, questionID, handler);
        mPresenter.getQuestionAnswer(mContext, handler, questionID, answers);
        refreshLayout.setRefreshing(false);
        _User user = BmobUser.getCurrentUser(mContext, _User.class);
        if (user != null) {
        BmobQuery<Question> query = new BmobQuery<>();
        query.getObject(mContext, questionID, new GetListener<Question>() {
            @Override
            public void onSuccess(Question question) {
                zanFlag = question.getState();
                shouocanFlag = question.getShouzanFlag();
                if (zanFlag == 1) {
                    zan.setBackgroundColor(Color.parseColor("#3CB371"));
                    zan.setTextColor(Color.parseColor("#ffffff"));
                }
                if (shouocanFlag == 1) {
                    shoucan.setBackgroundColor(Color.parseColor("#3CB371"));
                    shoucan.setTextColor(Color.parseColor("#ffffff"));
                }
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("网络连接超时");
            }
        });
    }else{
            zan.setBackgroundColor(Color.parseColor("#ffffff"));
            zan.setTextColor(Color.parseColor("#000000"));
            shoucan.setBackgroundColor(Color.parseColor("#ffffff"));
            shoucan.setTextColor(Color.parseColor("#000000"));
        }

    }

    private void initRecyClerView() {
        recyclerViewAdapter = new QuestionRecyclerViewAdapter(mContext, getQuestion, answers, handler);
        recyclerviewQuestionpage.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
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
        recyclerviewQuestionpage.setAdapter(recyclerViewAdapter);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        huida.setTextColor(Color.parseColor("#000000"));
        huida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _User user = BmobUser.getCurrentUser(mContext, _User.class);
                if (user == null) {
                    showToast("请先登录");
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(mContext, NewAnswerActivity.class);// 把这个问题的objectid传过去
                    intent.putExtra("questionObjectid", questionID);
                    intent.putExtra("question_userID", question_userID);
                    startActivity(intent);
                    finish();
                }
            }
        });
        zan.setTextColor(Color.parseColor("#000000"));
        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _User user = BmobUser.getCurrentUser(mContext, _User.class);
                if (user == null) {
                    showToast("请先登录");
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                } else {
                    BmobQuery<Question> query = new BmobQuery<>();
                    query.getObject(mContext, questionID, new GetListener<Question>() {
                        @Override
                        public void onSuccess(Question question) {
                            int flag = question.getState();
                            if (flag == 0) {
                                zan.setBackgroundColor(Color.parseColor("#3CB371"));
                                zan.setTextColor(Color.parseColor("#ffffff"));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mPresenter.zan(mContext, handler, questionID);
                                        mPresenter.zanStateChange(mContext, questionID, 0);
                                    }
                                }).start();

                            } else if (flag == 1) {
                                zan.setBackgroundColor(Color.parseColor("#ffffff"));
                                zan.setTextColor(Color.parseColor("#000000"));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mPresenter.quxiaoZan(mContext, questionID);
                                        mPresenter.zanStateChange(mContext, questionID, 1);
                                    }
                                }).start();
                            }
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            showToast("网络连接超时");
                        }
                    });
                }
            }
        });
        shoucan.setTextColor(Color.parseColor("#000000"));
        shoucan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _User user = BmobUser.getCurrentUser(mContext, _User.class);
                if (user == null) {
                    showToast("请先登录");
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                } else {
                    BmobQuery<Question> query = new BmobQuery<>();
                    query.getObject(mContext, questionID, new GetListener<Question>() {
                        @Override
                        public void onSuccess(Question question) {
                            int flag = question.getShouzanFlag();
                            if (flag == 0) {
                                shoucan.setBackgroundColor(Color.parseColor("#3CB371"));
                                shoucan.setTextColor(Color.parseColor("#ffffff"));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mPresenter.shoucan(mContext, handler, questionID);
                                        mPresenter.shouzanStateChange(mContext, questionID, 0);
                                    }
                                }).start();

                            } else if (flag == 1) {
                                shoucan.setBackgroundColor(Color.parseColor("#ffffff"));
                                shoucan.setTextColor(Color.parseColor("#000000"));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mPresenter.quxiaoShouzan(mContext, questionID);
                                        mPresenter.shouzanStateChange(mContext, questionID, 1);
                                    }
                                }).start();
                            }
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });


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


    @Override
    public void setRecyclewViewBug() {
        recyclerviewQuestionpage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isRolling) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}
