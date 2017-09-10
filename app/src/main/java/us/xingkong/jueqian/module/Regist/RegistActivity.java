package us.xingkong.jueqian.module.Regist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import cn.bmob.v3.listener.SaveListener;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.module.Login.LoginActivity;
import us.xingkong.jueqian.module.main.MainActivity;
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
    TextView loginButton;
    String username;
    String password;
    Context context;
    String passwordComfirm;
    @BindView(R.id.passwordAgainWrapper)
    TextInputLayout passwordAgainWrapper;
    @BindView(R.id.regist_passwordAgain)
    EditText passwordAgain;
    @BindView(R.id.close)
    ImageView close;


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
        context = this;
        usernameWrapper.setHint("Username");
        passwordWrapper.setHint("Password");
        passwordAgainWrapper.setHint("confirm");
        mPresenter.setEditText(regist_username);
        mPresenter.setEditText(regist_password);
        mPresenter.setEditText(passwordAgain);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(JueQianAPP.getAppContext(),MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });

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
                username = regist_username.getText().toString();
                password = regist_password.getText().toString();
                passwordComfirm = passwordAgain.getText().toString();
                if (username.equals("")) {
                    usernameWrapper.setError("用户名不能为空");
                } else if (!CheckUtils.checkPassword(password)) {
                    passwordWrapper.setError("密码由6个小写字符和数字组成");
                } else if (!passwordComfirm.equals(password)) {
                    passwordWrapper.setError("两个密码不相等");
                } else {
                    usernameWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    mPresenter.regist(context, username, password,handler);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                finish();
            }
        });

    }
        Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 0:
                String username1=msg.getData().getString("username");
                String password1=msg.getData().getString("password");
                _User bu = new _User();
                bu.setUsername(username1);
                bu.setPassword(password1);
                bu.login(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        MainActivity.instance.finish();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                       showToast("网络连接超时");
                    }
                });
                finish();
                break;
        }
    }
};

}
