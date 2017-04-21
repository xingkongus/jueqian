package us.xingkong.jueqian.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.GetListener;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Comment;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;


public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Handler mHandler;
    private Answer answer;
    public static final int ONE = 1;
    public static final int TWO = 2;
    private ArrayList<Comment> comments=new ArrayList<>();
    private Context context;

    public CommentRecyclerViewAdapter(Context context,Handler mHandler, Answer answer,ArrayList<Comment> comments) {
        this.mHandler = mHandler;
        this.answer = answer;
        this.comments=comments;
        this.context=context;
    }

    public void addItem(int position,Comment comment1){
        comments.add(position,comment1);
        Message msg=new Message();
        msg.obj=position;
        msg.what=4;
        mHandler.sendMessage(msg);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==ONE){
            return new VH_head(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_head, parent, false));
        }else if (viewType==TWO){
            return new VH_comment(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false));
        }else{
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VH_head) {
            final VH_head head = (VH_head) holder;
            head.content.setText(answer.getMcontent());
            String userid=answer.getUser().getObjectId();
            BmobQuery<_User> query = new BmobQuery<>();
            try{
                query.getObject(context, userid, new GetListener<_User>() {
                    @Override
                    public void onSuccess(_User user) {
                        head.username.setText(user.getUsername());
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(context,"网络连接错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){
                head.username.setText("");
                Toast.makeText(context,"网络连接错误", Toast.LENGTH_SHORT).show();
            }
        }
        if (holder instanceof VH_comment) {
            VH_comment vh_comment = (VH_comment) holder;
            vh_comment.username.setText(comments.get(position-1).getUser().getUsername());
//            vh_comment.time.setText(comments.get(position-1).getTime());
            vh_comment.content.setText(comments.get(position-1).getMcontent());
        }
    }


    @Override
    public int getItemCount() {
        return comments.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return ONE;
        }else {
            return TWO;
        }

    }

    class VH_head extends RecyclerView.ViewHolder {
        TextView username;
//        TextView time;
        TextView content;
        ImageView icon;

        public VH_head(View itemView) {
            super(itemView);
            username= (TextView) itemView.findViewById(R.id.username_comment_head);
//            time= (TextView) itemView.findViewById(R.id.time_comment_head);
            content= (TextView) itemView.findViewById(R.id.answer_content_comment_head);
            icon= (ImageView) itemView.findViewById(R.id.usericon_comment_head);
        }
    }
    class VH_comment extends RecyclerView.ViewHolder {
        TextView username;
//        TextView time;
        TextView content;
        ImageView usericon;
        ImageButton zan;
        LinearLayout itemLayout;
        public VH_comment(View itemView) {
            super(itemView);
            itemLayout= (LinearLayout) itemView.findViewById(R.id.item_comment_layout);
            username= (TextView) itemView.findViewById(R.id.username_comment);
//            time= (TextView) itemView.findViewById(R.id.time_comment);
            content= (TextView) itemView.findViewById(R.id.answer_comment);
            usericon= (ImageView) itemView.findViewById(R.id.usericon_comment);
            zan= (ImageButton) itemView.findViewById(R.id.zan_comment);
        }
    }
}
