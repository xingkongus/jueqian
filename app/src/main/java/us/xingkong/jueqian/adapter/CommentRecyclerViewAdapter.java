package us.xingkong.jueqian.adapter;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import us.xingkong.jueqian.R;


/**
 * Created by Garfield on 1/9/17.
 */

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.VH> {
    private Handler mHandler;

    public CommentRecyclerViewAdapter(Handler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commentpage, parent, false));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.sendEmptyMessage(1);
            }
        });
    }

    class VH extends RecyclerView.ViewHolder {
        TextView content;
        ImageButton imageButton;

        public VH(View itemView) {
            super(itemView);
//            imageButton = (ImageButton) itemView.findViewById(R.id.more_commentpage);
        }
    }
}
