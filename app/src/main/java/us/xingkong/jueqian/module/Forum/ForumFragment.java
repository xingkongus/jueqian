package us.xingkong.jueqian.module.Forum;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import java.util.ArrayList;
import java.util.List;

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
    private boolean isInitRecyclewView = false;
    private int item_count;

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

    }

    @Override
    protected void initView(View rootView) {
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefreshLayout.setProgressViewEndTarget(true, 200);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showToast("刷新");
                swipeRefreshLayout.setRefreshing(true);
                if (isNetworkAvailable(MainActivity.instance)) {
                    mHandler.sendEmptyMessage(REQUEST_REFRESH);
                } else {
                    showToast("网络不可用");
                    swipeRefreshLayout.setRefreshing(false);
                }

            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        swipeRefreshLayout.setRefreshing(true);
        mPresenter.getBmobQuestion(getContext(), questions, mHandler, 1);
//        if (questions != null && isInitRecyclewView == true) {
//            initRecyclerview();
//        }

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
        if(recyclerview==null) recyclerview= (RecyclerView) getActivity().findViewById(R.id.recyclerview);
        recyclerViewAdapter = new ForumRecyclerViewAdapter(questions, mHandler, getContext());
        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(mLayoutManager);
//        recyclerview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {  // 当不滚动时
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    // 判断是否滚动到底部
                    if (lastVisibleItem == (totalItemCount - 1)) {
                        //加载更多功能的代码
                        ArrayList<Question> newQuestions = new ArrayList<>();
                        mPresenter.getMoreBmobQuestion(getContext(), newQuestions, mHandler, item_count);
                    }


//                    if (!recyclerView.canScrollVertically(1)) {
//                        mPresenter.getMoreBmobQuestion(getContext(),questions,mHandler,item_count);
//                    }
//                    if (!recyclerView.canScrollVertically(-1)) {
//                        showToast("刷新");
//                        swipeRefreshLayout.setRefreshing(true);
//                        mHandler.sendEmptyMessage(REQUEST_REFRESH);
//                    }
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
        isInitRecyclewView = true;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REQUEST_REFRESH://刷新
                    if (isInitRecyclewView == false) {
                        initRecyclerview();
                    }
                    questions.clear();
                    isRolling = true;
                    setRecyclewViewBug();
                    mPresenter.getBmobQuestion(getContext(), questions, mHandler, 2);
                    item_count=20;
                    break;
//                case 3:
//                    if (questions != null && isInitRecyclewView == true) {
//                        recyclerViewAdapter.notifyDataSetChanged();
//                        swipeRefreshLayout.setRefreshing(false);
//                        isRolling = false;
//                        setRecyclewViewBug();
//                    }
//                    break;
                case 4:
                    swipeRefreshLayout.setRefreshing(false);
                    isRolling = false;
                    setRecyclewViewBug();
                    break;
                case 5:
                    questions = (ArrayList<Question>) msg.obj;
                    if (questions.size() != 0) {
                        if (isInitRecyclewView == false) {
                            initRecyclerview();
                            item_count = 20;
                        }
                        recyclerViewAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        isRolling = false;
                        setRecyclewViewBug();
                    }
                    break;
                case 6://加载更多返回的数据操作
                    List<Question> newQuestion = (List<Question>) msg.obj;
                    if (newQuestion.size() != 0) {
                        recyclerViewAdapter.addMoreItem(newQuestion);
                        recyclerViewAdapter.changeMoreStatus(ForumRecyclerViewAdapter.LOADING_MORE);
                        item_count += 20;
                    } else {
                        recyclerViewAdapter.changeMoreStatus(ForumRecyclerViewAdapter.NO_MORE);
                    }

                    break;
                case 7://没有更多信息
                    recyclerViewAdapter.changeMoreStatus(ForumRecyclerViewAdapter.NO_MORE);
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

    @Override
    public void onStart() {
        super.onStart();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
