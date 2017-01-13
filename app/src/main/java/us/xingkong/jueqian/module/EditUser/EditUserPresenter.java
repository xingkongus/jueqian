package us.xingkong.jueqian.module.EditUser;

import android.content.Context;

import cn.bmob.v3.listener.UpdateListener;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.LoginRegistBean.Userinfo;


public class EditUserPresenter extends BasePresenterImpl implements EditUserContract.Presenter {

    private final EditUserContract.View mView;

    public EditUserPresenter(EditUserContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void saveUser(Context context, String url, String nick, String phone, String email, String sex) {
        Userinfo newInfo = new Userinfo();
        newInfo.setUrl(url);
        newInfo.setNick(nick);
        newInfo.setPhone(phone);
        newInfo.setEmail(email);
        newInfo.setSex(sex);
        Userinfo oldInfo = (Userinfo) Userinfo.getCurrentUser(context);
        oldInfo.update(context, oldInfo.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                mView.showToast("更新成功");
            }

            @Override
            public void onFailure(int i, String s) {
                mView.showToast("更新失败");
            }
        });
//    @Override
//    public void pushURL(Context context, Uri uri) {
//        try {
//            String url= URLDecoder.decode(String.valueOf(uri),"UTF-8");
//            String parh= Environment.getExternalStorageDirectory()+"/juexian/testphoto.jpg";
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
    }
}
