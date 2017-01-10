package us.xingkong.jueqian.module.RealS;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindArray;
import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.MainPagerAdapter;
import us.xingkong.jueqian.base.BaseFragment;
import us.xingkong.jueqian.module.RealS.Content.ContentFragment;
import us.xingkong.jueqian.utils.LogUtils;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/10 14:38
 */

public class RealSFragment extends BaseFragment<RealSContract.Presenter> implements RealSContract.View {

    @BindArray(R.array.tab_reals)
    String[] mRealSTitles;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private ArrayList<Fragment> mFragments;

    @Override
    protected RealSContract.Presenter createPresenter() {
        return new RealSPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_reals;
    }

    @Override
    protected void prepareData(Bundle savedInstanceState) {
        mFragments = new ArrayList<>();
        for (String title : mRealSTitles) {
            mFragments.add(ContentFragment.getInstance(title));
            LogUtils.$Log(title);
        }
    }

    @Override
    protected void initView(View rootView) {
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(new MainPagerAdapter(getChildFragmentManager(), mFragments, mRealSTitles));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @Override
    protected void initEvent() {

    }

}
