package us.xingkong.jueqian.module.me.mycollection;

import android.util.Log;

import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MyCollectionPresenter extends BasePresenterImpl implements MyCollectionContract.Presenter {
    private final MyCollectionContract.View mView;
    private List<Question> questions;

    public MyCollectionPresenter(MyCollectionContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    public List<Question> getCollections() {
        BmobQuery<Question> query = new BmobQuery<Question>();
        _User user = new _User();
        user.setObjectId("1mdf000N");
        query.addWhereRelatedTo("recentlooks", new BmobPointer(user));
        System.out.println("---------------------------------------");
        query.findObjects(JueQianAPP.getAppContext(), new FindListener<Question>() {
            @Override
            public void onSuccess(List<Question> list) {
                questions = list;
                mView.showToast("获取收藏表失败成功");
                for (Question question : list) {
                    System.out.println("ssssssssssssssssssssssss" + question.getMtitle());
                }
            }

            @Override
            public void onError(int i, String s) {
                mView.showToast("获取收藏表失败");
                System.out.println("ffffffffffffffffffffffffffffffffff" + s);
            }
        });
//        query.findObjects(new FindListener<Question>() {
//            @Override
//            public void done(List<Question> list, BmobException e) {
//                mView.showToast("获取收藏列表成功");
//                questions = list;
//                for (Question question : list) {
//                    System.out.println("sssssssssssssss" + question.getMtitle());
//                }
//            }
//        });


        return questions;
    }
}
