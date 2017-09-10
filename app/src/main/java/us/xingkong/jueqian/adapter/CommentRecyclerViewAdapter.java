package us.xingkong.jueqian.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.DeleteListener;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Comment;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.module.Forum.QuestionPage.QuestionActivity;
import us.xingkong.jueqian.module.me.mainpage.MainPageAcitivity;


public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Handler mHandler;
    private Answer answer;
    public static final int ONE = 1;
    public static final int TWO = 2;
    private ArrayList<Comment> comments = new ArrayList<>();
    private Context context;
    private BmobFile bmobFile_head;
    private BmobFile bmobFile_comments;
    private String questionID;
    private String question_userID;
    private static final int FOOTER = 3;  //底部FootView
    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;
    public static final int NO_MORE = 2;
    //上拉加载更多状态-默认为0
    private int load_more_status = 0;

    public CommentRecyclerViewAdapter(Context context, Handler mHandler, Answer answer, ArrayList<Comment> comments, String questionID, String question_userID) {
        this.mHandler = mHandler;
        this.answer = answer;
        this.comments = comments;
        this.context = context;
        this.questionID = questionID;
        this.question_userID = question_userID;
    }

    public void addItem(int position, Comment comment1) {
        comments.add(position, comment1);
        Message msg = new Message();
        msg.what = 4;
        msg.obj = comment1;
        mHandler.sendMessage(msg);
    }

    public void addMoreItem(List<Comment> newDatas) {
        comments.addAll(newDatas);
        notifyDataSetChanged();
    }

    public void changeMoreStatus(int status) {
        load_more_status = status;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ONE) {
            return new VH_head(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_head, parent, false));
        } else if (viewType == TWO) {
            return new VH_comment(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
        }else if(viewType==FOOTER){
            return new vh_footer(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loadmore, parent, false));
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VH_head) {
            final VH_head head = (VH_head) holder;
            head.username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MainPageAcitivity.class);
                    intent.putExtra("intentUserID", answer.getUser().getObjectId());
                    context.startActivity(intent);
                }
            });

            bmobFile_head = answer.getUser().getProfile();
            if (bmobFile_head != null) {
                Picasso.with(context).load(bmobFile_head.getUrl()).into(head.icon_head);
            } else {
                head.icon_head.setBackgroundResource(R.mipmap.ic_launcher);
            }
            head.content.setText(answer.getMcontent());
            if (answer.getUser().getNickname() != null) {
                head.username.setText(answer.getUser().getNickname());
            } else {
                head.username.setText(answer.getUser().getUsername());
            }

            if (answer.getUser().getState() == 2) {
                head.lebal_head.setVisibility(View.VISIBLE);
            } else {
                head.lebal_head.setVisibility(View.GONE);
            }
            _User now = BmobUser.getCurrentUser(context, _User.class);
            if (now.getObjectId().equals(answer.getUser().getObjectId())) {
                head.delete_answer.setVisibility(View.VISIBLE);
                head.delete_answer.setClickable(true);
                head.delete_answer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new MaterialDialog.Builder(context)
                                .title("提示")
                                .content("是否删除？")
                                .negativeText("取消")
                                .positiveText("确认")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        Answer answer1 = new Answer();
                                        answer1.setObjectId(answer.getObjectId());
                                        answer1.delete(context, new DeleteListener() {
                                            @Override
                                            public void onSuccess() {
                                                QuestionActivity.close.finish();
                                                Intent intent = new Intent(context, QuestionActivity.class);
                                                intent.putExtra("questionid", questionID);
                                                intent.putExtra("question_userID", question_userID);
                                                context.startActivity(intent);
                                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                                Message message=new Message();
                                                message.what=8;
                                                message.obj=answer.getObjectId();
                                                mHandler.sendMessage(message);
                                            }

                                            @Override
                                            public void onFailure(int i, String s) {

                                            }
                                        });

                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    }
                                }).show();


                    }
                });
            } else {
                head.delete_answer.setVisibility(View.GONE);
                head.delete_answer.setClickable(false);
            }
        }
        if (holder instanceof VH_comment) {
            final VH_comment vh_comment = (VH_comment) holder;
            vh_comment.username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MainPageAcitivity.class);
                    intent.putExtra("intentUserID", comments.get(position - 1).getUser().getObjectId());
                    context.startActivity(intent);
                }
            });


            bmobFile_comments = comments.get(position - 1).getUser().getProfile();
            if (bmobFile_comments != null) {
                Picasso.with(context).load(bmobFile_comments.getUrl()).into(vh_comment.usericon_answer);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        bmobFile_comments.download(context, new DownloadFileListener() {
//                            @Override
//                            public void onSuccess(String s) {
//                                File file = new File(s);
//                                if (file.exists()) {
//                                    Bitmap bm = BitmapFactory.decodeFile(s);
//                                    vh_comment.usericon_answer.setImageBitmap(bm);
//                                } else {
//                                    return;
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(int i, String s) {
//                                Toast.makeText(context, "网络连接超时", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }).start();
            } else {
                vh_comment.usericon_answer.setBackgroundResource(R.mipmap.ic_launcher);
            }
            if (comments.get(position - 1).getUser().getNickname() != null) {
                vh_comment.username.setText(comments.get(position - 1).getUser().getNickname());
            } else {
                vh_comment.username.setText(comments.get(position - 1).getUser().getUsername());
            }

            vh_comment.content.setText(comments.get(position - 1).getMcontent());
            if (comments.get(position - 1).getUser().getState() == 2) {
                vh_comment.lebal_comment.setVisibility(View.VISIBLE);
            } else {
                vh_comment.lebal_comment.setVisibility(View.GONE);
            }


            _User now = BmobUser.getCurrentUser(context, _User.class);
            if (now.getObjectId().equals(comments.get(position - 1).getUser().getObjectId()) || now.getObjectId().equals(answer.getObjectId())) {
                vh_comment.delete_comments.setVisibility(View.VISIBLE);
                vh_comment.delete_comments.setClickable(true);
                final String commentID1=comments.get(position-1).getObjectId();
                vh_comment.delete_comments.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialDialog.Builder(context)
                                .title("提示")
                                .content("是否删除？")
                                .negativeText("取消")
                                .positiveText("确认")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        Comment comment = new Comment();
                                        comment.setObjectId(comments.get(position - 1).getObjectId());
                                        comment.delete(context, new DeleteListener() {
                                            @Override
                                            public void onSuccess() {
                                                comments.remove(position - 1);
                                                notifyItemRemoved(position - 1);
                                                notifyItemRangeChanged(position - 1, comments.size());
                                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                                Message msg=new Message();
                                                Bundle bundle=new Bundle();
                                                bundle.putString("commentID",commentID1);
                                                msg.setData(bundle);
                                                msg.what=11;
                                                mHandler.sendMessage(msg);
                                            }

                                            @Override
                                            public void onFailure(int i, String s) {

                                            }
                                        });

                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    }
                                }).show();


                    }
                });
            } else {
                vh_comment.delete_comments.setVisibility(View.GONE);
                vh_comment.delete_comments.setClickable(false);
            }
        }
        if (holder instanceof vh_footer) {
            final vh_footer footer = (vh_footer) holder;
            switch (load_more_status) {
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


    @Override
    public int getItemCount() {
        return comments.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ONE;
        } else if (position + 1 == getItemCount()) {
            return FOOTER;
        } else {
            return TWO;
        }

    }

    class VH_head extends RecyclerView.ViewHolder {
        TextView username;
        //        TextView time;
        TextView content;
        ImageView icon_head;
        ImageView lebal_head;
        ImageButton delete_answer;

        public VH_head(View itemView) {
            super(itemView);
            delete_answer = (ImageButton) itemView.findViewById(R.id.delete_answer);
            username = (TextView) itemView.findViewById(R.id.username_comment_head);
//            time= (TextView) itemView.findViewById(R.id.time_comment_head);
            content = (TextView) itemView.findViewById(R.id.answer_content_comment_head);
            icon_head = (ImageView) itemView.findViewById(R.id.usericon_comment_head);
            lebal_head = (ImageView) itemView.findViewById(R.id.lebal_head);
        }
    }

    class VH_comment extends RecyclerView.ViewHolder {
        TextView username;
        //        TextView time;
        TextView content;
        ImageView usericon_answer;
        LinearLayout itemLayout;
        ImageView lebal_comment;
        ImageButton delete_comments;

        public VH_comment(View itemView) {
            super(itemView);
            delete_comments = (ImageButton) itemView.findViewById(R.id.delete_comments);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.item_comment_layout);
            username = (TextView) itemView.findViewById(R.id.username_comment);
//            time= (TextView) itemView.findViewById(R.id.time_comment);
            content = (TextView) itemView.findViewById(R.id.answer_comment);
            usericon_answer = (ImageView) itemView.findViewById(R.id.usericon_comment);
            lebal_comment = (ImageView) itemView.findViewById(R.id.lebal_comment);
        }
    }
    class vh_footer extends RecyclerView.ViewHolder {
        TextView loadmore;
        ProgressBar pro;

        public vh_footer(View itemView) {
            super(itemView);
            loadmore = (TextView) itemView.findViewById(R.id.item_loadmore_text);
            pro = (ProgressBar) itemView.findViewById(R.id.pro);
        }
    }

}
