package us.xingkong.jueqian.module.Regist;

import android.content.Context;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import us.xingkong.jueqian.base.BasePresenterImpl;


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
               BmobUser bu=new BmobUser();
               bu.setUsername(username);
               bu.setPassword(password);
               bu.signUp(context, new SaveListener() {
                   @Override
                   public void onSuccess() {
                       System.out.println("regist successful");
                   }

                   @Override
                   public void onFailure(int i, String s) {
                       System.out.println("regist fail");
                   }
               });
           }
       });
        thread.start();

    }
}
