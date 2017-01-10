package us.xingkong.jueqian.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.utils.ToastUtils;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 10:50
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment
        implements BaseView<P> {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Unbinder mUnbinder;
    protected P mPresenter;

    protected ProgressDialog mProgressDialog;

    private ActionBar mActionbar;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
        View root = inflater.inflate(bindLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, root);
        prepareData(savedInstanceState);
        initToolbar();
        // 初始化视图
        initView(root);
        initData(savedInstanceState);
        initEvent();
        return root;
    }


    protected abstract P createPresenter();


    @Override
    public void setPresenter(P presenter) {
        mPresenter = presenter;
    }

    /**
     * 准备数据
     *
     * @param savedInstanceState
     */
    protected abstract void prepareData(Bundle savedInstanceState);

    /**
     * 绑定fragment的布局文件
     *
     * @return
     */
    protected abstract int bindLayout();

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 初始化界面
     *
     * @param rootView
     */
    protected abstract void initView(View rootView);

    /**
     * 初始化事件监听器
     */
    protected abstract void initEvent();


    /**
     * 初始化Toolbar
     */
    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mActionbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    /**
     * 设置Toolbar标题
     *
     * @param title 标题
     */
    protected void setToolbarTitle(String title) {
        if (mActionbar != null) {
            mActionbar.setTitle(title);
        }
    }

    /**
     * 设置Toolbar显示返回按钮及标题
     *
     * @param title 标题
     */
    protected void setToolbarBackEnable(String title) {
        if (mActionbar != null) {
            mActionbar.setDisplayHomeAsUpEnabled(true);
            mActionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_white_24dp);
        }
    }

    @Override
    public void showToast(CharSequence msg) {
        ToastUtils.shortToast(JueQianAPP.getAppContext(), msg);
    }

    @Override
    public void showToast(int msgId) {
        ToastUtils.shortToast(JueQianAPP.getAppContext(), msgId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoadingDialog(CharSequence msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage(msg);
        } else {
            mProgressDialog.setTitle(R.string.title_dialog_tips);
        }
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onDetach() {
        mUnbinder.unbind();
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

}
