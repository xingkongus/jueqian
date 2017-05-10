package us.xingkong.jueqian.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DownloadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.module.Forum.QuestionPage.QuestionActivity;
import us.xingkong.jueqian.module.me.mainpage.MainPageAcitivity;

/**
 * Created by Garfield on 1/9/17.
 */

public class ForumRecyclerViewAdapter extends RecyclerView.Adapter<ForumRecyclerViewAdapter.VH> {
    List<Question> infoSets;
    Handler mHandler;
    Context mContext;
    String questionID;
    BmobFile bmobFile;
    String userID;

    public ForumRecyclerViewAdapter(List<Question> infoSets, Handler handler, Context mContext) {
        this.infoSets = infoSets;
        mHandler = handler;
        this.mContext = mContext;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum_main, parent, false));
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        bmobFile = infoSets.get(position).getUser().getProfile();
        if (bmobFile != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    bmobFile.download(mContext, new DownloadFileListener() {
                        @Override
                        public void onSuccess(String s) {
                            File file = new File(s);
                            if (file.exists()) {
                                Bitmap bm = BitmapFactory.decodeFile(s);
                                holder.profile.setImageBitmap(bm);
                            } else {
                                return;
                            }
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(mContext, "网络连接超时", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        } else {
            holder.profile.setBackgroundResource(R.mipmap.ic_launcher);
        }
        holder.title.setText(infoSets.get(position).getMtitle());
        holder.tag1.setText(infoSets.get(position).getTAG1_ID());
        holder.tag2.setText(infoSets.get(position).getTAG2_ID());
        holder.count_answer.setText(infoSets.get(position).getAnswer_count() + "回答");
        if (infoSets.get(position).getUser().getNickname() != null) {
            holder.username.setText(infoSets.get(position).getUser().getNickname());
        } else {
            holder.username.setText(infoSets.get(position).getUser().getUsername());
        }

        if (infoSets.get(position).getUser().getState() == 2) {
            holder.userState.setVisibility(View.VISIBLE);
        } else {
            holder.userState.setVisibility(View.GONE);
        }


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    questionID = infoSets.get(position).getObjectId();
                    userID = infoSets.get(position).getUser().getObjectId();
                    Intent intent = new Intent(mContext, QuestionActivity.class);
                    intent.putExtra("questionid", questionID);
                    intent.putExtra("question_userID", userID);
                    mContext.startActivity(intent);
            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(JueQianAPP.getAppContext(), MainPageAcitivity.class);
                    intent.putExtra("intentUserID", infoSets.get(position).getUser().getObjectId());
                    mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return infoSets.size();
    }


    class VH extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView title;
        TextView username;
        TextView count_answer;
        TextView tag1;
        TextView tag2;
        CircleImageView profile;
        ImageView userState;
        LinearLayout item;

        public VH(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_forum);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.item_forum);
            profile = (CircleImageView) itemView.findViewById(R.id.user_icon_forum);
            username = (TextView) itemView.findViewById(R.id.username_forum);
            userState = (ImageView) itemView.findViewById(R.id.user_state_forum);
            count_answer = (TextView) itemView.findViewById(R.id.count_answer_forum);
            tag1 = (TextView) itemView.findViewById(R.id.TAG1_forum);
            tag2 = (TextView) itemView.findViewById(R.id.TAG2_forum);
            item = (LinearLayout) itemView.findViewById(R.id.forum_mainpager);
        }
    }

}
