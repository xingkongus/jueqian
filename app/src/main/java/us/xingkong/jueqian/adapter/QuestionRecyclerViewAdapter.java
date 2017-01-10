package us.xingkong.jueqian.adapter;

import android.os.Handler;
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

public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.VH> {
    private ArrayList questionSets;
    private ArrayList<ArrayList> answerSetsArr;
    private int HEADER = 0x1;
    private int CONTENT = 0x2;
    private int FOOTER = 0x3;
    private Handler mHandler;


    public QuestionRecyclerViewAdapter(ArrayList questionSets, ArrayList<ArrayList> answerSetsArr, Handler mHandler) {
        this.questionSets = questionSets;
        this.answerSetsArr = answerSetsArr;
        this.mHandler = mHandler;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionpage_header, parent, false));
        } else if (viewType == CONTENT) {
            return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionpage, parent, false));
        } else {
            return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else if (position == answerSetsArr.size() + 1) {
            return FOOTER;
        } else {
            return CONTENT;
        }

    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (position != 0) {
            holder.content.setText(String.valueOf(answerSetsArr.get(position - 1).get(0)+": {点我点我}"));
            holder.content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mHandler.sendEmptyMessage(3);
                }
            });
        }
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.sendEmptyMessage(2);
            }
        });

    }

    @Override
    public int getItemCount() {
        return answerSetsArr.size() + 1;
    }

    class VH extends RecyclerView.ViewHolder {
        TextView content;
        TextView comment;

        public VH(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content_questionpage);
            comment = (TextView) itemView.findViewById(R.id.comment_questionpage);
        }
    }
}
