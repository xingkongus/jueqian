package us.xingkong.jueqian.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.listener.UpdateListener;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.base.BaseFragment;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.bean.ForumBean.GsonBean.User;
import us.xingkong.jueqian.module.main.MainContract;
import us.xingkong.jueqian.module.me.myanswer.MyAnswerActivity;
import us.xingkong.jueqian.module.me.mycollection.MyCollectionActivity;
import us.xingkong.jueqian.module.me.mymessage.MyMessageActivity;
import us.xingkong.jueqian.module.me.myquestions.MyQuestionsAcitivity;
import us.xingkong.jueqian.module.me.myrecentlook.MyRecentLookActivity;
import us.xingkong.jueqian.module.me.mysettings.MySettingsActivity;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MeFragment extends BaseFragment<MeContract.Presenter> implements MeContract.View {
    @BindView(R.id.me_linerlayout_mymessage)
    RelativeLayout mLinerlayout_mymessage;
    @BindView(R.id.me_linerlayout_mycollection)
    RelativeLayout mLinerlayout_mycollection;
    @BindView(R.id.me_linerlayout_myanswer)
    RelativeLayout mLinerlayout_myanswer;
    @BindView(R.id.me_linerlayout_myrecentlook)
    RelativeLayout mLinerlayout_myrecentlook;
    @BindView(R.id.me_linerlayout_settings)
    RelativeLayout mLinerlayout_mysettings;
    @BindView(R.id.me_linerlayout_myquestions)
    RelativeLayout mLinerlayout_myquestions;


    private int mPageCount;
    private static final String PAGE_COUNT = "page_count";

    public static MeFragment getInstance(int page_count) {
        MeFragment fra = new MeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE_COUNT, page_count);
        fra.setArguments(bundle);
        return fra;
    }

    @Override
    protected MeContract.Presenter createPresenter() {
        return new MePresenter(this);
    }

    @Override
    protected void prepareData(Bundle savedInstanceState) {

    }

    @Override
    protected int bindLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(View rootView) {
        toMyMessage();
        toMyCollection();
        toMyAnswer();
        toMyRecentLook();
        toMySettings();
        toMyQusetions();
    }

    private void toMyQusetions() {
        mLinerlayout_myquestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyQuestionsAcitivity.class);
                startActivity(intent);
            }
        });
    }

    private void toMyMessage() {
        mLinerlayout_mymessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyMessageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toMyCollection() {
        mLinerlayout_mycollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyCollectionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toMyAnswer() {
        mLinerlayout_myanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyAnswerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toMyRecentLook() {
        mLinerlayout_myrecentlook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MyRecentLookActivity.class);
                startActivity(intent);
            }
        });
    }

    private void toMySettings() {
        mLinerlayout_mysettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MySettingsActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void initEvent() {

    }
}
