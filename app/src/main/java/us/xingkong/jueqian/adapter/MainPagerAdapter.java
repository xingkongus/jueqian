package us.xingkong.jueqian.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 12:36
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments;
    private final String[] mTabTitles;

    public MainPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments, String[] tabTitles) {
        super(fragmentManager);
        this.mFragments = fragments;
        this.mTabTitles = tabTitles;
    }

    public int getCount() {
        return this.mFragments.size();
    }

    public Fragment getItem(int position) {
        return this.mFragments.get(position);
    }

    public CharSequence getPageTitle(int position) {
        return this.mTabTitles[position];
    }
}
