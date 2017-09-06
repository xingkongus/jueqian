package us.xingkong.jueqian.module.Login;

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
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.module.Regist.RegistActivity;
import us.xingkong.jueqian.utils.CheckUtils;


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
    TextView registButton;
    String password;
    String username;
    Context con;
    @BindView(R.id.close)
    ImageView close;

    @Override
    protected LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int bindLayout() {

        return R.layout.activity_login;
    }

    @Override
    protected void prepareData() {

    }

    @Override
    protected void initView() {
        con = this;
        username_layout.setHint("Username");
        password_layout.setHint("Password");
        mPresenter.setEditText(username_edit);
        mPresenter.setEditText(password_edit);
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
    }

    @Override
    protected void initEvent() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_edit.getText().toString();
                password = password_edit.getText().toString();
//                if (!CheckUtils.checkPassword(username)) {
//                    username_layout.setError("用户名不存在");
//                } else
                if (!CheckUtils.checkPassword(password)) {
                    password_layout.setError("密码错误");
                } else {
                    username_layout.setErrorEnabled(false);
                    password_layout.setErrorEnabled(false);
                    mPresenter.login(con, username, password, handler);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        registButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(con, RegistActivity.class);
                con.startActivity(intent);
                finish();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    showToast("登陆成功");
                    finish();
                    break;
            }
        }
    };

}
