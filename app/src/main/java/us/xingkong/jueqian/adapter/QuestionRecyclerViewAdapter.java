package us.xingkong.jueqian.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;


/**
 * Created by Garfield on 1/9/17.
 */

public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.VH> implements View.OnClickListener{
    private List<Answer> answers;
    private int HEADER = 1;
    private int CONTENT = 2;
    private int FOOTER = 3;
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
        } else if (position == answers.size() + 1) {
            return FOOTER;
        } else {
            return CONTENT;
        }

    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        if(position==0){
            holder.title_question.setText(getQuestion.getMtitle());
            holder.content_question.setText(getQuestion.getMcontent());
            holder.tag1.setText(getQuestion.getTAG1_ID());
            holder.tag2.setText(getQuestion.getTAG2_ID());
            holder.time.setText(getQuestion.getUpdatedAt());
            String userID=getQuestion.getUser().getObjectId();
            BmobQuery<_User> query = new BmobQuery<>();
            if (!userID.isEmpty()){
                query.getObject(context, userID, new GetListener<_User>() {
                    @Override
                    public void onSuccess(_User user) {
                        holder.username.setText("作者名字:"+user.getUsername());
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }else{
                holder.username.setText("");
                Toast.makeText(context,"网络连接错误",Toast.LENGTH_SHORT).show();
            }

        }

        if (position != 0) {
            holder.content.setText(answers.get(position-1).getMcontent());
            holder.username_answer.setText(answers.get(position-1).getUser().getUsername());
            holder.like.setText("赞同:"+answers.get(position-1).getUps());

//            holder.like.setOnClickListener(this);
                holder.like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Answer answer = new Answer();
                        answer.increment("ups", 1);
                        answer.update(context, answers.get(position - 1).getObjectId(), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(context, "66666", Toast.LENGTH_SHORT).show();
                                holder.like.setText("赞同:"+answers.get(position-1).getUps());
                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });
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

    @Override
    public void onClick(View v) {
//        switch(v.getId()){
//            case R.id.like_questionpage_item:
//
//                break;
//        }
    }

    class VH extends RecyclerView.ViewHolder {
        TextView content;
        TextView comment;
        ImageButton imageButton_more;
        TextView title_question;
        TextView content_question;
        TextView tag1;
        TextView tag2;
        TextView like_count;
        TextView comment_count;
        TextView username;
        TextView time;
        TextView username_answer;
        TextView like;
        public VH(View itemView) {
            super(itemView);
            like= (TextView) itemView.findViewById(R.id.like_questionpage_item);
            username_answer= (TextView) itemView.findViewById(R.id.username_questionpage);
            content = (TextView) itemView.findViewById(R.id.content_questionpage);
            comment = (TextView) itemView.findViewById(R.id.comment_questionpage);
            imageButton_more = (ImageButton) itemView.findViewById(R.id.more_questionpage);
            title_question= (TextView) itemView.findViewById(R.id.title_questionpage);
            content_question= (TextView) itemView.findViewById(R.id.content_question);
            tag1= (TextView) itemView.findViewById(R.id.tag1_questionpage);
            tag2= (TextView) itemView.findViewById(R.id.tag2_questionpage);
            like_count= (TextView) itemView.findViewById(R.id.like_questionpage1);
            comment_count= (TextView) itemView.findViewById(R.id.comment_questionpage1);
            username= (TextView) itemView.findViewById(R.id.username_question);
            time= (TextView) itemView.findViewById(R.id.time_question);
        }
    }
}
