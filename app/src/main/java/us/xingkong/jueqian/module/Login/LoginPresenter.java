package us.xingkong.jueqian.module.Login;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.module.main.MainActivity;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 11:28
 */

public class LoginPresenter extends BasePresenterImpl implements LoginContract.Presenter {

    private final LoginContract.View mView;
    public LoginPresenter(LoginContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }


    @Override
    public void login(final Context context, final String username, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobUser bu=new BmobUser();
                bu.setUsername(username);
                bu.setPassword(password);
                bu.login(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        System.out.println("login successful");
                        Intent intent=new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        onFinish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        System.out.println("login fail");
                        Toast.makeText(context, "用户名不存在或密码错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }
}
