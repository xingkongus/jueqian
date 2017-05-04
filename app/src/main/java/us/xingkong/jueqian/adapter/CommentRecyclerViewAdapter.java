package us.xingkong.jueqian.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.File;
import java.util.ArrayList;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DownloadFileListener;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Comment;


public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Handler mHandler;
    private Answer answer;
    public static final int ONE = 1;
    public static final int TWO = 2;
    private ArrayList<Comment> comments=new ArrayList<>();
    private Context context;
    private BmobFile bmobFile_head;
    private BmobFile bmobFile_comments;

    public CommentRecyclerViewAdapter(Context context,Handler mHandler, Answer answer,ArrayList<Comment> comments) {
        this.mHandler = mHandler;
        this.answer = answer;
        this.comments=comments;
        this.context=context;
    }

    public void addItem(int position,Comment comment1){
        comments.add(position,comment1);
        Message msg=new Message();
        msg.what=4;
        msg.obj=comment1;
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
            bmobFile_head = answer.getUser().getProfile();
            if (bmobFile_head != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        bmobFile_head.download(context, new DownloadFileListener() {
                            @Override
                            public void onSuccess(String s) {
                                File file = new File(s);
                                if (file.exists()) {
                                    Bitmap bm = BitmapFactory.decodeFile(s);
                                    head.icon_head.setImageBitmap(bm);
                                } else {
                                    return;
                                }
                            }
                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(context, "网络连接超时", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }else{
                head.icon_head.setBackgroundResource(R.mipmap.ic_launcher);
            }
            head.content.setText(answer.getMcontent());
            head.username.setText(answer.getUser().getUsername());
            if (answer.getUser().getState() == 2) {
                head.lebal_head.setVisibility(View.VISIBLE);
            }else{
                head.lebal_head.setVisibility(View.GONE);
            }
        }
        if (holder instanceof VH_comment) {
            final VH_comment vh_comment = (VH_comment) holder;
            bmobFile_comments=comments.get(position-1).getUser().getProfile();
            if (bmobFile_comments != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        bmobFile_comments.download(context, new DownloadFileListener() {
                            @Override
                            public void onSuccess(String s) {
                                File file = new File(s);
                                if (file.exists()) {
                                    Bitmap bm = BitmapFactory.decodeFile(s);
                                    vh_comment.usericon_answer.setImageBitmap(bm);
                                } else {
                                    return;
                                }
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(context, "网络连接超时", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
            else{
                vh_comment.usericon_answer.setBackgroundResource(R.mipmap.ic_launcher);
            }
            vh_comment.username.setText(comments.get(position-1).getUser().getUsername());
            vh_comment.content.setText(comments.get(position-1).getMcontent());
            if (comments.get(position - 1).getUser().getState() == 2) {
                vh_comment.lebal_comment.setVisibility(View.VISIBLE);
            }else{
                vh_comment.lebal_comment.setVisibility(View.GONE);
            }
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
        ImageView icon_head;
        ImageView lebal_head;
        public VH_head(View itemView) {
            super(itemView);
            username= (TextView) itemView.findViewById(R.id.username_comment_head);
//            time= (TextView) itemView.findViewById(R.id.time_comment_head);
            content= (TextView) itemView.findViewById(R.id.answer_content_comment_head);
            icon_head= (ImageView) itemView.findViewById(R.id.usericon_comment_head);
            lebal_head= (ImageView) itemView.findViewById(R.id.lebal_head);
        }
    }
    class VH_comment extends RecyclerView.ViewHolder {
        TextView username;
//        TextView time;
        TextView content;
        ImageView usericon_answer;
        ImageButton zan;
        LinearLayout itemLayout;
        ImageView lebal_comment;
        public VH_comment(View itemView) {
            super(itemView);
            itemLayout= (LinearLayout) itemView.findViewById(R.id.item_comment_layout);
            username= (TextView) itemView.findViewById(R.id.username_comment);
//            time= (TextView) itemView.findViewById(R.id.time_comment);
            content= (TextView) itemView.findViewById(R.id.answer_comment);
            usericon_answer= (ImageView) itemView.findViewById(R.id.usericon_comment);
            zan= (ImageButton) itemView.findViewById(R.id.zan_comment);
            lebal_comment= (ImageView) itemView.findViewById(R.id.lebal_comment);
        }
    }
}
