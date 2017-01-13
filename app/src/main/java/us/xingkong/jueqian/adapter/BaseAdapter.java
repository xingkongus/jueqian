package us.xingkong.jueqian.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/12 00:46
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mData = new ArrayList<>();

    public void addData(T t) {
        this.mData.add(t);
    }

    public void addData(int position, T t) {
        this.mData.add(position, t);
    }

    public void addData(List<T> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        this.mData.remove(position);
        notifyDataSetChanged();
    }

    public void replaceData(List<T> data) {
        this.mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
