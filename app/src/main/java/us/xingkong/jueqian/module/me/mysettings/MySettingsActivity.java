package us.xingkong.jueqian.module.me.mysettings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import cn.bmob.v3.listener.SaveListener;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.module.me.mysettings.aboutme.AboutMeActivity;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MySettingsActivity extends BaseActivity<MySettingsContract.Presenter> implements MySettingsContract.View {

    @BindView(R.id.mysettings_ry_aboutme)
    RelativeLayout mRelativeLayout_aboutme;
    @BindView(R.id.mysettings_ry_clean)
    RelativeLayout mRelativeLayout_clean;

    @Override
    protected MySettingsContract.Presenter createPresenter() {
        return null;
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_mysettings;
    }

    @Override
    protected void prepareData() {

    }

    @Override
    protected void initView() {
        setToolbar();
        setClean();
        setAboutMe();
    }

    private void setClean() {
        mRelativeLayout_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Question u = new Question();
                        u.setState(1);
                        u.setMtitle("11111111111111");
                        u.setMcontent("111111222333333333333");
                        u.save(getApplicationContext(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                showToast("YES!");
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                showToast("NO!"+s);
                            }
                        });
                    }
                });
                thread.start();
            }
        });
    }

    private void setAboutMe() {
        mRelativeLayout_aboutme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AboutMeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setToolbar() {
        ActionBar acb = getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("个人设置");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
