package us.xingkong.jueqian.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.module.Forum.QuestionPage.QuestionActivity;

/**
 * Created by PERFECTLIN on 2017/1/11 0011.
 */

public class MyRecentLookAdapter extends RecyclerView.Adapter<MyRecentLookAdapter.MyViewHolder> {
    private Handler mHandler;
    private List<Question> questions;
    private Context context;

    public MyRecentLookAdapter(Handler mHandler, List<Question> questions, Context context) {
        this.mHandler = mHandler;
        this.questions = questions;
        this.context = context;
    }


    @Override
    public MyRecentLookAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyRecentLookAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myrecentlook, parent, false));
    }

    @Override
    public void onBindViewHolder(MyRecentLookAdapter.MyViewHolder holder, final int position) {
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
        private TextView tv_questiontitle;
        private RelativeLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_questiontitle = (TextView) itemView.findViewById(R.id.item_myrecentlook_tv_questiontitle);
            layout = (RelativeLayout) itemView.findViewById(R.id.myrecentlook_item);
        }
    }
}
