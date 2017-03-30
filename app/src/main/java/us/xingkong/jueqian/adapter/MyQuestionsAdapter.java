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
 * Created by PERFECTLIN on 2017/1/14 0014.
 */

public class MyQuestionsAdapter extends RecyclerView.Adapter<MyQuestionsAdapter.MyViewHolder> {
    private Handler mHander;
    private ArrayList<String> mArrayList;

    public MyQuestionsAdapter(Handler mHander, ArrayList<String> mArrayList) {
        this.mHander = mHander;
        this.mArrayList = mArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyQuestionsAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myquestions, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_title.setText(mArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title= (TextView) itemView.findViewById(R.id.item_myquestions_tv_questiontitle);
        }
    }
}
