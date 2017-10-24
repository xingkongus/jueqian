package us.xingkong.jueqian.module.Regist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import cn.bmob.v3.listener.SaveListener;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;


public class RegistPresenter extends BasePresenterImpl implements RegistContract.Presenter {

    private final RegistContract.View mView;

    public RegistPresenter(RegistContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

    public void regist(final Context context, final String username, final String password, final Handler handler) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                _User bu = new _User();
                bu.setUsername(username);
                bu.setPassword(password);
                bu.setState(1);
                bu.signUp(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        mView.showToast("注册完成");
                        Message msg=new Message();
                        msg.what=0;
                        Bundle bundle=new Bundle();
                        bundle.putString("username",username);
                        bundle.putString("password",password);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        mView.showToast("用户名已存在");
                    }
                });
            }
        });
        thread.start();

    }

    @Override
    public void setEditText(EditText editText) {
        editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
        editText.setLongClickable(false);
    }
}
