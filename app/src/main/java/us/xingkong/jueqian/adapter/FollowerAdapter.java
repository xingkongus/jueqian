package us.xingkong.jueqian.adapter;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Follow;
import us.xingkong.jueqian.module.me.mainpage.MainPageAcitivity;

/**
 * Created by PERFECTLIN on 2017/1/11 0011.
 */

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.MyViewHolder> implements View.OnLongClickListener {
    private Handler mHandler;
    private List<Follow> follows;

    public FollowerAdapter(Handler mHandler, List<Follow> follows) {
        this.mHandler = mHandler;
        this.follows = follows;
    }


    @Override
    public FollowerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowerAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_follower, parent, false));
    }

    @Override
    public void onBindViewHolder(FollowerAdapter.MyViewHolder holder, final int position) {
        Glide.with(JueQianAPP.getAppContext()).load(follows.get(position).getFollowUser().getProfile().getUrl()).into(holder.civ_touxiang);
        holder.tv_nickname.setText(follows.get(position).getFollowUser().getNickname());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JueQianAPP.getAppContext(), MainPageAcitivity.class);
                intent.putExtra("intentUserID", follows.get(position).getFollowUser().getObjectId());
                JueQianAPP.getAppContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return follows.size();
    }

    @Override
    public boolean onLongClick(final View view) {

        return false;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nickname;
        private RelativeLayout item;
        private CircleImageView civ_touxiang;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_nickname = (TextView) itemView.findViewById(R.id.item_follower_tv_nickname);
            item = (RelativeLayout) itemView.findViewById(R.id.item_follower);
            civ_touxiang = (CircleImageView) itemView.findViewById(R.id.item_follower_civ_touxiang);
        }
    }
}
