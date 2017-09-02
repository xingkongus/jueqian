package us.xingkong.jueqian.module.main;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.MainPagerAdapter;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.module.Forum.ForumFragment;
import us.xingkong.jueqian.module.RealS.RealSFragment;
import us.xingkong.jueqian.module.me.MeFragment;
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
    @BindView(R.id.tab_ganhuo)
    RadioButton mTabGanH;
    @BindView(R.id.rg_tab)
    RadioGroup mRadioGroup;
    public static MainActivity instance = null;

    private MeFragment mMeFragment;
    private ForumFragment mForumFragment;
    private RealSFragment mRealSFragment;
    Context con;
    public Handler handler123 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mRadioGroup.setVisibility(View.GONE);
                    break;
                case 1:
                    mRadioGroup.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected int bindLayout() {
        con = this;
        return R.layout.activity_main;
    }

    @Override
    protected void prepareData() {
    }

    @Override
    protected void initView() {
        registPgyCrash(); //蒲公英注册Crash
        autoUpadate(); //蒲公英自动更新
        instance = this;
        mViewPager.setPagingEnabled(false);
        List<Fragment> fragments = new ArrayList<>();
        addFragmentList(fragments);

        mViewPager.setOffscreenPageLimit(3);
        MainPagerAdapter pagerAdapter = new MainPagerAdapter(
                getSupportFragmentManager(),
                fragments,
                this.mTitles);
        mViewPager.setAdapter(pagerAdapter);

    }

    private void autoUpadate() {
        PgyUpdateManager.setIsForced(false); //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
//        PgyUpdateManager.register(this, "pgy_provider");

        PgyUpdateManager.register(MainActivity.this, "pgy_provider",
                new UpdateManagerListener() {
                    String releaseNote;
                    @Override
                    public void onUpdateAvailable(final String result) {

                        try {
                            JSONObject upadate_result=new JSONObject(result);
                            JSONObject data=upadate_result.getJSONObject("data");
                            releaseNote=data.getString("releaseNote");
                            if (releaseNote==null){
                                releaseNote="修复了一些bug";
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // 将新版本信息封装到AppBean中
                        final AppBean appBean = getAppBeanFromString(result);
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("发现新的版本")
                                .setMessage(releaseNote)
                                .setNegativeButton(
                                        "确定",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(
                                                    DialogInterface dialog,
                                                    int which) {
                                                startDownloadTask(
                                                        MainActivity.this,
                                                        appBean.getDownloadURL());
                                            }
                                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
                    }

                    @Override
                    public void onNoUpdateAvailable() {
                    }
                });
    }

    private void registPgyCrash() {
        PgyCrashManager.register(this);
    }

    private void addFragmentList(List<Fragment> fragments) {
        mForumFragment = new ForumFragment().getInstance(0);
        fragments.add(mForumFragment);
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
                    case R.id.tab_ganhuo:
                        mViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.tab_me:
                        mViewPager.setCurrentItem(2, false);
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
