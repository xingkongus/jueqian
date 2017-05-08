package us.xingkong.jueqian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.module.Forum.QuestionPage.QuestionActivity;

/**
 * Created by PERFECTLIN on 2017/1/11 0011.
 */

public class MyAnswerAdapter extends RecyclerView.Adapter<MyAnswerAdapter.MyViewHolder> {

    private List<Question> questions;
    private String title;
    private Context context;

    public MyAnswerAdapter(List<Question> questions, Context context) {
        this.questions = questions;
        this.context = context;
    }

    @Override
    public MyAnswerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAnswerAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myanswer, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyAnswerAdapter.MyViewHolder holder, final int position) {
        holder.tv_questiontitle.setText(questions.get(position).getMtitle());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String questionID;
                questionID = questions.get(position).getObjectId();
                Intent intent = new Intent(JueQianAPP.getAppContext(), QuestionActivity.class);
                intent.putExtra("questionid", questionID);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_myanswer_tv_questiontitle)
        TextView tv_questiontitle;
        @BindView(R.id.item)
        RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
