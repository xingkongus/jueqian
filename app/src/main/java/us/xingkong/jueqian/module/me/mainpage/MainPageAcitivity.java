package us.xingkong.jueqian.module.me.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Follow;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.module.me.follower.FollowerActivity;
import us.xingkong.jueqian.module.me.following.FollowingActivity;
import us.xingkong.jueqian.module.me.mycollection.MyCollectionActivity;
import us.xingkong.jueqian.module.me.mymainpage.editinfo.EditInfoActivity;
import us.xingkong.jueqian.module.me.myrecentlook.MyRecentLookActivity;

/**
 * Created by PERFECTLIN on 2017/4/30 0030.
 */

public class MainPageAcitivity extends BaseActivity<MainPageContract.Presenter> implements MainPageContract.View {
    @BindView(R.id.mainpage_bt_edit)
    Button bt_edit;
    @BindView(R.id.mainpage_nickname)
    TextView tv_nickname;
    @BindView(R.id.mainpage_tv_following_count)
    TextView tv_following;
    @BindView(R.id.mainpage_tv_follower_count)
    TextView tv_followers;
    @BindView(R.id.mainpage_touxiang)
    CircleImageView iv_touxiang;
    @BindView(R.id.collections)
    CardView collections;
    @BindView(R.id.mainpage_recentlooks)
    CardView rencentlooks;
    @BindView(R.id.collectioncount)
    TextView tv_collectioncount;
    @BindView(R.id.mainpage_ry_following)
    RelativeLayout ry_following;
    @BindView(R.id.mainpage_ry_follower)
    RelativeLayout ry_follower;
    @BindView(R.id.mainpage_tv_selfintro)
    TextView tv_selfIntro;

    private String intentUserID;
    private _User followedUser;
    private _User current_user;
    private _User intentUser = new _User();
    private String followID;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //删除关注关系
                    Follow follow = new Follow();
                    follow.setObjectId(followID);
                    follow.delete(JueQianAPP.getAppContext(), new DeleteListener() {
                        @Override
                        public void onSuccess() {
                            updateFans();
                            bt_edit.setText("关注 +");
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            showToast("取消关注失败CASE:" + s);
                        }
                    });
                    break;
            }
        }
    };

    @Override
    protected MainPageContract.Presenter createPresenter() {
        return new MainPagePresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_mainpage;
    }

    @Override
    protected void prepareData() {
        current_user = BmobUser.getCurrentUser(JueQianAPP.getAppContext(), _User.class);
        Intent intent = getIntent();
        intentUserID = intent.getStringExtra("intentUserID");
        intentUser.setObjectId(intentUserID);

        updateFans();
        updateFollowing();
    }

    private void updateFollowing() {
        //更新关注的人
        BmobQuery<Follow> query_following = new BmobQuery<Follow>();
        query_following.addWhereEqualTo("followUser", new BmobPointer(intentUser));
        query_following.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query_following.findObjects(JueQianAPP.getAppContext(), new FindListener<Follow>() {
            @Override
            public void onSuccess(List<Follow> list) {
//                showToast("获取关注的人成功" + list.size());
                if (list.size() == 0) return;
                tv_following.setText("" + list.size());
            }

            @Override
            public void onError(int i, String s) {
                showToast("获取关注的人失败CASE:" + s);
            }
        });
    }

    private void updateFans() {
        //更新粉丝
        _User user = new _User();
        user.setObjectId(intentUserID);
        BmobQuery<Follow> query_follower = new BmobQuery<Follow>();
        query_follower.addWhereEqualTo("followedUser", new BmobPointer(user));
        query_follower.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query_follower.findObjects(JueQianAPP.getAppContext(), new FindListener<Follow>() {
            @Override
            public void onSuccess(List<Follow> list) {
                tv_followers.setText("" + list.size());
            }

            @Override
            public void onError(int i, String s) {
                showToast("获取粉丝失败");
            }
        });
    }

    @Override
    protected void initView() {
        initToolbar();
        initNickName();
        initSelfIntro();
        initFocusButton();
        initProfile();
        initCollection();
        initRecentLooks();
        toFollowing();
        toFollower();

    }

    private void initSelfIntro() {
        BmobQuery<_User> bmobQuery = new BmobQuery<>();
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        bmobQuery.getObject(JueQianAPP.getAppContext(), intentUserID, new GetListener<_User>() {
            @Override
            public void onSuccess(_User user) {
//                showToast("更新用户昵称成功");
                tv_selfIntro.setText(user.getSelfsign());
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("更新用户昵称失败CASE:" + s);
            }
        });
    }

    private void toFollower() {
        ry_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JueQianAPP.getAppContext(), FollowerActivity.class);
                intent.putExtra("intentUserID", intentUserID);
                startActivity(intent);
            }
        });

    }

    private void toFollowing() {
        ry_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JueQianAPP.getAppContext(), FollowingActivity.class);
                intent.putExtra("intentUserID", intentUserID);
                startActivity(intent);
            }
        });

    }

    private void initRecentLooks() {
        rencentlooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JueQianAPP.getAppContext(), MyRecentLookActivity.class);
                intent.putExtra("intentUserID", intentUserID);
                startActivity(intent);
            }
        });
    }

    private void initCollection() {
        _User u = new _User();
        u.setObjectId(intentUserID);
        BmobQuery<Question> query = new BmobQuery<Question>();
        query.addWhereRelatedTo("collections", new BmobPointer(u));
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(JueQianAPP.getAppContext(), new FindListener<Question>() {
            @Override
            public void onSuccess(List<Question> list) {
                tv_collectioncount.setText("" + list.size());
            }

            @Override
            public void onError(int i, String s) {
                showToast("获取收藏表失败");
            }
        });
        collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JueQianAPP.getAppContext(), MyCollectionActivity.class);
                intent.putExtra("intentUserID", intentUserID);
                startActivity(intent);
            }
        });
    }

    private void initProfile() {
        BmobQuery<_User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", intentUserID);
        query.addQueryKeys("profile");
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(JueQianAPP.getAppContext(), new FindListener<_User>() {
            @Override
            public void onSuccess(List<_User> list) {
                if (list.size() == 0) {
                    showToast("当前用户无头像");
                    return;
                }
                BmobFile bmobFile = list.get(0).getProfile();
                if (bmobFile == null) {
                    showToast("当前用户无头像");
                    return;
                }
                String profileURL = bmobFile.getUrl();
                Glide.with(JueQianAPP.getAppContext()).load(profileURL).into(iv_touxiang);
            }

            @Override
            public void onError(int i, String s) {
                showToast("获取头像失败CASE:" + s);
            }
        });
    }

    private void initNickName() {
        BmobQuery<_User> bmobQuery = new BmobQuery<>();
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        bmobQuery.getObject(JueQianAPP.getAppContext(), intentUserID, new GetListener<_User>() {
            @Override
            public void onSuccess(_User user) {
//                showToast("更新用户昵称成功");
                tv_nickname.setText(user.getNickname());
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("更新用户昵称失败CASE:" + s);
            }
        });
    }

    private void initToolbar() {
        ActionBar acb = getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("个人主页");
    }

    private void initFocusButton() {
        if (BmobUser.getCurrentUser(JueQianAPP.getAppContext()) == null) {
            bt_edit.setVisibility(View.INVISIBLE);
            return;
        }
        //判断是否为当前用户
        if (BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId().equals(intentUserID)) {
            bt_edit.setText("编辑");
            bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(JueQianAPP.getAppContext(), EditInfoActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            isFocus();
            bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (bt_edit.getText().equals("关注 +")) {
                        //添加关注关系
                        Follow follow = new Follow();
                        follow.setFollowUser(current_user);
                        follow.setFollowedUser(followedUser);
                        follow.save(JueQianAPP.getAppContext(), new SaveListener() {
                            @Override
                            public void onSuccess() {
                                updateFans();
                                bt_edit.setText("取消关注");
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                showToast("关注失败CASE:" + s);
                            }
                        });
                    }

                    if (bt_edit.getText().equals("取消关注")) {

                        //判断当前用户是否存关注已经关注此用户
                        followedUser = new _User();
                        followedUser.setObjectId(intentUserID);
                        final BmobQuery<Follow> f1 = new BmobQuery<>();
                        f1.addWhereEqualTo("followUser", new BmobPointer(current_user));
                        BmobQuery<Follow> f2 = new BmobQuery<>();
                        f2.addWhereEqualTo("followedUser", new BmobPointer(followedUser));
                        List<BmobQuery<Follow>> queries = new ArrayList<>();
                        queries.add(f1);
                        queries.add(f2);
                        BmobQuery<Follow> query = new BmobQuery<>();
                        query.and(queries);
                        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
                        query.findObjects(JueQianAPP.getAppContext(), new FindListener<Follow>() {
                            @Override
                            public void onSuccess(List<Follow> list) {
                                if (list.size() == 0) {
                                    bt_edit.setText("关注 +");
                                } else if (list.size() == 1) {
                                    if (list.get(0).getObjectId() != null) {
                                        followID = list.get(0).getObjectId();
                                        bt_edit.setText("取消关注");
                                        handler.sendEmptyMessage(1);
                                    }
                                } else {
                                    showToast("关注表数据异常");
                                }
                            }

                            @Override
                            public void onError(int i, String s) {
                                showToast("查询是否存在关注关系失败CASE:" + s);
                            }
                        });
                    }
                }
            });
        }
    }

    private void isFocus() {
        //判断当前用户是否存关注已经关注此用户
        followedUser = new _User();
        followedUser.setObjectId(intentUserID);
        final BmobQuery<Follow> f1 = new BmobQuery<>();
        f1.addWhereEqualTo("followUser", new BmobPointer(current_user));
        BmobQuery<Follow> f2 = new BmobQuery<>();
        f2.addWhereEqualTo("followedUser", new BmobPointer(followedUser));
        List<BmobQuery<Follow>> queries = new ArrayList<>();
        queries.add(f1);
        queries.add(f2);
        BmobQuery<Follow> query = new BmobQuery<>();
        query.and(queries);
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(JueQianAPP.getAppContext(), new FindListener<Follow>() {
            @Override
            public void onSuccess(List<Follow> list) {
//                    handler.sendEmptyMessage(1);
                if (list.size() == 0) {
                    bt_edit.setText("关注 +");
                } else if (list.size() == 1) {
                    if (list.get(0).getObjectId() != null) {
                        followID = list.get(0).getObjectId();
                        bt_edit.setText("取消关注");
                    }
                } else {
                    showToast("关注表数据异常");
                }
            }

            @Override
            public void onError(int i, String s) {
                showToast("查询是否存在关注关系失败CASE:" + s);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initNickName();
        initSelfIntro();
    }
}
