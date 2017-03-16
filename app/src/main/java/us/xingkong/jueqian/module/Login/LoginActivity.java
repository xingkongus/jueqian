package us.xingkong.jueqian.module.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import cn.bmob.v3.Bmob;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.module.Regist.RegistActivity;
import us.xingkong.jueqian.utils.CheckUtils;
import us.xingkong.jueqian.utils.Key;


public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.username_layout)
    TextInputLayout username_layout;
    @BindView(R.id.password_layout)
    TextInputLayout password_layout;
    @BindView(R.id.login_username)
    EditText username_edit;
    @BindView(R.id.login_password)
    EditText password_edit;
    @BindView(R.id.loginButton)
    Button loginButton;
    @BindView(R.id.registButton)
    Button registButton;
    String password;
    String username;
    Context con;

    @Override
    protected LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int bindLayout() {
        //TODO:添加视图，记得添加androidmanifest
        return R.layout.activity_login;
    }

    @Override
    protected void prepareData() {
        //TODO:准备数据 比如：从数据库加载数据，或者网络请求数据等等

    }

    @Override
    protected void initView() {
        //TODO:初始化视图 比如：recycleview的准备，添加adapter等等
        con=this;
        Bmob.initialize(con, Key.Application_ID);
        username_layout.setHint("Username");
        password_layout.setHint("Password");

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //TODO:初始化数据 比如：将数据加入到view中
    }

    @Override
    protected void initEvent() {
        //TODO:初始化事件监听 比如：增加监听器，下拉刷新，加载更多等等
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=username_edit.getText().toString();
                password=password_edit.getText().toString();
                if (!CheckUtils.checkPassword(username)) {
                    username_layout.setError("Not a valid username!");
                }
                else if (!CheckUtils.checkPassword(password)) {
                    password_layout.setError("Not a valid password!");
                }
                else {
                    username_layout.setErrorEnabled(false);
                    password_layout.setErrorEnabled(false);
                    mPresenter.login(con,username,password);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(con, RegistActivity.class);
                con.startActivity(intent);
                finish();
            }
        });
    }
}
