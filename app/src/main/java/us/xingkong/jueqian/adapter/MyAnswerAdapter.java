package us.xingkong.jueqian.adapter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.module.Forum.QuestionPage.QuestionActivity;

/**
 * Created by PERFECTLIN on 2017/1/11 0011.
 */

public class MyAnswerAdapter extends RecyclerView.Adapter<MyAnswerAdapter.MyViewHolder> {

    private List<Question> questions;
    private String title;

    public MyAnswerAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public MyAnswerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAnswerAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myanswer, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyAnswerAdapter.MyViewHolder holder, final int position) {
//        final BmobQuery<Question> query = new BmobQuery<>();
//        query.getObject(JueQianAPP.getAppContext(), questionID, new GetListener<Question>() {
//            @Override
//            public void onSuccess(Question question) {
//                title = question.getMtitle();
//                Toast.makeText(JueQianAPP.getAppContext(), "获取问题标题成功" + title, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                Toast.makeText(JueQianAPP.getAppContext(), "获取问题标题失败 case by:" + s, Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.tv_questiontitle.setText(questions.get(position).getMtitle());


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String questionID;
                questionID = questions.get(position).getObjectId();
                Intent intent = new Intent(JueQianAPP.getAppContext(), QuestionActivity.class);
                intent.putExtra("questionid", questionID);
                JueQianAPP.getAppContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_questiontitle;
        private RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_questiontitle = (TextView) itemView.findViewById(R.id.item_myanswer_tv_questiontitle);
            layout = (RelativeLayout) itemView.findViewById(R.id.item);
        }
    }
}
