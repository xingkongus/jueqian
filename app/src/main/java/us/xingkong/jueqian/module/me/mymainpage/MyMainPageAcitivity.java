package us.xingkong.jueqian.module.me.mymainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.module.me.mymainpage.editinfo.EditInfoActivity;

/**
 * Created by PERFECTLIN on 2017/4/19 0019.
 */

public class MyMainPageAcitivity extends BaseActivity<MyMainPageContract.Presenter> implements MyMainPageContract.View {
    @BindView(R.id.mainpage_bt_edit)
    Button bt_edit;

    @Override
    protected MyMainPageContract.Presenter createPresenter() {
        return new MyMainPagePresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_mymainpage;
    }

    @Override
    protected void prepareData() {

    }

    @Override
    protected void initView() {
        setToolbar();
        setEditButton();

    }

    private void setEditButton() {
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JueQianAPP.getAppContext(), EditInfoActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setToolbar() {
        ActionBar acb = getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("个人主页");
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
