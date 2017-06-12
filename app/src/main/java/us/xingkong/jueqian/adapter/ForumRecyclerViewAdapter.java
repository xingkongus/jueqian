package us.xingkong.jueqian.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import de.hdodenhof.circleimageview.CircleImageView;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.module.Forum.QuestionPage.QuestionActivity;
import us.xingkong.jueqian.module.me.mainpage.MainPageAcitivity;

/**
 * Created by Garfield on 1/9/17.
 */

public class ForumRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView
    //上拉加载更多
    public static final int  PULLUP_LOAD_MORE=0;
    //正在加载中
    public static final int  LOADING_MORE=1;
    public static final int  NO_MORE=2;
    //上拉加载更多状态-默认为0
    private int load_more_status=0;
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
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum_main, parent, false));
        } else if (viewType == TYPE_FOOTER) {
            return new Footer(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loadmore, parent, false));
        } else {
            return null;
        }

//        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum_main, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VH) {
            final VH vh = (VH) holder;
            bmobFile = infoSets.get(position).getUser().getProfile();
            if (bmobFile != null) {
                Picasso.with(mContext).load(bmobFile.getUrl()).into(vh.profile);

            } else {
                vh.profile.setBackgroundResource(R.mipmap.ic_launcher);
            }
            vh.title.setText(infoSets.get(position).getMtitle());
            vh.tag1.setText(infoSets.get(position).getTAG1_ID());
            vh.tag2.setText(infoSets.get(position).getTAG2_ID());
            vh.count_answer.setText(infoSets.get(position).getAnswer_count() + "回答");
            if (infoSets.get(position).getUser().getNickname() != null) {
                vh.username.setText(infoSets.get(position).getUser().getNickname());
            } else {
                vh.username.setText(infoSets.get(position).getUser().getUsername());
            }

            if (infoSets.get(position).getUser().getState() == 2) {
                vh.userState.setVisibility(View.VISIBLE);
            } else {
                vh.userState.setVisibility(View.GONE);
            }


            vh.linearLayout.setOnClickListener(new View.OnClickListener() {
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
            vh.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(JueQianAPP.getAppContext(), MainPageAcitivity.class);
                    intent.putExtra("intentUserID", infoSets.get(position).getUser().getObjectId());
                    mContext.startActivity(intent);
                }
            });
        }
        if (holder instanceof Footer) {
            final Footer footer = (Footer) holder;
            switch (load_more_status){
                case PULLUP_LOAD_MORE:
                    footer.pro.setVisibility(View.GONE);
                    footer.loadmore.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footer.pro.setVisibility(View.VISIBLE);
                    footer.loadmore.setText("正在加载更多数据...");
                    break;
                case NO_MORE:
                    footer.loadmore.setText("已经没有更多啦...");
                    footer.pro.setVisibility(View.GONE);
                    break;
            }
        }
    }
    public void addMoreItem(List<Question> newDatas) {
        infoSets.addAll(newDatas);
        notifyDataSetChanged();
    }

    public void changeMoreStatus(int status){
        load_more_status=status;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return infoSets.size()+1;
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
    class Footer extends RecyclerView.ViewHolder{
    TextView loadmore;
        ProgressBar pro;
        public Footer(View itemView) {
            super(itemView);
            loadmore= (TextView) itemView.findViewById(R.id.item_loadmore_text);
            pro= (ProgressBar) itemView.findViewById(R.id.pro);
        }
    }

}
