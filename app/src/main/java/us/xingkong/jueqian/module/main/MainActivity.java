package us.xingkong.jueqian.module.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.MainPagerAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.module.Home.HomePageFragment;
import us.xingkong.jueqian.utils.AppUtils;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 10:50
 */

public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View {

    @BindArray(R.array.tab_title)
    String[] mTitles;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindView(R.id.tablayout)
    TabLayout tablayout;
//    @BindView(R.id.tab_homepager)
//    RadioButton mTabHomePager;
//    @BindView(R.id.tab_type)
//    RadioButton mTabType;
//    @BindView(R.id.tab_forum)
//    RadioButton mTabForm;
//    @BindView(R.id.rg_tab)
//    RadioGroup mRgTab;

    private HomePageFragment mHomePageFragment;

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void prepareData() {

    }

    @Override
    protected void initView() {
        setToolbarTitle(AppUtils.getAppName(this));

        List<Fragment> fragments = new ArrayList<>();
        addFragmentList(fragments);

        mViewPager.setOffscreenPageLimit(4);
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(
                getSupportFragmentManager(),
                fragments,
                this.mTitles);
        mViewPager.setAdapter(pagerAdapter);

        tablayout.setupWithViewPager(mViewPager);
    }

    private void addFragmentList(List<Fragment> fragments) {
        mHomePageFragment = new HomePageFragment().getInstance(0);
        fragments.add(mHomePageFragment);
        mHomePageFragment = new HomePageFragment().getInstance(1);
        fragments.add(mHomePageFragment);
        mHomePageFragment = new HomePageFragment().getInstance(2);
        fragments.add(mHomePageFragment);
        mHomePageFragment = new HomePageFragment().getInstance(3);
        fragments.add(mHomePageFragment);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void onBackPressed() {
        JueQianAPP.exitApp();
    }

}
