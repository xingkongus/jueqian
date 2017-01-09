package us.xingkong.jueqian.module.Forum;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.ForumRecyclerViewAdapter;
import us.xingkong.jueqian.base.BaseFragment;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 15:30
 */

public class ForumFragment extends BaseFragment<ForumContract.Presenter> implements ForumContract.View {


    @BindView(R.id.recyclerview_forum_main)
    RecyclerView recyclerview;

    private int mPageCount;
    private RecyclerView.LayoutManager mLayoutManager;
    private ForumRecyclerViewAdapter recyclerViewAdapter;
    private static final String PAGE_COUNT = "page_count";

    public static ForumFragment getInstance(int page_count) {
        ForumFragment fra = new ForumFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_COUNT, page_count);
        fra.setArguments(bundle);
        return fra;
    }

    @Override
    protected ForumContract.Presenter createPresenter() {
        return new ForumPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_forum;
    }

    @Override
    protected void prepareData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mPageCount = bundle.getInt(PAGE_COUNT);

    }

    @Override
    protected void initView(View rootView) {

        recyclerViewAdapter = new ForumRecyclerViewAdapter();
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setAdapter(recyclerViewAdapter);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @Override
    protected void initEvent() {

    }


}
