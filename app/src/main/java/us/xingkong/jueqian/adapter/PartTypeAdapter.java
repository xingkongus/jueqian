
package us.xingkong.jueqian.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
 * Date: 17/1/11 15:23
 */

public class PartTypeAdapter extends RecyclerView.Adapter<PartTypeAdapter.PartViewHolder> {

    private Context context;
    private List<Results> read_list = new ArrayList<>();

    public List<Results> getResults() {
        return read_list;
    }

    public void replaceData(List<Results> list) {
        read_list.clear();
        read_list.addAll(list);
        notifyDataSetChanged();
    }

    public List<Results> addData(List<Results> list) {
        read_list.addAll(list);
        notifyDataSetChanged();
        return read_list;
    }

    public List<Results> addData(Results results) {
        read_list.add(results);
        notifyDataSetChanged();
        return read_list;
    }

    public PartTypeAdapter(Context context, List<Results> read_list) {
        this.context = context;
        if (read_list != null) {
            this.read_list = read_list;
        }
    }

    @Override
    public PartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PartViewHolder holder = new PartViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.item_part_type, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(PartViewHolder holder, final int position) {
        holder.textView.setText(read_list.get(position).getDesc());
        String author = read_list.get(position).getWho();
        if (author != null) {
            holder.tv_author.setText(author);
            holder.tv_author.setTextColor(Color.parseColor("#87000000"));
        } else {
            holder.tv_author.setText("");
        }
        String time = read_list.get(position).getCreatedAt();
        if (time != null) {
            holder.tv_time.setText(TimeDifferenceUtils.getTimeDifference(time));
        } else {
            holder.tv_time.setText("");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.newIntent(context,
                        read_list.get(position).getUrl(),
                        read_list.get(position).getDesc());
            }
        });
    }

    @Override
    public int getItemCount() {
        return read_list.size();
    }

    class PartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_read)
        TextView textView;
        @BindView(R.id.tv_read_author)
        TextView tv_author;
        @BindView(R.id.tv_read_time)
        TextView tv_time;

        public PartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
