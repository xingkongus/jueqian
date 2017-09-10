package us.xingkong.jueqian.module.Forum.QuestionPage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.QuestionRecyclerViewAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Comment;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Ding;
import us.xingkong.jueqian.bean.ForumBean.BombBean.NewMessage;
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
    ImageButton huida;
    private String questionID;
    Question getQuestion = new Question();
    ArrayList<Answer> answers = new ArrayList<>();
    @BindView(R.id.refreshLayout_question)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tab_shoucan)
    ImageButton shoucan;
    @BindView(R.id.tab_zan)
    ImageButton zan;
    PopupWindow mpopupWindow;
    Button popupwindow_huida;
    private Boolean isRolling = false;
    private String question_userID;
    private boolean isZan;
    private boolean isShouzan;
    private boolean isInitRecyclewView = false;
    public static QuestionActivity close = null;
    private int item_count = 0;//记载跳过的回答数


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
                    if (getQuestion != null) {
                        initRecyClerView();
                    }
                    break;
                case 3:
                    if (answers != null && isInitRecyclewView == true) {
                        recyclerViewAdapter.notifyDataSetChanged();
                        item_count = 20;
                    }
                    if (refreshLayout != null) {
                        refreshLayout.setRefreshing(false);
                    }
                    break;
                case 4:
                    final List<NewMessage> newMessageList1 = (List<NewMessage>) msg.obj;
                    List<BmobObject> newMessageList2 = new ArrayList<>();
                    for (int i = 0; i < newMessageList1.size(); i++) {
                        NewMessage newMessage = new NewMessage();
                        newMessage.setObjectId(newMessageList1.get(i).getObjectId());
                        newMessageList2.add(newMessage);
                    }
                    if (newMessageList2.size() == 0) return;
                    new BmobObject().deleteBatch(mContext, newMessageList2, new DeleteListener() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFailure(int i, String s) {
                        }
                    });
                    break;
                case 5:  //刷新数据
                    if (isInitRecyclewView == false) {
                        mPresenter.getQuestion(mContext, questionID, handler);
                    }
                    isRolling = true;
                    setRecyclewViewBug();
                    answers.clear();
                    mPresenter.getQuestionAnswer(mContext, handler, questionID, answers);
                    refreshLayout.setRefreshing(false);
                    isRolling = false;
                    setRecyclewViewBug();
                    item_count = 20;
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
                                intent.putExtra("question_userID", question_userID);
                                startActivity(intent);
                                mpopupWindow.dismiss();
                            }
                        }
                    });
                    break;
                case 7://设置点赞flag数组
                    String answer_ID = msg.getData().getString("answerID");
                    int zanFlag = msg.getData().getInt("flag");
                    if (zanFlag == 0) {
                        final int pos = msg.getData().getInt("pos");
                        final String dingID[] = (String[]) msg.obj;
                        _User user = BmobUser.getCurrentUser(mContext, _User.class);
                        Answer answer2 = new Answer();
                        answer2.setObjectId(answer_ID);
                        final Ding ding1 = new Ding();
                        ding1.setDing(user);
                        ding1.setDinged(answer2);
                        ding1.save(mContext, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                dingID[pos] = ding1.getObjectId();
                            }

                            @Override
                            public void onFailure(int i, String s) {
                            }
                        });
                    } else if (zanFlag == 1) {
                        String dingID = msg.getData().getString("dingID");
                        Ding ding1 = new Ding();
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
                    String questionID1 = msg.getData().getString("questionID");
                    String answerID1 = msg.getData().getString("answerID1");
                    if (questionID1 != null) {
                        Question question = new Question();
                        question.increment("answer_count", -1);
                        question.update(mContext, questionID1, new UpdateListener() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onFailure(int i, String s) {
                            }
                        });
                    }
                    //查找NewMessage表相同的questionID项
                    final List<NewMessage> newMessageList22 = new ArrayList<>();
                    BmobQuery<NewMessage> messageBmobQuery1 = new BmobQuery<>();
                    messageBmobQuery1.addWhereEqualTo("messAnswer", answerID1);
                    messageBmobQuery1.findObjects(mContext, new FindListener<NewMessage>() {
                        @Override
                        public void onSuccess(List<NewMessage> list) {
                            for (NewMessage newMessage : list) {
                                newMessageList22.add(newMessage);
                            }
                            Message message = new Message();
                            message.obj = newMessageList22;
                            message.what = 13;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });

                    //查找Comment中对应answerID项
                    final List<Comment> comments = new ArrayList<>();
                    BmobQuery<Comment> commentBmobQuery = new BmobQuery<>();
                    commentBmobQuery.addWhereEqualTo("answer", answerID1);
                    commentBmobQuery.findObjects(mContext, new FindListener<Comment>() {
                        @Override
                        public void onSuccess(List<Comment> list) {
                            for (Comment comment1 : list) {
                                comments.add(comment1);
                            }
                            Message message = new Message();
                            message.obj = comments;
                            message.what = 14;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onError(int i, String s) {
                        }
                    });
                    //查找NewMessage表中的对应commentID项
                    final List<NewMessage> newMessageList44 = new ArrayList<>();
                    BmobQuery<NewMessage> messageBmobQuery44 = new BmobQuery<>();
                    messageBmobQuery44.addWhereEqualTo("answer", answerID1);
                    messageBmobQuery44.findObjects(mContext, new FindListener<NewMessage>() {
                        @Override
                        public void onSuccess(List<NewMessage> list) {
                            for (NewMessage newMessage : list) {
                                newMessageList44.add(newMessage);
                            }
                            Message message = new Message();
                            message.obj = newMessageList44;
                            message.what = 15;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onError(int i, String s) {
                        }
                    });
                    break;
                case 9://连级删除问题下的回答
                    //先查找到所有关于这个问题的answerID
                    String questionID2 = msg.getData().getString("questionID");
                    //查找answer表相同questionID的项
                    final List<Answer> list1 = new ArrayList<>();
                    BmobQuery<Answer> query = new BmobQuery<>();
                    query.addWhereEqualTo("question", questionID2);
                    query.findObjects(mContext, new FindListener<Answer>() {
                        @Override
                        public void onSuccess(List<Answer> list) {
                            for (Answer answer : list) {
                                answer.getObjectId();
                                list1.add(answer);
                            }
                            Message msg = new Message();
                            msg.obj = list1;
                            msg.what = 10;
                            handler.sendMessage(msg);
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                    //查找NewMessage表相同的questionID项
                    final List<NewMessage> newMessageList = new ArrayList<>();
                    BmobQuery<NewMessage> messageBmobQuery = new BmobQuery<>();
                    messageBmobQuery.addWhereEqualTo("question", questionID2);
                    messageBmobQuery.findObjects(mContext, new FindListener<NewMessage>() {
                        @Override
                        public void onSuccess(List<NewMessage> list) {
                            for (NewMessage newMessage : list) {
                                newMessageList.add(newMessage);
                            }
                            Message message = new Message();
                            message.obj = newMessageList;
                            message.what = 4;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onError(int i, String s) {

                        }
                    });
                    finish();
                    break;
                case 10://删除问题下的所有回答
                    List<Answer> list11 = (List<Answer>) msg.obj;
                    List<BmobObject> list2 = new ArrayList<>();
                    for (int i = 0; i < list11.size(); i++) {
                        Answer answer = new Answer();
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
                    break;
                case 11:   //1是已赞的处理，2是取消赞处理，3是收藏后的处理，4是取消收藏后的处理
                    int flag = (int) msg.obj;
                    if (flag == 1) {
                        if (zan == null) return;
                        zan.setImageResource(R.drawable.ic_action_like2);
                        isZan = true;
                    } else if (flag == 2) {
                        if (zan == null) return;
                        zan.setImageResource(R.drawable.ic_action_like1);
                        isZan = false;
                    } else if (flag == 3) {
                        if (shoucan == null) return;
                        shoucan.setImageResource(R.drawable.ic_action_star2);
                        isShouzan = true;
                    } else if (flag == 4) {
                        if (shoucan == null) return;
                        shoucan.setImageResource(R.drawable.ic_action_star1);
                        isShouzan = false;
                    }
                    break;
                case 12:
                    List<Answer> newAnswers = (List<Answer>) msg.obj;
                    if (newAnswers.size() != 0) {
                        recyclerViewAdapter.addMoreItem(newAnswers);
                        recyclerViewAdapter.changeMoreStatus(QuestionRecyclerViewAdapter.LOADING_MORE);
                        item_count += 20;
                    } else {
                        recyclerViewAdapter.changeMoreStatus(QuestionRecyclerViewAdapter.NO_MORE);
                    }
                    break;
                case 13:
                    //删除点击删除answer关联下的NewMessage
                    final List<NewMessage> newMessageList3 = (List<NewMessage>) msg.obj;
                    List<BmobObject> newMessageList4 = new ArrayList<>();
                    for (int i = 0; i < newMessageList3.size(); i++) {
                        NewMessage newMessage = new NewMessage();
                        newMessage.setObjectId(newMessageList3.get(i).getObjectId());
                        newMessageList4.add(newMessage);
                    }
                    if (newMessageList4.size() == 0) return;
                    new BmobObject().deleteBatch(mContext, newMessageList4, new DeleteListener() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFailure(int i, String s) {
                        }
                    });
                    break;
                case 14://通过answerID删除Comment表对应的项
                    final List<Comment> comments2 = (List<Comment>) msg.obj;
                    List<BmobObject> bmobObjectList = new ArrayList<>();
                    for (int i = 0; i < comments2.size(); i++) {
                        Comment newComment = new Comment();
                        newComment.setObjectId(comments2.get(i).getObjectId());
                        bmobObjectList.add(newComment);
                    }
                    if (bmobObjectList.size() == 0) return;
                    new BmobObject().deleteBatch(mContext, bmobObjectList, new DeleteListener() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                    break;
                case 15:
                    //通过answerID删除NewMessage表的对应项
                    final List<NewMessage> newMessageList55 = (List<NewMessage>) msg.obj;
                    List<BmobObject> newMessageList555 = new ArrayList<>();
                    for (int i = 0; i < newMessageList55.size(); i++) {
                        NewMessage newMessage = new NewMessage();
                        newMessage.setObjectId(newMessageList55.get(i).getObjectId());
                        newMessageList555.add(newMessage);
                    }
                    if (newMessageList555.size() == 0) return;
                    new BmobObject().deleteBatch(mContext, newMessageList555, new DeleteListener() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFailure(int i, String s) {
                        }
                    });
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
        close = this;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        questionID = bundle.getString("questionid");
        question_userID = bundle.getString("question_userID");
        _User user = BmobUser.getCurrentUser(mContext, _User.class);
        if (user != null && questionID != null && question_userID != null) {
            mPresenter.addRecentlook(mContext, questionID, question_userID, user);
        }
        if (user != null) {
            if (question_userID.equals(user.getObjectId())) {
                if (shoucan == null) return;
                shoucan.setVisibility(View.GONE);
                shoucan.setEnabled(false);
            }
        }

    }

    private void initSwipeRefreshLayout() {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setProgressViewEndTarget(true, 200);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Toast.makeText(getApplicationContext(), "刷新", Toast.LENGTH_SHORT).show();
                if (isNetworkAvailable(mContext)) {
                    handler.sendEmptyMessage(5);
                } else {
                    showToast("网络不可用");
                }

            }
        });
    }


    @Override
    protected void initView() {
        _User user = BmobUser.getCurrentUser(mContext, _User.class);
        setToolbarBackEnable("问题详情");
        initSwipeRefreshLayout();
        refreshLayout.setRefreshing(true);
        if (isNetworkAvailable(mContext)) {
            mPresenter.getQuestion(mContext, questionID, handler);
            mPresenter.getQuestionAnswer(mContext, handler, questionID, answers);
        } else {
            showToast("网络不可用");
        }
        if (user != null) {
            final String userID = user.getObjectId();
            BmobQuery<_User> likeQuery = new BmobQuery<>();
            Question question = new Question();
            question.setObjectId(questionID);
            likeQuery.addWhereRelatedTo("likepeople", new BmobPointer(question));
            likeQuery.findObjects(mContext, new FindListener<_User>() {
                @Override
                public void onSuccess(List<_User> list) {
                    for (_User user : list) {
                        if (user.getObjectId().equals(userID)) {
                            if (zan == null) return;
                            zan.setImageResource(R.drawable.ic_action_like2);
                            isZan = true;
                            return;
                        } else {
                            isZan = false;
                            if (zan == null) return;
                            zan.setImageResource(R.drawable.ic_action_like1);
                        }
                    }
                }

                @Override
                public void onError(int i, String s) {

                }
            });

            BmobQuery<Question> query = new BmobQuery<>();
            query.addWhereRelatedTo("collections", new BmobPointer(user));
            query.findObjects(mContext, new FindListener<Question>() {
                @Override
                public void onSuccess(List<Question> list) {
                    for (Question question : list) {
                        if (question.getObjectId().equals(questionID)) {
                            if (shoucan == null) return;
                            shoucan.setImageResource(R.drawable.ic_action_star2);
                            isShouzan = true;
                            return;
                        } else {
                            isShouzan = false;
                            if (shoucan == null) return;
                            shoucan.setImageResource(R.drawable.ic_action_star1);
                        }
                    }
                }

                @Override
                public void onError(int i, String s) {

                }
            });
        } else {
            if (zan == null) return;
            if (shoucan == null) return;
            zan.setPressed(false);
            shoucan.setPressed(false);
        }

    }

    private void initRecyClerView() {
        if (recyclerviewQuestionpage == null) return;
        recyclerViewAdapter = new QuestionRecyclerViewAdapter(mContext, getQuestion, answers, handler);
        recyclerviewQuestionpage.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerviewQuestionpage.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerviewQuestionpage.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底部
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        //加载更多功能的代码
                        ArrayList<Answer> newAnswers = new ArrayList<Answer>();
                        mPresenter.getMoreAnswer(mContext, newAnswers, handler, item_count, questionID);
                    }


//                    if (!recyclerView.canScrollVertically(1)) {
////                        Toast.makeText(getApplicationContext(), "到底啦", Toast.LENGTH_SHORT).show();
//                    }
//                    if (!recyclerView.canScrollVertically(-1)) {
//                        Toast.makeText(getApplicationContext(), "刷新", Toast.LENGTH_SHORT).show();
//                        refreshLayout.setRefreshing(true);
//                        handler.sendEmptyMessage(5);
//
//                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && answers.size() >= 0) {
                    tab.setVisibility(View.GONE);//底部的tab隐藏和出现
                } else if (dy < 0) {
                    tab.setVisibility(View.VISIBLE);
                }

            }
        });
        recyclerviewQuestionpage.setAdapter(recyclerViewAdapter);
        isInitRecyclewView = true;
    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        final _User user = BmobUser.getCurrentUser(mContext, _User.class);
        huida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                }
            }
        });

        zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    showToast("请先登录");
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (isNetworkAvailable(mContext)) {
                        if (isZan == true) {
                            mPresenter.quxiaoZan(mContext, questionID, handler);

                        } else {
                            mPresenter.zan(mContext, handler, questionID);

                        }
                    } else {
                        showToast("网络不可用");
                    }
                }
            }
        });

        shoucan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    showToast("请先登录");
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (isNetworkAvailable(mContext)) {
                        if (isShouzan == true) {
                            mPresenter.quxiaoShouzan(mContext, questionID, handler);

                        } else if (isShouzan == false) {
                            mPresenter.shoucan(mContext, handler, questionID, question_userID);

                        }
                    } else {
                        showToast("网络不可用");
                    }
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

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
