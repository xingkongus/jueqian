package us.xingkong.jueqian.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import us.xingkong.jueqian.R;

/**
 * Created by Garfield on 1/9/17.
 */

public class ForumRecyclerViewAdapter extends RecyclerView.Adapter<ForumRecyclerViewAdapter.VH>{
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum_main, parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.content.setText("这是第"+position+"个项目");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class VH extends RecyclerView.ViewHolder{
        TextView content;
        public VH(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
