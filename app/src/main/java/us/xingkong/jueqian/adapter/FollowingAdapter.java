package us.xingkong.jueqian.adapter;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.module.Forum.QuestionPage.QuestionActivity;
import us.xingkong.jueqian.module.me.mainpage.MainPageAcitivity;

/**
 * Created by PERFECTLIN on 2017/1/11 0011.
 */

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.MyViewHolder> implements View.OnLongClickListener {
    private Handler mHandler;
    private List<_User> followings;

    public FollowingAdapter(Handler mHandler, List<_User> followings) {
        this.mHandler = mHandler;
        this.followings = followings;
    }


    @Override
    public FollowingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowingAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_following, parent, false));
    }

    @Override
    public void onBindViewHolder(FollowingAdapter.MyViewHolder holder, final int position) {
        Glide.with(JueQianAPP.getAppContext()).load(followings.get(position).getProfile().getUrl()).into(holder.civ_touxiang);
        holder.tv_nickname.setText(followings.get(position).getNickname());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JueQianAPP.getAppContext(), MainPageAcitivity.class);
                intent.putExtra("intentUserID", followings.get(position).getObjectId());
                JueQianAPP.getAppContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return followings.size();
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
            tv_nickname = (TextView) itemView.findViewById(R.id.item_following_tv_nickname);
            item = (RelativeLayout) itemView.findViewById(R.id.item_following);
            civ_touxiang = (CircleImageView) itemView.findViewById(R.id.item_following_civ_touxiang);
        }
    }
}
