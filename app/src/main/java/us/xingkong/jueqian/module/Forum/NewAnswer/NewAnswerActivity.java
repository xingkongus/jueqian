package us.xingkong.jueqian.module.Forum.NewAnswer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.NewMessage;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;

/**
 * Created by lenovo on 2017/3/30.
 */

public class NewAnswerActivity extends BaseActivity<NewAnswerContract.Presenter> implements NewAnswerContract.View {

    @BindView(R.id.new_huidacontent)
    EditText new_huidacontent;
    Context context;
    private String questionID;
    private String question_userID;
    private _User user;
    private String currentUserID;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 0:
                    Question question=new Question();
                    question.increment("answer_count");
                    question.update(context, questionID, new UpdateListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int i, String s) {
                        }
                    });
                    //添加NewMessage关联
                    if (!currentUserID.equals(question_userID)) {
                        _User currentUser=new _User();
                        currentUser.setObjectId(currentUserID);
                        Answer answer;
                        answer = (Answer) msg.obj;
                        NewMessage message = new NewMessage();
                        _User receiver = new _User();
                        message.setSender(currentUser);
                        receiver.setObjectId(question_userID);
                        message.setReceiver(receiver);
                        message.setTYPE(2);
                        message.setIsRead(0);
                        message.setMessAnswer(answer);
                        Question answer_question=new Question();
                        answer_question.setObjectId(questionID);
                        message.setQuestion(answer_question);
                        message.save(context, new SaveListener() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });
                    }
                    //添加用户表的我的回答
                    Answer answer;
                    answer= (Answer) msg.obj;
                    answer.setObjectId(answer.getObjectId());
                    BmobRelation bmobRelation=new BmobRelation();
                    bmobRelation.add(answer);
                    user.setAnswers(bmobRelation);
                    user.update(context, new UpdateListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                    finish();
                    break;
            }
        }
    };

    @Override
    protected NewAnswerContract.Presenter createPresenter() {
        return new NewAnswerPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_newanswer;
    }

    @Override
    protected void prepareData() {
        user= BmobUser.getCurrentUser(getApplicationContext(),_User.class);
        currentUserID=user.getObjectId();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        questionID = bundle.getString("questionObjectid");
        question_userID=bundle.getString("question_userID");
    }

    @Override
    protected void initView() {
        context = this;
        setToolbarBackEnable("回答");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
//        new_Answer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String newAnswer = new_huidacontent.getText().toString();
//                mPresenter.addNewAnswer(context, newAnswer, questionID,handler);
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.sendMessage:
                    new MaterialDialog.Builder(this).title("提示").content("是否提交").negativeText("取消").positiveText("确认")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    String newAnswer = new_huidacontent.getText().toString();
                                    if (newAnswer.isEmpty()) {
                                        showToast("回答不能为空");
                                    } else {
                                        mPresenter.addNewAnswer(context, newAnswer, questionID, handler,question_userID);
                                    }
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    showToast("已取消");
                                }
                            }).show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.new_answer,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
