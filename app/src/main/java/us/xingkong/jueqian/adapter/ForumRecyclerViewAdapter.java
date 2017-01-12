package us.xingkong.jueqian.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.Constants;
import us.xingkong.jueqian.bean.ForumBean.GsonBean.GSON_ForumPageBean;

import static us.xingkong.jueqian.base.Constants.REQUEST_INTENT_TO_QUESTIONPAGE;

/**
 * Created by Garfield on 1/9/17.
 */

public class ForumRecyclerViewAdapter extends RecyclerView.Adapter<ForumRecyclerViewAdapter.VH> implements View.OnClickListener {
    ArrayList<GSON_ForumPageBean> infoSets;
    Handler mHandler;
    Context mContext;


    public ForumRecyclerViewAdapter(ArrayList<GSON_ForumPageBean> infoSets, Handler handler, Context mContext) {
        this.infoSets = infoSets;
        mHandler = handler;
        this.mContext = mContext;
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum_main, parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.linearLayout.setOnClickListener(this);
        holder.title.setText(infoSets.get(position).getMtitle());
        holder.username.setText(infoSets.get(position).getSender());
        holder.count_answer.setText(infoSets.get(position).getAnswer_count());
        holder.tag1.setText(infoSets.get(position).getTAG1());
        holder.tag2.setText(infoSets.get(position).getTAG2());
//        holder.profile
        Glide.with(mContext)
                .load(infoSets.get(position).getProfileURL())
//                .placeholder(R.drawable.loading_spinner)
                .crossFade()
                .into(holder.profile);
        if (infoSets.get(position).getSender_state() == Constants.STATE_MEMBER) {
            holder.userState.setBackgroundColor(Color.BLACK);
        } else if (infoSets.get(position).getSender_state() == Constants.STATE_MEMBER) {
            holder.userState.setBackgroundColor(Color.CYAN);
        }


    }

    @Override
    public int getItemCount() {
        return infoSets.size();
    }

    @Override
    public void onClick(View view) {
        mHandler.sendEmptyMessage(REQUEST_INTENT_TO_QUESTIONPAGE);
    }

    class VH extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView title;
        TextView username;
        TextView count_answer;
        TextView tag1;
        TextView tag2;
        ImageView profile;
        ImageView userState;

        public VH(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_forum);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.item_forum);
            profile = (ImageView) itemView.findViewById(R.id.user_icon_forum);
            username = (TextView) itemView.findViewById(R.id.username_forum);
            userState = (ImageView) itemView.findViewById(R.id.user_state_forum);
            count_answer = (TextView) itemView.findViewById(R.id.count_answer_forum);
            tag1 = (TextView) itemView.findViewById(R.id.TAG1_forum);
            tag2 = (TextView) itemView.findViewById(R.id.TAG2_forum);
        }
    }


}
