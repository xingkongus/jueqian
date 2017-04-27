package us.xingkong.jueqian.module.me.mysettings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Answer;
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
                Glide.get(JueQianAPP.getAppContext()).clearMemory();
//                Glide.get(JueQianAPP.getAppContext()).clearDiskCache();
                BmobQuery.clearAllCachedResults(JueQianAPP.getAppContext());
                showToast("清理缓存完成");
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
