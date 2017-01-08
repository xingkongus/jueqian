package us.xingkong.jueqian.module.Home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseFragment;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 15:30
 */

public class HomePageFragment extends BaseFragment<HomePageContract.Presenter> implements HomePageContract.View {

    @BindView(R.id.tv_page_count)
    TextView mTvPageCount;

    private int mPageCount;
    private static final String PAGE_COUNT = "page_count";

    public static HomePageFragment getInstance(int page_count) {
        HomePageFragment fra = new HomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_COUNT, page_count);
        fra.setArguments(bundle);
        return fra;
    }

    @Override
    protected HomePageContract.Presenter createPresenter() {
        return new HomePagePresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void prepareData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mPageCount = bundle.getInt(PAGE_COUNT);
    }

    @Override
    protected void initView(View rootView) {
        mTvPageCount.setText("这是第" + mPageCount + "页");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @Override
    protected void initEvent() {

    }

}
