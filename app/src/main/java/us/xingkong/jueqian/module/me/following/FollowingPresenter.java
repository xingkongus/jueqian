package us.xingkong.jueqian.module.me.following;

import android.os.Handler;
import android.util.Log;

import java.util.List;

import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class FollowingPresenter extends BasePresenterImpl implements FollowingContract.Presenter {
    private final FollowingContract.View mView;
    static List<Question> questions;
    private Handler mHandler;

    public FollowingPresenter(FollowingContract.View mView, Handler mHandler) {
        this.mView = mView;
        this.mHandler = mHandler;
        this.mView.setPresenter(this);
        Log.i("1", "~~~~~~~~~~~~~~~~~~~~~~~" + mHandler);
    }

    public List<Question> getCollections() {
//        BmobUser bmobUser = BmobUser.getCurrentUser(JueQianAPP.getAppContext());
//        Log.i("1", "---------------------------------1"+bmobUser);
//        final BmobQuery<Question> query = new BmobQuery<Question>();
//        query.addWhereRelatedTo("collections", new BmobPointer(bmobUser));
//        query.findObjects(JueQianAPP.getAppContext(), new FindListener<Question>() {
//                    @Override
//                    public void onSuccess(List<Question> list) {
//
//                    }
//
//                    @Override
//                    public void onError(int i, String s) {
//
//                    }
//                });
//
//                Log.i("1", "++++++++++++++++++++++++++++++++++:" + questions);
//        return questions;

//        BmobQuery<_User> query=new BmobQuery<>();
//        String username=BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getUsername();
//        query.addWhereEqualTo("username",username);
//        query.setLimit(20);
//        query.order("-createdAt");
//        query.findObjects(JueQianAPP.getAppContext(), new FindListener<_User>() {
//            @Override
//            public void onSuccess(List<_User> list) {
//                questions= (List<Question>) list.get(0).getCollections();
//                mHandler.sendEmptyMessage(1);
//                mView.showToast("获取收藏列表成功");
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                mView.showToast("获取收藏列表失败");
//            }
//        });
//
//        return questions;
        return null;
    }
}
