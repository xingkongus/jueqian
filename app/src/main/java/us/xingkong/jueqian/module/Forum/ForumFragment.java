package us.xingkong.jueqian.module.Forum;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.ForumRecyclerViewAdapter;
import us.xingkong.jueqian.base.BaseFragment;
import us.xingkong.jueqian.module.QuestionPage.QuestionActivity;

import static android.R.attr.key;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 15:30
 */

public class ForumFragment extends BaseFragment<ForumContract.Presenter> implements ForumContract.View {


    @BindView(R.id.recyclerview_forum_main)
    RecyclerView recyclerview;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private int mPageCount;
    ArrayList infoSets;
    private Handler mHandler;
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

        infoSets = new ArrayList();
        for (int i = 0; i < 30; ++i) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("position " + i);
            infoSets.add(arrayList);
        }
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        swipeRefreshLayout.setRefreshing(false);
                        break;
                    case 2:
                        Intent intent = new Intent(getContext(), QuestionActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    protected void initView(View rootView) {

        recyclerViewAdapter = new ForumRecyclerViewAdapter(infoSets, mHandler);
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setAdapter(recyclerViewAdapter);
        recyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerview.setItemAnimator(new DefaultItemAnimator());


        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
//        swipeRefreshLayout.setProgressBackgroundColor(Color.WHITE);
        swipeRefreshLayout.setProgressViewEndTarget(true, 200);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                            mHandler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @Override
    protected void initEvent() {


    }

    public void onClick(View view) {
        String strTmp = "点击Button03";
        Intent intent = new Intent(getContext(), QuestionActivity.class);

    }

}
