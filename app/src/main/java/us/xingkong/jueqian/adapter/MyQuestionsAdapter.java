package us.xingkong.jueqian.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;

/**
 * Created by PERFECTLIN on 2017/1/14 0014.
 */

public class MyQuestionsAdapter extends RecyclerView.Adapter<MyQuestionsAdapter.MyViewHolder> {
    private Handler mHandler;
    private List<Question> questions;

    public MyQuestionsAdapter(Handler mHandler, List<Question> questions) {
        this.mHandler = mHandler;
        this.questions = questions;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyQuestionsAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myquestions, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_title.setText(questions.get(position).getMtitle());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title= (TextView) itemView.findViewById(R.id.item_myquestions_tv_questiontitle);
        }
    }
}
