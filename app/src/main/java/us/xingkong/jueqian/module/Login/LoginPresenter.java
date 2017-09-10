package us.xingkong.jueqian.module.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import cn.bmob.v3.listener.SaveListener;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.module.main.MainActivity;


public class LoginPresenter extends BasePresenterImpl implements LoginContract.Presenter {

    private final LoginContract.View mView;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }


    @Override
    public void login(final Context context, final String username, final String password, final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                _User bu = new _User();
                bu.setUsername(username);
                bu.setPassword(password);
                bu.login(context, new SaveListener() {
                    @Override
                    public void onSuccess() {

//                        if (BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getUsername() == null) {
//                            MainActivity.instance.finish();
//                            Intent intent = new Intent(context, MainActivity.class);
//                            context.startActivity(intent);
//                            handler.sendEmptyMessage(0);
//                        }else {
//                            MainActivity.instance.finish();
//                            Intent intent = new Intent(context, EditInfoActivity.class);
//                            context.startActivity(intent);
//                        }
                        MainActivity.instance.finish();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                        handler.sendEmptyMessage(0);

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        mView.showToast("用户名不存在或密码错误");
                    }
                });
            }
        }).start();
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
