package us.xingkong.jueqian.module.ChangePassWord;

import android.content.Context;
import android.widget.Toast;

import cn.bmob.v3.listener.UpdateListener;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;

/**
 * Created by lenovo on 2017/1/12.
 */

public class ChangePWPresenter extends BasePresenterImpl implements ChangePWContract.Presenter {
  private final ChangePWContract.View mView;

    public ChangePWPresenter(ChangePWContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void checkpd(final Context context, String repd, String nowpd) {
        _User.updateCurrentUserPassword(context, repd, nowpd, new UpdateListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "修改密码成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(context, "原密码错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
