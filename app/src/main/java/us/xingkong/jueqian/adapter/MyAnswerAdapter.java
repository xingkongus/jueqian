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
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;

/**
 * Created by PERFECTLIN on 2017/1/11 0011.
 */

public class MyAnswerAdapter extends RecyclerView.Adapter<MyAnswerAdapter.MyViewHolder> {
    private Handler mHandler;
    private List<Answer> answers;

    public MyAnswerAdapter(Handler mHandler, List<Answer> answers) {
        this.mHandler = mHandler;
        this.answers = answers;
    }


    @Override
    public MyAnswerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAnswerAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myanswer, parent, false));
    }

    @Override
    public void onBindViewHolder(MyAnswerAdapter.MyViewHolder holder, int position) {
        holder.tv_questiontitle.setText(answers.get(position).getQuestion().getMtitle());

    }

    @Override
    public int getItemCount() {
        System.out.println("ssssssssssssssssssssssss"+answers.size());
        return answers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_questiontitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_questiontitle = (TextView) itemView.findViewById(R.id.item_myanswer_tv_questiontitle);
        }
    }
}
