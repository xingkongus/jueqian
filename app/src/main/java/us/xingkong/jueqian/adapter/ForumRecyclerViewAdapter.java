package us.xingkong.jueqian.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import us.xingkong.jueqian.R;

/**
 * Created by Garfield on 1/9/17.
 */

public class ForumRecyclerViewAdapter extends RecyclerView.Adapter<ForumRecyclerViewAdapter.VH>{
    ArrayList<ArrayList> infoSets;
    public ForumRecyclerViewAdapter(ArrayList<ArrayList> infoSets) {
        this.infoSets = infoSets;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum_main, parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.title.setText(String.valueOf(infoSets.get(position).get(0)));
    }

    @Override
    public int getItemCount() {
        return infoSets.size();
    }

    class VH extends RecyclerView.ViewHolder{
        TextView title;
        public VH(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_forum);
        }
    }
}
