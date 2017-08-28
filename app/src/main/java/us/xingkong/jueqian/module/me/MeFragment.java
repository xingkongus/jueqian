package us.xingkong.jueqian.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.ValueEventListener;
import de.hdodenhof.circleimageview.CircleImageView;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseFragment;
import us.xingkong.jueqian.bean.ForumBean.BombBean.NewMessage;
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
    private BmobFile bmobFile;
    private BmobRealTimeData rtd;
    private static String profileURL = null; //头像URL
    private _User current_user;

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
        isSignIn(); //判断是否登陆
        initNickName(); //初始化昵称
        initRedPoint(); //初始化小红点
        initSignOut(); //初始化注销按钮
        initProfile();//初始化头像
        toMyMainPage(); //前往个人主页
        toMyMessage(); //前往我的消息
        toMyCollection(); //前往我的收藏
        toMyAnswer(); //前往我的回答
        toMyRecentLook(); //前往最近浏览
        toMySettings(); //前往我的设置
        toMyQusetions();//前往我的宿舍
    }

    private void initRedPoint() {
        //开始监听数据库
        if (!isLogin) return;
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
                    String receiverID = receiverObj.getString("objectId");
                    int isRead = dataObject.getInt("isRead");
                    if (receiverID.equals(current_user.getObjectId()) && isRead == 0) {
                        if (mCircleImageView_redpoint==null)  return;
                        mCircleImageView_redpoint.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        _User bmobUser = BmobUser.getCurrentUser(JueQianAPP.getAppContext(), _User.class);
        BmobQuery<NewMessage> query = new BmobQuery<>();
        query.addWhereEqualTo("receiver", new BmobPointer(bmobUser));
        query.addWhereNotEqualTo("isRead", 1);
        query.include("sender,messComment.question,messAnswer.question");
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(JueQianAPP.getAppContext(), new FindListener<NewMessage>() {
            @Override
            public void onSuccess(List<NewMessage> list) {
                if (list.size() == 0) {
                    return;
                }
                mCircleImageView_redpoint.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(int i, String s) {
                showToast("获取我的消息列表失败CASE:" + s);
            }
        });
    }

    private void initNickName() {
        if (!isLogin) return;
        BmobQuery<_User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(JueQianAPP.getAppContext(), current_user.getObjectId(), new GetListener<_User>() {
            @Override
            public void onSuccess(_User user) {
                if (user.getNickname() == null) {
                    mTextView_nickname.setText("请前往设置你的昵称..");
                    return;
                }
                if (mTextView_nickname != null)
                    mTextView_nickname.setText(user.getNickname());
            }

            @Override
            public void onFailure(int i, String s) {
            }
        });
    }

    private void initSignOut() {
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
//                                BmobUser currentUser = BmobUser.getCurrentUser(JueQianAPP.getAppContext()); // 现在的currentUser是null了
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

    private void isSignIn() {
        current_user = BmobUser.getCurrentUser(JueQianAPP.getAppContext(), _User.class);
        if (current_user == null) isLogin = false;
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
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
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
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
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
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
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
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
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
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void toMyMainPage() {
        mCardView_editinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_user == null) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), MyMainPageAcitivity.class);
                    intent.putExtra("profileURL", profileURL);
                    startActivity(intent);
                }
            }
        });
    }

    private void initProfile() {
        if (isLogin) {
            BmobQuery<_User> query = new BmobQuery<>();
            query.addWhereEqualTo("objectId", current_user.getObjectId());
            query.addQueryKeys("profile");
            query.findObjects(JueQianAPP.getAppContext(), new FindListener<_User>() {
                @Override
                public void onSuccess(List<_User> list) {
                    if (list.size() == 0) {
                        return;
                    }
                    bmobFile = list.get(0).getProfile();
                    if (bmobFile == null) {
                        return;
                    }
                    profileURL = bmobFile.getUrl();
                    if (mCircleImageView_profile == null) return;
                    Picasso.with(getContext()).load(profileURL).into(mCircleImageView_profile);

                }

                @Override
                public void onError(int i, String s) {
                }
            });
        } else {
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        initProfile();
        initNickName();
    }
}