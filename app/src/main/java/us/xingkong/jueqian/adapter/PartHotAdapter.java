package us.xingkong.jueqian.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.bean.RealSBean.Results;
import us.xingkong.jueqian.module.common.WebActivity;
import us.xingkong.jueqian.utils.TimeDifferenceUtils;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/11 15:01
 */

public class PartHotAdapter extends RecyclerView.Adapter<PartHotAdapter.PartViewHolder> {

    private Context context;
    private List<Results> part_list = new ArrayList<>();

    public List<Results> getDatas() {
        return part_list;
    }

    public void replaceData(List<Results> list) {
        part_list.clear();
        part_list.addAll(list);
        notifyDataSetChanged();
    }

    public List<Results> addData(List<Results> list) {
        part_list.addAll(list);
        notifyDataSetChanged();
        return part_list;
    }

    public List<Results> addData(Results results) {
        part_list.add(results);
        notifyDataSetChanged();
        return part_list;
    }

    public PartHotAdapter(Context context, List<Results> part_list) {
        this.context = context;
        if (part_list != null) {
            this.part_list = part_list;
        }
    }

    @Override
    public PartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PartViewHolder holder = new PartViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_part_hot, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(PartViewHolder holder, final int position) {
        List<String> images = part_list.get(position).getImages();
        if (!images.isEmpty()) {
            holder.iv_img.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(images.get(0))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.iv_img);
        } else {
            holder.iv_img.setVisibility(View.GONE);
        }
        holder.relativeLayout.setVisibility(View.VISIBLE);
        holder.textView.setText(part_list.get(position).getDesc());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.newIntent(context,
                        part_list.get(position).getUrl(),
                        part_list.get(position).getDesc());
            }
        });

        Uri uri = null;
        switch (part_list.get(position).getType()) {
            case "Android":
                uri = Uri.parse("res:///" + R.mipmap.android_icon);
                break;
            case "iOS":
                uri = Uri.parse("res:///" + R.mipmap.ios_icon);
                break;
            case "前端":
                uri = Uri.parse("res:///" + R.mipmap.js_icon);
                break;
            case "拓展资源":
                uri = Uri.parse("res:///" + R.mipmap.other_icon);
                break;
        }

        holder.iv_icon.setImageURI(uri);
        String author = part_list.get(position).getWho();
        if (author != null) {
            holder.tv_author.setText(author);
            holder.tv_author.setTextColor(Color.parseColor("#87000000"));
        } else {
            holder.tv_author.setText("");
        }

        String time = part_list.get(position).getCreatedAt();
        String type = part_list.get(position).getType();
        if (time != null) {
            holder.tv_time.setText(TimeDifferenceUtils.getTimeDifference(time));
        } else {
            holder.tv_time.setText("");
        }
        holder.tv_type.setText(type);

    }

    @Override
    public int getItemCount() {
        return part_list == null ? 0 : part_list.size();
    }

    class PartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_part_message)
        RelativeLayout relativeLayout;
        @BindView(R.id.tv_part_author)
        TextView tv_author;
        @BindView(R.id.tv_part_time)
        TextView tv_time;
        @BindView(R.id.tv_part_type)
        TextView tv_type;
        @BindView(R.id.iv_part_type_icon)
        ImageView iv_icon;
        @BindView(R.id.iv_part_img)
        ImageView iv_img;
        @BindView(R.id.tv_part)
        TextView textView;

        public PartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
