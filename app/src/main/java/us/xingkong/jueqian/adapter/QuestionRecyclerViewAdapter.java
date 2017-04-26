package us.xingkong.jueqian.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import cn.bmob.v3.listener.UpdateListener;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;


/**
 * Created by Garfield on 1/9/17.
 */

public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.VH>{
    private List<Answer> answers;
    private int HEADER = 1;
    private int CONTENT = 2;
//    private int FOOTER = 3;
    private Handler mHandler;
    private Question getQuestion;
    private Context context;
    public QuestionRecyclerViewAdapter(Context context, Question getQuestion, List<Answer> answers, Handler mHandler) {
        this.getQuestion=getQuestion;
        this.answers = answers;
        this.mHandler = mHandler;
        this.context=context;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionpage_header, parent, false));
        } else if (viewType == CONTENT) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionpage, parent, false));
        } else {
            return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        }
        else {
            return CONTENT;
        }

    }
//    @Override
//    public void onBindViewHolder(final VH holder, final int position, List<Object> payloads){
//
//    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        if(position==0){
            if(getQuestion.getMtitle()!=null) {
                holder.title_question.setText(getQuestion.getMtitle());
                holder.content_question.setText(getQuestion.getMcontent());
                holder.tag1.setText(getQuestion.getTAG1_ID());
                holder.tag2.setText(getQuestion.getTAG2_ID());
                holder.time.setText(getQuestion.getUpdatedAt());
                holder.username.setText(getQuestion.getUser().getUsername());
            }
        }

        if (position != 0) {
            holder.content.setText(answers.get(position-1).getMcontent());
            holder.username_answer.setText(answers.get(position-1).getUser().getUsername());
            holder.like.setText(String.valueOf(answers.get(position-1).getUps()));
            holder.question_time.setText(answers.get(position-1).getUpdatedAt());
                holder.zan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(context).title("为答案点赞")
                                .content("您是赞同还是反对呢?").negativeText("赞同").positiveText("反对")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        Answer answer = new Answer();
                                        answer.increment("ups");
                                        answer.update(context, answers.get(position - 1).getObjectId(), new UpdateListener() {
                                            @Override
                                            public void onSuccess() {
                                                String a= (String) holder.like.getText();
                                                holder.like.setText(String.valueOf((Integer.parseInt(a))+1));

                                            }

                                            @Override
                                            public void onFailure(int i, String s) {

                                            }
                                        });
                                    }
                                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Answer answer=new Answer();
                                answer.increment("ups",-1);
                                answer.update(context, answers.get(position - 1).getObjectId(), new UpdateListener() {
                                    @Override
                                    public void onSuccess() {
                                        String a= (String) holder.like.getText();
                                        holder.like.setText(String.valueOf((Integer.parseInt(a))-1));
                                    }

                                    @Override
                                    public void onFailure(int i, String s) {

                                    }
                                });
                            }
                        }).show();
                    }
                });

            holder.item_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message msg=new Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("answerID",answers.get(position-1).getObjectId());
                    msg.setData(bundle);
                    msg.obj=v;
                    msg.what=6;
                    mHandler.sendMessage(msg);
                }
            });
        }
        holder.imageButton_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.sendEmptyMessage(4);
            }
        });

    }

    @Override
    public int getItemCount() {
        return answers.size() + 1;
    }

    class VH extends RecyclerView.ViewHolder {
        TextView content;
//        TextView comment;
        ImageButton imageButton_more;
        TextView title_question;
        TextView content_question;
        TextView tag1;
        TextView tag2;
//        TextView like_count;
//        TextView comment_count;
        TextView username;
        TextView time;
        TextView username_answer;
        TextView like;
        TextView question_time;
        TextView zan;
        LinearLayout item_question;
        public VH(View itemView) {
            super(itemView);
            item_question= (LinearLayout) itemView.findViewById(R.id.item_question);
            question_time= (TextView) itemView.findViewById(R.id.question_time);
            zan= (TextView) itemView.findViewById(R.id.zan);
            like= (TextView) itemView.findViewById(R.id.like_questionpage_item);
            username_answer= (TextView) itemView.findViewById(R.id.username_questionpage);
            content = (TextView) itemView.findViewById(R.id.content_questionpage);
//            comment = (TextView) itemView.findViewById(R.id.comment_questionpage);
            imageButton_more = (ImageButton) itemView.findViewById(R.id.more_questionpage);
            title_question= (TextView) itemView.findViewById(R.id.title_questionpage);
            content_question= (TextView) itemView.findViewById(R.id.content_question);
            tag1= (TextView) itemView.findViewById(R.id.tag1_questionpage);
            tag2= (TextView) itemView.findViewById(R.id.tag2_questionpage);
//            like_count= (TextView) itemView.findViewById(R.id.like_questionpage1);
//            comment_count= (TextView) itemView.findViewById(R.id.comment_questionpage1);
            username= (TextView) itemView.findViewById(R.id.username_question);
            time= (TextView) itemView.findViewById(R.id.time_question);
        }
    }
}
