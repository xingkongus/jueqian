package us.xingkong.jueqian.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Comment;

/**
 * Created by PERFECTLIN on 2017/1/11 0011.
 */

public class MyMessageRecyclerAdapter extends RecyclerView.Adapter<MyMessageRecyclerAdapter.MyViewHolder> {

    private Handler mHandler;
    private List<Comment> answers;

    public MyMessageRecyclerAdapter(Handler mHandler, List<Comment> answers) {
        this.mHandler = mHandler;
        this.answers = answers;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mymessage, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_content.setText(answers.get(position).getMcontent());
        holder.tv_username.setText(answers.get(position).getUser().getUsername());
        holder.tv_time.setText(answers.get(position).getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView civ_touxiang; //头像
        private TextView tv_username; // 用户名
        private TextView tv_action; // 消息通知类型
        private TextView tv_time; //消息时间
        private TextView tv_content; //消息内容

        public MyViewHolder(View itemView) {
            super(itemView);
            civ_touxiang = (CircleImageView) itemView.findViewById(R.id.item_mymessage_circleimageview);
            tv_username = (TextView) itemView.findViewById(R.id.item_mymessage_tv_username);
            tv_action = (TextView) itemView.findViewById(R.id.item_mymessage_tv_action);
            tv_time = (TextView) itemView.findViewById(R.id.item_mymessage_tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.item_mymessage_tv_content);
        }
    }
}
