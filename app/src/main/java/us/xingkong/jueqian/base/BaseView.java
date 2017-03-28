package us.xingkong.jueqian.base;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 10:50
 */

public interface BaseView<P> {

    void setPresenter(P presenter);

    void showToast(CharSequence msg);

    void showToast(int msgId);

    void showLoadingDialog(CharSequence msg);

    void hideLoadingDialog();


}
