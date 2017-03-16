package us.xingkong.jueqian.module.ChangePassWord;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.utils.CheckUtils;

/**
 * Created by lenovo on 2017/1/12.
 */

public class ChangePWActivity extends BaseActivity<ChangePWContract.Presenter> implements  ChangePWContract.View{

    @BindView(R.id.repassword)
    EditText repassword;
    @BindView(R.id.nowpassword)
    EditText nowpassword;
    @BindView(R.id.repassword_layout)
    TextInputLayout repassword_layout;
    @BindView(R.id.nowpassword_layout)
    TextInputLayout nowpassword_layout;
    Context con;
    @BindView(R.id.commit)
    Button commit;
    String repd;
    String nowpd;


    @Override
    protected ChangePWContract.Presenter createPresenter() {
        return new ChangePWPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_changepw;
    }

    @Override
    protected void prepareData() {

    }

    @Override
    protected void initView() {
        con=this;
        repassword_layout.setHint("原密码");
        nowpassword_layout.setHint("新密码");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repd=repassword.getText().toString();
                nowpd=nowpassword.getText().toString();
                if(CheckUtils.checkPassword(repd)&&CheckUtils.checkPassword(nowpd)&&!repd.equals("")&&!nowpd.equals("")){
                    repassword_layout.setErrorEnabled(false);
                    nowpassword_layout.setErrorEnabled(false);
                    mPresenter.checkpd(con,repd,nowpd);
                }else{
                    repassword_layout.setError("Not a valid password!");
                    nowpassword_layout.setError("Not a valid password!");
                }
            }
        });
    }
}
