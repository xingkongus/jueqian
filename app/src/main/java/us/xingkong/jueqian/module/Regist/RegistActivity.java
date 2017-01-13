package us.xingkong.jueqian.module.Regist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.utils.CheckUtils;



public class RegistActivity extends BaseActivity<RegistContract.Presenter> implements RegistContract.View {

    @BindView(R.id.usernameWrapper)
    TextInputLayout usernameWrapper;
    @BindView(R.id.passwordWrapper)
    TextInputLayout passwordWrapper;
    @BindView(R.id.regist_username)
    EditText regist_username;
    @BindView(R.id.regist_password)
    EditText regist_password;
    @BindView(R.id.registButton)
    Button registButton;
    @BindView(R.id.loginButton)
    Button loginButton;
    String username;
    String password;
    Context context;

    @Override
    protected RegistContract.Presenter createPresenter() {
        return new RegistPresenter(this);
    }

    @Override
    protected int bindLayout() {
        //TODO:添加视图，记得添加androidmanifest
        return R.layout.activity_regist;
    }

    @Override
    protected void prepareData() {
        //TODO:准备数据 比如：从数据库加载数据，或者网络请求数据等等

    }

    @Override
    protected void initView() {
        //TODO:初始化视图 比如：recycleview的准备，添加adapter等等
        context=this;

        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //TODO:初始化数据 比如：将数据加入到view中

    }

    @Override
    protected void initEvent() {
        //TODO:初始化事件监听 比如：增加监听器，下拉刷新，加载更多等等
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=regist_username.getText().toString();
                password=regist_password.getText().toString();
                if (username.equals("")) {
                    usernameWrapper.setError("Not a valid username!");
                }
                  else if (!CheckUtils.checkPassword(password)) {
                        passwordWrapper.setError("Not a valid password!");
                }
                else {
                    usernameWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    mPresenter.regist(context,username,password);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,RegistActivity.class);
                context.startActivity(intent);
                finish();
            }
        });

    }

}
