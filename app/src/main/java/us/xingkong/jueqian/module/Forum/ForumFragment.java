package us.xingkong.jueqian.module.Forum;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.ForumRecyclerViewAdapter;
import us.xingkong.jueqian.base.BaseFragment;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.data.RepositData.ForumRepository;
import us.xingkong.jueqian.module.Forum.NewPage.NewActivity;
import us.xingkong.jueqian.module.Login.LoginActivity;
import us.xingkong.jueqian.module.main.MainActivity;

import static us.xingkong.jueqian.base.Constants.REQUEST_REFRESH;


public class ForumFragment extends BaseFragment<ForumContract.Presenter> implements ForumContract.View {

    //
    @BindView(R.id.recyclerview_forum_main)
    RecyclerView recyclerview;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fab_forum_main)
    FloatingActionButton fabForumMain;
    private RecyclerView.LayoutManager mLayoutManager;
    private ForumRecyclerViewAdapter recyclerViewAdapter;
    private static final String PAGE_COUNT = "page_count";
    ArrayList<Question> questions = new ArrayList<>();
    Boolean isRolling = false;

    public static ForumFragment getInstance(int page_count) {
        ForumFragment fra = new ForumFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_COUNT, page_count);
        fra.setArguments(bundle);
        return fra;
    }

    @Override
    protected ForumContract.Presenter createPresenter() {
        return new ForumPresenter(this, new ForumRepository(getContext()));
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_forum;
    }

    @Override
    protected void prepareData(Bundle savedInstanceState) {
        questions = (ArrayList<Question>) mPresenter.getBmobQuestion(getContext(), questions, mHandler);

    }

    @Override
    protected void initView(View rootView) {
        initSwipeRefreshLayout();
        swipeRefreshLayout.setRefreshing(true);
        initRecyclerview();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefreshLayout.setProgressViewEndTarget(true, 200);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick(R.id.fab_forum_main)
    public void onClick() {
        _User user = BmobUser.getCurrentUser(getContext(), _User.class);
        if (user == null) {
            showToast("请先登录");
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), NewActivity.class);
            startActivity(intent);
        }
    }

    private void initRecyclerview() {
        recyclerViewAdapter = new ForumRecyclerViewAdapter(questions, mHandler, getContext());
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(mLayoutManager);
//        recyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {

                    }
                    if (!recyclerView.canScrollVertically(-1)) {
                        Toast.makeText(getContext(), "刷新", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(true);
                        mHandler.sendEmptyMessage(REQUEST_REFRESH);

                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Handler handler = ((MainActivity) getActivity()).handler123;
                if (dy > 0) {
                    handler.sendEmptyMessage(0);
                } else if (dy < 0) {
                    handler.sendEmptyMessage(1);
                }
            }
        });
        recyclerview.setAdapter(recyclerViewAdapter);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REQUEST_REFRESH:
                    questions.clear();
                    isRolling = true;
                    setRecyclewViewBug();
                    mPresenter.getBmobQuestion(getContext(), questions, mHandler);
                    break;
                case 3:
                    recyclerViewAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    isRolling = false;
                    setRecyclewViewBug();
                    break;
            }
        }
    };

    @Override
    public void setRecyclewViewBug() {
        recyclerview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isRolling) {
                    return true;
                } else {
                    return false;
                }
            }
        });
    }
}
