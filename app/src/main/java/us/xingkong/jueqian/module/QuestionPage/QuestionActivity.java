package us.xingkong.jueqian.module.QuestionPage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import us.xingkong.jueqian.utils.AppUtils;
import us.xingkong.jueqian.widget.ScrollViewPager;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 10:50
 */

public class QuestionActivity extends BaseActivity<QuestionContract.Presenter> implements QuestionContract.View {





    @Override
    protected QuestionContract.Presenter createPresenter() {
        return new QuestionPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_question;
    }

    @Override
    protected void prepareData() {

    }

    @Override
    protected void initView() {
        setToolbarTitle(AppUtils.getAppName(this));


    }

    private void addFragmentList(List<Fragment> fragments) {

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
