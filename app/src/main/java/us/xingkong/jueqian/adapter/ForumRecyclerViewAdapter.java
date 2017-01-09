package us.xingkong.jueqian.adapter;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import us.xingkong.jueqian.R;

/**
 * Created by Garfield on 1/9/17.
 */

public class ForumRecyclerViewAdapter extends RecyclerView.Adapter<ForumRecyclerViewAdapter.VH> implements View.OnClickListener {
    ArrayList<ArrayList> infoSets;
    Handler mHandler;


    public ForumRecyclerViewAdapter(ArrayList<ArrayList> infoSets, Handler handler) {
        this.infoSets = infoSets;
        mHandler = handler;
//        Message m = new Message();
    }


    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forum_main, parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.title.setText(String.valueOf(infoSets.get(position).get(0)));
        holder.linearLayout.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return infoSets.size();
    }

    @Override
    public void onClick(View view) {
        mHandler.sendEmptyMessage(2);
    }

    class VH extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView title;

        public VH(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title_forum);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.item_forum);
        }
    }


}
