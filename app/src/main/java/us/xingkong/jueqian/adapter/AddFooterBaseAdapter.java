package us.xingkong.jueqian.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.listener.LoadMoreDataAgainListener;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/13 13:14
 */

public abstract class AddFooterBaseAdapter<T> extends BaseAdapter<T> {

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;

    private AddFooterBaseAdapter.FooterViewHolder mFooterViewHolder;
    private LoadMoreDataAgainListener mLoadMoreDataAgainListener;

    public void setOnMoreDataLoadAgainListener(LoadMoreDataAgainListener onMoreDataLoadAgainListener) {
        mLoadMoreDataAgainListener = onMoreDataLoadAgainListener;
    }

    public void setLoadFailureView() {
        mFooterViewHolder.mAvLoadingIndicatorView.setVisibility(View.GONE);
        mFooterViewHolder.mTvLoadDataAgain.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AddFooterBaseAdapter.FooterViewHolder) {
            mFooterViewHolder = ((FooterViewHolder) holder);
            mFooterViewHolder.mAvLoadingIndicatorView.setVisibility(View.VISIBLE);
            mFooterViewHolder.mTvLoadDataAgain.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) return TYPE_FOOTER;
        else return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.loadingIndicatorView)
        AVLoadingIndicatorView mAvLoadingIndicatorView;
        @BindView(R.id.tv_load_data_again)
        TextView mTvLoadDataAgain;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick({R.id.loadingIndicatorView, R.id.tv_load_data_again})
        public void onClick(View view) {
            mLoadMoreDataAgainListener.loadMoreDataAgain(mTvLoadDataAgain, mAvLoadingIndicatorView);
        }
    }
}
