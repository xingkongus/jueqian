package us.xingkong.jueqian.module.Forum.QuestionPage.Comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.CommentRecyclerViewAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Comment;
import us.xingkong.jueqian.bean.ForumBean.BombBean.NewMessage;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;


public class CommentActivity extends BaseActivity<CommentContract.Presenter> implements CommentContract.View {


    @BindView(R.id.comment_recyclerview)
    RecyclerView recyclerviewCommentpage;
    private CommentRecyclerViewAdapter recyclerViewAdapter;
    private Context mContext;
    private String answerID;
    private String questionID;
    private Answer answer = new Answer();
    private ArrayList<Comment> comments = new ArrayList<>();
    private String comment_content;
    @BindView(R.id.edit_comment)
    EditText edit_comment;
    @BindView(R.id.edit_send)
    Button edit_send;
    @BindView(R.id.comment_tab)
    LinearLayout tab;
    @BindView(R.id.refreshLayout_comment)
    SwipeRefreshLayout refreshLayout;
    private Boolean isRolling = false;
    private String answer_userID;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    finish();
                    break;
                case 1:
                    answer = (Answer) msg.obj;
                    initRecyclerView();
                    break;
                case 2:
                    recyclerViewAdapter.notifyDataSetChanged();
                    break;
                case 3: //刷新数据
                    isRolling = true;
                    setRecyclewViewBug();
                    comments.clear();
                    mPresenter.getAnswerComments(mContext, handler, answerID, comments);
                    recyclerViewAdapter.notifyDataSetChanged();
                    isRolling = false;
                    setRecyclewViewBug();
                    refreshLayout.setRefreshing(false);
                    break;
                case 4:
                    int position = (int) msg.obj;
                    recyclerviewCommentpage.scrollToPosition(1);
                    recyclerViewAdapter.notifyItemInserted(1);
                    recyclerViewAdapter.notifyDataSetChanged();
                    _User user = BmobUser.getCurrentUser(mContext, _User.class);
                    if(!user.getObjectId().equals(answer_userID)) {
                        Comment comment1 = (Comment) msg.obj;
                        NewMessage message = new NewMessage();
                        message.setSender(user);
                        _User receiver = new _User();
                        receiver.setObjectId(answer_userID);
                        message.setTYPE(1);
                        message.setMessComment(comment1);
                        message.save(mContext, new SaveListener() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });
                    }
                    break;
                case 5:
                    String newCommentID = msg.getData().getString("new_commentID");
                    mPresenter.getNewComment(mContext, handler, newCommentID);
                    break;
                case 6:
                    Comment comment;
                    comment = (Comment) msg.obj;
                    recyclerViewAdapter.addItem(0, comment);

                    break;
            }
        }
    };

    @Override
    protected CommentContract.Presenter createPresenter() {
        return new CommentPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_comment;
    }

    @Override
    protected void prepareData() {
        mContext = this;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        answerID = bundle.getString("answerID");
        questionID = bundle.getString("questionID");
        answer_userID=bundle.getString("answer_userID");

//        mHandler = new Handler(getMainLooper()){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                switch (msg.what){
//                    case 1:
//                        new MaterialDialog.Builder(mContext)
//                                .items(R.array.option_head)
//                                .itemsCallback(new MaterialDialog.ListCallback() {
//                                    @Override
//                                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
//                                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
//                                    }
//                                })
//                                .show();
//                        break;
//                    case 2:
//                        break;
//                    case 3:
//                        break;
//                }
//            }
//        };
    }

    private void initSwipeRefreshLayout() {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        refreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        refreshLayout.setProgressViewEndTarget(true, 300);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getApplicationContext(), "刷新", Toast.LENGTH_SHORT).show();
                handler.sendEmptyMessage(3);
            }
        });
    }


    @Override
    protected void initView() {
        setToolbarBackEnable("评论");
        initSwipeRefreshLayout();
    }

    private void initRecyclerView() {
        recyclerViewAdapter = new CommentRecyclerViewAdapter(mContext, handler, answer, comments);
        recyclerviewCommentpage.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerviewCommentpage.setAdapter(recyclerViewAdapter);
        recyclerviewCommentpage.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerviewCommentpage.setItemAnimator(new DefaultItemAnimator());
        recyclerviewCommentpage.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
        refreshLayout.setRefreshing(true);
        mPresenter.getAnswer(mContext, answerID, handler);
        mPresenter.getAnswerComments(mContext, handler, answerID, comments);
        refreshLayout.setRefreshing(false);
    }

    @Override
    protected void initEvent() {
        edit_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_content = edit_comment.getText().toString();
                if (comment_content.isEmpty()) {
                    showToast("评论内容不能为空");
                } else {
                    mPresenter.addNewComment(mContext, handler, comment_content, answerID, questionID,answer_userID);
                    edit_comment.setText("");
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
        recyclerviewCommentpage.setOnTouchListener(new View.OnTouchListener() {
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
