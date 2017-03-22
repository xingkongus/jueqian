package us.xingkong.jueqian.module.Regist;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import cn.bmob.v3.listener.SaveListener;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.LoginRegistBean.Userinfo;
import us.xingkong.jueqian.module.Login.LoginActivity;


public class RegistPresenter extends BasePresenterImpl implements RegistContract.Presenter {

    private final RegistContract.View mView;
    public RegistPresenter(RegistContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }
    public void regist(final Context context, final String username, final String password){
       Thread thread=new Thread(new Runnable() {
           @Override
           public void run() {
               Userinfo bu=new Userinfo();
               bu.setUsername(username);
               bu.setPassword(password);
               bu.signUp(context, new SaveListener() {
                   @Override
                   public void onSuccess() {
                       System.out.println("activity_regist successful");
                       Intent intent=new Intent(context, LoginActivity.class);
                       context.startActivity(intent);
                       onFinish();
                   }

                   @Override
                   public void onFailure(int i, String s) {
                       System.out.println("activity_regist fail");
                       Toast.makeText(context, "用户名已存在", Toast.LENGTH_SHORT).show();
                   }
               });
           }
       });
        thread.start();

    }
}
