package us.xingkong.jueqian.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
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
import us.xingkong.jueqian.module.Forum.ForumFragment;
import us.xingkong.jueqian.module.Home.HomePageFragment;
import us.xingkong.jueqian.module.me.MeFragment;
import us.xingkong.jueqian.module.RealS.RealSFragment;
import us.xingkong.jueqian.utils.AppUtils;
import us.xingkong.jueqian.widget.ScrollViewPager;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 10:50
 */

public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View {

    @BindArray(R.array.tab_title)
    String[] mTitles;
    @BindView(R.id.viewpager)
    ScrollViewPager mViewPager;

    @BindView(R.id.tab_homepager)
    RadioButton mTabHomePager;
    @BindView(R.id.tab_discovery)
    RadioButton mTabDiscovery;
    @BindView(R.id.tab_ganhuo)
    RadioButton mTabGanH;
    @BindView(R.id.rg_tab)
    RadioGroup mRadioGroup;

    private HomePageFragment mHomePageFragment;
    private MeFragment mMeFragment;
    private ForumFragment mForumFragment;
    private RealSFragment mRealSFragment;

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

        mViewPager.setPagingEnabled(false);
        List<Fragment> fragments = new ArrayList<>();
        addFragmentList(fragments);

        mViewPager.setOffscreenPageLimit(4);
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(
                getSupportFragmentManager(),
                fragments,
                this.mTitles);
        mViewPager.setAdapter(pagerAdapter);

    }

    private void addFragmentList(List<Fragment> fragments) {
        mForumFragment = new ForumFragment().getInstance(0);
        fragments.add(mForumFragment);
        mHomePageFragment = new HomePageFragment().getInstance(1);
        fragments.add(mHomePageFragment);
        mRealSFragment = new RealSFragment();
        fragments.add(mRealSFragment);
        mMeFragment = new MeFragment().getInstance(3);
        fragments.add(mMeFragment);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.tab_homepager:
                        mViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.tab_discovery:
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.tab_ganhuo:
                        mViewPager.setCurrentItem(2, false);
                        break;
                    case R.id.tab_me:
                        mViewPager.setCurrentItem(3, false);
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        JueQianAPP.exitApp();
    }



}
