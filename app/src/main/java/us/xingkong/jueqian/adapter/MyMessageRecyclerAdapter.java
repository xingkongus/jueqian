package us.xingkong.jueqian.adapter;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Comment;
import us.xingkong.jueqian.bean.ForumBean.BombBean.NewMessage;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.module.Forum.QuestionPage.Comment.CommentActivity;
import us.xingkong.jueqian.module.Forum.QuestionPage.QuestionActivity;

/**
 * Created by PERFECTLIN on 2017/1/11 0011.
 */

public class MyMessageRecyclerAdapter extends RecyclerView.Adapter<MyMessageRecyclerAdapter.MyViewHolder> {

    private Handler mHandler;
    private List<NewMessage> messages;

    public MyMessageRecyclerAdapter(Handler mHandler, List<NewMessage> messages) {
        this.mHandler = mHandler;
        this.messages = messages;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mymessage, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        switch (messages.get(position).getTYPE()) {
            case 1:
                holder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(JueQianAPP.getAppContext(), CommentActivity.class);
                        intent.putExtra("questionid", messages.get(position).getMessAnswer().getQuestion().getObjectId());
                        intent.putExtra("question_userID", messages.get(position).getMessAnswer().getQuestion().getUser().getObjectId());
                        JueQianAPP.getAppContext().startActivity(intent);
                    }
                });
                if (messages.get(position).getMessComment().getMcontent() == null || messages.get(position).getSender().getNickname() == null || messages.get(position).getCreatedAt() == null) {
                    Toast.makeText(JueQianAPP.getAppContext(), "数据异常", Toast.LENGTH_SHORT).show();
                    break;
                }
                String content = messages.get(position).getMessComment().getMcontent();
                String nickname = messages.get(position).getSender().getNickname();
                String time = messages.get(position).getCreatedAt();
                holder.tv_action.setText("评论了你");
                holder.tv_content.setText(content);
                holder.tv_nickname.setText(nickname);
                holder.tv_time.setText(time);
                BmobQuery<_User> query = new BmobQuery<>();
                query.addWhereEqualTo("objectId", messages.get(position).getSender().getObjectId());
                query.addQueryKeys("profile");
                query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
                query.findObjects(JueQianAPP.getAppContext(), new FindListener<_User>() {
                    @Override
                    public void onSuccess(List<_User> list) {
                        if (list.size() == 0) {
                            return;
                        }
                        BmobFile bmobFile = list.get(0).getProfile();
                        if (bmobFile == null) {
                            return;
                        }
                        String profileURL = bmobFile.getUrl();
                        Glide.with(JueQianAPP.getAppContext()).load(profileURL).into(holder.civ_touxiang);
                    }

                    @Override
                    public void onError(int i, String s) {
                    }
                });
                break;
            case 2:
                holder.item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(JueQianAPP.getAppContext(), QuestionActivity.class);
                        intent.putExtra("questionid", messages.get(position).getMessAnswer().getQuestion().getObjectId());
                        intent.putExtra("question_userID", messages.get(position).getMessAnswer().getQuestion().getUser().getObjectId());
                        JueQianAPP.getAppContext().startActivity(intent);
                    }
                });
                if (messages.get(position).getMessAnswer().getMcontent() == null || messages.get(position).getSender().getNickname() == null || messages.get(position).getCreatedAt() == null) {
                    Toast.makeText(JueQianAPP.getAppContext(), "数据异常", Toast.LENGTH_SHORT).show();
                    break;
                }
                String content1 = messages.get(position).getMessAnswer().getMcontent();
                String nickname1 = messages.get(position).getSender().getNickname();
                String time1 = messages.get(position).getCreatedAt();
                if (content1 == null || nickname1 == null || time1 == null) {
                    Toast.makeText(JueQianAPP.getAppContext(), "数据异常", Toast.LENGTH_SHORT).show();
                    break;
                }
                holder.tv_action.setText("回答了你");
                holder.tv_content.setText(content1);
                holder.tv_nickname.setText(nickname1);
                holder.tv_time.setText(time1);
                BmobQuery<_User> query1 = new BmobQuery<>();
                query1.addWhereEqualTo("objectId", messages.get(position).getSender().getObjectId());
                query1.addQueryKeys("profile");
                query1.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
                query1.findObjects(JueQianAPP.getAppContext(), new FindListener<_User>() {
                    @Override
                    public void onSuccess(List<_User> list) {
                        if (list.size() == 0) {
                            return;
                        }
                        BmobFile bmobFile = list.get(0).getProfile();
                        if (bmobFile == null) {
                            return;
                        }
                        String profileURL = bmobFile.getUrl();
                        Glide.with(JueQianAPP.getAppContext()).load(profileURL).into(holder.civ_touxiang);
                    }

                    @Override
                    public void onError(int i, String s) {
                    }
                });
                break;
        }


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView civ_touxiang; //头像
        private TextView tv_nickname; // 用户名
        private TextView tv_action; // 消息通知类型
        private TextView tv_time; //消息时间
        private TextView tv_content; //消息内容
        private RelativeLayout item;

        public MyViewHolder(View itemView) {
            super(itemView);
            civ_touxiang = (CircleImageView) itemView.findViewById(R.id.item_mymessage_circleimageview);
            tv_nickname = (TextView) itemView.findViewById(R.id.item_mymessage_tv_nickname);
            tv_action = (TextView) itemView.findViewById(R.id.item_mymessage_tv_action);
            tv_time = (TextView) itemView.findViewById(R.id.item_mymessage_tv_time);
            tv_content = (TextView) itemView.findViewById(R.id.item_mymessage_tv_content);
            item = (RelativeLayout) itemView.findViewById(R.id.item_mymessage);
        }
    }
}
