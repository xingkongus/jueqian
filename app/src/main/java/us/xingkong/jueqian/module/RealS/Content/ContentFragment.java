package us.xingkong.jueqian.module.RealS.Content;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseFragment;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/8 15:30
 */

public class ContentFragment extends BaseFragment<ContentContract.Presenter> implements ContentContract.View {

    @BindView(R.id.tv_page_count)
    TextView mTvPageCount;

    private String mRealSTitle;
    private static final String REALS_TITLE = "reals_title";

    public static ContentFragment getInstance(String title) {
        ContentFragment fra = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(REALS_TITLE, title);
        fra.setArguments(bundle);
        return fra;
    }

    @Override
    protected ContentContract.Presenter createPresenter() {
        return new ContentPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_reals_content;
    }

    @Override
    protected void prepareData(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mRealSTitle = bundle.getString(REALS_TITLE);
    }

    @Override
    protected void initView(View rootView) {
        mTvPageCount.setText(mRealSTitle + "页");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @Override
    protected void initEvent() {

    }

}
