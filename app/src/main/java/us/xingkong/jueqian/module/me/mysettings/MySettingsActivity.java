package us.xingkong.jueqian.module.me.mysettings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import butterknife.BindView;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
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
                Question u = new Question();

                Answer answer = new Answer();
                answer.setState(1);
                answer.setMcontent("这是回答");
                answer.setUps(2);
                answer.save(getApplicationContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        showToast("YES1!");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        showToast("NO1!" + s);
                    }
                });

                BmobRelation bmobRelation = new BmobRelation();
                bmobRelation.add(answer);
                u.setAnswers(bmobRelation);
                u.save(getApplicationContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        showToast("YES2!");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        showToast("NO2!" + s);
                    }
                });

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
