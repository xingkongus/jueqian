package us.xingkong.jueqian.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.ValueEventListener;
import de.hdodenhof.circleimageview.CircleImageView;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseFragment;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.module.Login.LoginActivity;
import us.xingkong.jueqian.module.me.myanswer.MyAnswerActivity;
import us.xingkong.jueqian.module.me.mycollection.MyCollectionActivity;
import us.xingkong.jueqian.module.me.mymainpage.MyMainPageAcitivity;
import us.xingkong.jueqian.module.me.mymessage.MyMessageActivity;
import us.xingkong.jueqian.module.me.myquestions.MyQuestionsAcitivity;
import us.xingkong.jueqian.module.me.myrecentlook.MyRecentLookActivity;
import us.xingkong.jueqian.module.me.mysettings.MySettingsActivity;

/**
 * Created by PERFECTLIN on 2017/1/10 0010.
 */

public class MeFragment extends BaseFragment<MeContract.Presenter> implements MeContract.View {
    @BindView(R.id.me_tv_nickname)
    TextView mTextView_nickname;
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
    @BindView(R.id.me_layout_edit)
    CardView mCardView_editinfo;
    @BindView(R.id.me_layout_signout)
    CardView mCardView_signout;
    @BindView(R.id.me_profile)
    CircleImageView mCircleImageView_profile;
    @BindView(R.id.redpoint)
    CircleImageView mCircleImageView_redpoint;

    private static final String PAGE_COUNT = "page_count";
    private boolean isLogin;
    private _User user; //当前用户
    private BmobFile bmobFile;
    private BmobRealTimeData rtd;
    private static String profileURL = null; //头像URL
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    break;
            }
        }
    };

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
        judgeLogin();
        initNickName();
        toEditInfo();
        toMyMessage();
        initRedPoint();
        toMyCollection();
        toMyAnswer();
        toMyRecentLook();
        toMySettings();
        toMyQusetions();
        toSignOut();
        getProfile();
    }

    private void initRedPoint() {
        //开始监听数据库
        rtd = new BmobRealTimeData();
        rtd.start(JueQianAPP.getAppContext(), new ValueEventListener() {
            @Override
            public void onConnectCompleted() {
                if (rtd.isConnected()) {
                    rtd.subTableUpdate("NewMessage");
                } else showToast("链接异常");
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {
                try {
                    JSONObject dataObject = jsonObject.getJSONObject("data");
                    JSONObject receiverObj = dataObject.getJSONObject("receiver");
                    JSONObject senderObj = dataObject.getJSONObject("sender");
                    String s1 = receiverObj.getString("objectId");
                    String s2 = senderObj.getString("objectId");
                    if (s1.equals(BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId())) {
                        mCircleImageView_redpoint.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initNickName() {
        _User user=BmobUser.getCurrentUser(JueQianAPP.getAppContext(),_User.class);
        if (user==null)return;
        BmobQuery<_User> bmobQuery = new BmobQuery<>();
        _User bmobUser = BmobUser.getCurrentUser(JueQianAPP.getAppContext(), _User.class);
        if (bmobUser == null) return;
        bmobQuery.getObject(JueQianAPP.getAppContext(), BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId(), new GetListener<_User>() {
            @Override
            public void onSuccess(_User user) {
                showToast("更新用户昵称成功");
                mTextView_nickname.setText(user.getNickname());
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("更新用户昵称失败CASE:" + s);
            }
        });
    }

    private void toSignOut() {
        if (!isLogin) mCardView_signout.setVisibility(View.INVISIBLE);
        mCardView_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(view.getContext())
                        .title("提示")
                        .content("确认注销？")
                        .negativeText("取消")
                        .positiveText("确认")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                BmobUser.logOut(JueQianAPP.getAppContext());   //清除缓存用户对象
                                BmobUser currentUser = BmobUser.getCurrentUser(JueQianAPP.getAppContext()); // 现在的currentUser是null了
                                Intent intent = new Intent(JueQianAPP.getAppContext(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            }
                        }).show();

            }
        });
    }

    private void judgeLogin() {
        BmobUser bmobUser = BmobUser.getCurrentUser(JueQianAPP.getAppContext());
        if (bmobUser == null) isLogin = false;
        else isLogin = true;
    }

    private void toMyCollection() {
        mLinerlayout_mycollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin) {
                    Intent intent = new Intent(getContext(), MyCollectionActivity.class);
                    intent.putExtra("intentUserID", BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId());
                    startActivity(intent);
                } else {
                    Toast.makeText(JueQianAPP.getAppContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void toMyAnswer() {
        mLinerlayout_myanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin) {
                    Intent intent = new Intent(getContext(), MyAnswerActivity.class);
                    intent.putExtra("intentUserID", BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId());
                    startActivity(intent);
                } else {
                    Toast.makeText(JueQianAPP.getAppContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void toMyRecentLook() {
        mLinerlayout_myrecentlook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin) {
                    Intent intent = new Intent(getContext(), MyRecentLookActivity.class);
                    intent.putExtra("intentUserID", BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId());
                    startActivity(intent);
                } else {
                    Toast.makeText(JueQianAPP.getAppContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }

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

    private void toMyMessage() {

        mLinerlayout_mymessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin) {
                    Intent intent = new Intent(getContext(), MyMessageActivity.class);
                    startActivity(intent);
                    mCircleImageView_redpoint.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(JueQianAPP.getAppContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void toMyQusetions() {
        mLinerlayout_myquestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin) {
                    Intent intent = new Intent(getContext(), MyQuestionsAcitivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(JueQianAPP.getAppContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void toEditInfo() {
        mCardView_editinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _User user = BmobUser.getCurrentUser(JueQianAPP.getAppContext(), _User.class);
                if (user == null) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), MyMainPageAcitivity.class);
                    intent.putExtra("intentUserID", BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId());
                    intent.putExtra("profileURL", profileURL);
                    startActivity(intent);
                }
            }
        });
    }

    private void getProfile() {
        if (isLogin) {
            BmobQuery<_User> query = new BmobQuery<>();
            query.addWhereEqualTo("objectId", BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId());
            query.addQueryKeys("profile");
            query.findObjects(JueQianAPP.getAppContext(), new FindListener<_User>() {
                @Override
                public void onSuccess(List<_User> list) {
                    if (list.size() == 0) {
                        showToast("当前用户无头像");
                        return;
                    }
                    bmobFile = list.get(0).getProfile();
                    if (bmobFile == null) {
                        showToast("当前用户无头像");
                        return;
                    }
                    profileURL = bmobFile.getUrl();
                    Glide.with(JueQianAPP.getAppContext()).load(profileURL).into(mCircleImageView_profile);
                }

                @Override
                public void onError(int i, String s) {
                    showToast("获取头像失败");
                }
            });
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        getProfile();
        initNickName();
    }
}