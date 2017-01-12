package us.xingkong.jueqian.module.RealS.Content;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.BaseAdapter;
import us.xingkong.jueqian.adapter.PartHotAdapter;
import us.xingkong.jueqian.adapter.PartTypeAdapter;
import us.xingkong.jueqian.base.BaseFragment;
import us.xingkong.jueqian.bean.RealSBean.Results;
import us.xingkong.jueqian.data.RealSData.RealSRepository;
import us.xingkong.jueqian.listener.LoadMoreDataAgainListener;
import us.xingkong.jueqian.utils.LogUtils;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 15:30
 */

public class ContentFragment extends BaseFragment<ContentContract.Presenter> implements ContentContract.View {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rv_content)
    RecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private BaseAdapter mBaseAdapter;
    private PartHotAdapter mPartHotAdapter;
    private PartTypeAdapter mPartTypeAdapter;

    private int pageNum = 1;
    private String mRealSTitle;
    private static final String REALS_TITLE = "reals_title";

    public static ContentFragment getInstance(String title) {
        ContentFragment fra = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(REALS_TITLE, title);
        fra.setArguments(bundle);
        return fra;
    }

    @Override
    protected ContentContract.Presenter createPresenter() {
        return new ContentPresenter(new RealSRepository(), this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_reals_content;
    }

    @Override
    protected void prepareData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mRealSTitle = bundle.getString(REALS_TITLE);
        if (mRealSTitle.equals("热门")) mRealSTitle = "all";
    }

    @Override
    protected void initView(View rootView) {
        initRecyclerView();
        mBaseAdapter = mPartHotAdapter != null ? mPartHotAdapter : mPartTypeAdapter;
    }

    private void initRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(mRecyclerView.getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        switch (mRealSTitle) {
            case "all":
                mPartHotAdapter = new PartHotAdapter(getActivity());
                mRecyclerView.setAdapter(mPartHotAdapter);
                break;
            default:
                mPartTypeAdapter = new PartTypeAdapter(getActivity());
                mRecyclerView.setAdapter(mPartTypeAdapter);
                break;
        }
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter.getRealSList(mRealSTitle, 1);
    }

    @Override
    protected void initEvent() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getRealSList(mRealSTitle, 1);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisiableItemPosition = mLinearLayoutManager.findLastVisibleItemPosition();
                if (lastVisiableItemPosition + 1 == mBaseAdapter.getItemCount()) {
                    if (pageNum == 1) {
                        pageNum++;
                    }
                    mPresenter.getRealSList(mRealSTitle, pageNum);
                }

            }
        });
        mBaseAdapter.setOnMoreDataLoadAgainListener(new LoadMoreDataAgainListener() {
            @Override
            public void loadMoreDataAgain(TextView textView, View loadingView) {
                textView.setVisibility(View.GONE);
                loadingView.setVisibility(View.VISIBLE);
                mPresenter.getRealSList(mRealSTitle, pageNum);
            }
        });
    }

    @Override
    public void loadSuccess(int page) {
        if (page == 1) {
            mSwipeRefreshLayout.setRefreshing(false);
            pageNum = 1;
        } else {
            pageNum = ++page;
        }
    }

    @Override
    public void loadFailure(int page) {
        if (page == 1) {
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            mBaseAdapter.setloadFailureView();
        }
    }

    @Override
    public void showRefresh(boolean isRefresh) {
        mSwipeRefreshLayout.setRefreshing(isRefresh);
    }

    @Override
    public void showRealSList(int page, List<Results> realSList) {
        if (page == 1) {
            mBaseAdapter.replaceData(realSList);
        } else {
            mBaseAdapter.addData(realSList);
        }
    }
}
