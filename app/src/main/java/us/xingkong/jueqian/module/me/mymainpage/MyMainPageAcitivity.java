package us.xingkong.jueqian.module.me.mymainpage;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
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
 * Created by PERFECTLIN on 2017/4/19 0019.
 */

public class MyMainPageAcitivity extends BaseActivity<MyMainPageContract.Presenter> implements MyMainPageContract.View {
    @BindView(R.id.mymainpage_bt_edit)
    Button bt_edit;
    @BindView(R.id.mymainpage_tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.mymainpage_tv_following_count)
    TextView tv_following;
    @BindView(R.id.mymainpage_tv_follower_count)
    TextView tv_followers;
    @BindView(R.id.mymainpage_touxiang)
    CircleImageView iv_touxiang;
    @BindView(R.id.mymainpage_collections)
    CardView collections;
    @BindView(R.id.mymainpage_recentlooks)
    CardView rencentlooks;
    @BindView(R.id.mymainpage_collection_count)
    TextView tv_collectioncount;
    @BindView(R.id.mymainpage_ry_follower)
    RelativeLayout ry_follower;
    @BindView(R.id.mymainpage_ry_following)
    RelativeLayout ry_following;
    @BindView(R.id.mymainpage_tv_selfintro)
    TextView tv_selfIntro;

    private _User current_user;

    @Override
    protected MyMainPageContract.Presenter createPresenter() {
        return new MyMainPagePresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_mymainpage;
    }

    @Override
    protected void prepareData() {
        current_user = BmobUser.getCurrentUser(JueQianAPP.getAppContext(), _User.class);

        updateFans();
        updateFollowing();
    }

    private void updateFollowing() {
        //更新关注的人
        BmobQuery<Follow> query_following = new BmobQuery<Follow>();
        query_following.addWhereEqualTo("followUser", new BmobPointer(current_user));
//        query_following.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query_following.findObjects(JueQianAPP.getAppContext(), new FindListener<Follow>() {
            @Override
            public void onSuccess(List<Follow> list) {
//                showToast("获取关注的人成功" + list.size());
                if (list == null) return;
                if (list.size() == 0) return;
                if (tv_following == null) return;
                tv_following.setText(String.valueOf(list.size()));
            }

            @Override
            public void onError(int i, String s) {
//                showToast("获取关注的人失败CASE:" + s);
            }
        });
    }

    private void updateFans() {
        //更新粉丝
        _User user = new _User();
        user.setObjectId(current_user.getObjectId());
        BmobQuery<Follow> query_follower = new BmobQuery<Follow>();
        query_follower.addWhereRelatedTo("followedUser", new BmobPointer(user));
//        query_follower.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query_follower.findObjects(JueQianAPP.getAppContext(), new FindListener<Follow>() {
            @Override
            public void onSuccess(List<Follow> list) {
                if (list == null) return;
                if (list.size() == 0) return;
                tv_followers.setText(String.valueOf(list.size()));
            }

            @Override
            public void onError(int i, String s) {
//                showToast("获取粉丝失败");
            }
        });
    }

    @Override
    protected void initView() {
        initToolbar();
        initNickName();
        initSelfIntro();
        initEditButton();
        initCollection();
        initRencentLooks();
        initProfile();
        toFollowing();
        toFollower();
    }

    private void initSelfIntro() {
        BmobQuery<_User> bmobQuery = new BmobQuery<>();
//        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        bmobQuery.getObject(JueQianAPP.getAppContext(), current_user.getObjectId(), new GetListener<_User>() {
            @Override
            public void onSuccess(_User user) {
//                showToast("更新用户昵称成功");
                if (user.getSelfsign() == null) return;
                if (tv_selfIntro == null) return;
                tv_selfIntro.setText(user.getSelfsign());
            }

            @Override
            public void onFailure(int i, String s) {
//                showToast("更新用户简介失败CASE:" + s);
            }
        });
    }

    private void toFollower() {
        ry_follower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JueQianAPP.getAppContext(), FollowerActivity.class);
                intent.putExtra("intentUserID", current_user.getObjectId());
                startActivity(intent);
            }
        });
    }

    private void toFollowing() {
        ry_following.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JueQianAPP.getAppContext(), FollowingActivity.class);
                intent.putExtra("intentUserID", current_user.getObjectId());
                startActivity(intent);
            }
        });
    }

    private void initNickName() {
        BmobQuery<_User> bmobQuery = new BmobQuery<>();
        bmobQuery.getObject(JueQianAPP.getAppContext(), current_user.getObjectId(), new GetListener<_User>() {
            @Override
            public void onSuccess(_User user) {
//                showToast("更新用户昵称成功");
                if (user.getNickname() == null) {
                    tv_nickname.setText("请前往设置你的昵称..");
                    return;
                }
                if (tv_nickname == null) return;
                tv_nickname.setText(user.getNickname());
            }

            @Override
            public void onFailure(int i, String s) {
//                showToast("更新昵称失败CASE:" + s);
            }
        });
    }

    private void initRencentLooks() {
        rencentlooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JueQianAPP.getAppContext(), MyRecentLookActivity.class);
                intent.putExtra("intentUserID", current_user.getObjectId());
                startActivity(intent);
            }
        });
    }

    private void initCollection() {
        BmobQuery<Question> query = new BmobQuery<Question>();
        query.addWhereRelatedTo("collections", new BmobPointer(current_user));
//        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(JueQianAPP.getAppContext(), new FindListener<Question>() {
            @Override
            public void onSuccess(List<Question> list) {
                if (list == null) return;
                if (tv_collectioncount == null) return;
                tv_collectioncount.setText(String.valueOf(list.size()));
            }

            @Override
            public void onError(int i, String s) {
//                showToast("获取收藏表失败");
            }
        });
        collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JueQianAPP.getAppContext(), MyCollectionActivity.class);
                intent.putExtra("intentUserID", current_user.getObjectId());
                startActivity(intent);
            }
        });

    }

    private void initProfile() {

        BmobQuery<_User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", current_user.getObjectId());
        query.addQueryKeys("profile");
        query.findObjects(JueQianAPP.getAppContext(), new FindListener<_User>() {
            @Override
            public void onSuccess(List<_User> list) {
                if (list == null) return;
                if (list.size() == 0) {
//                    showToast("当前用户无头像");
                    return;
                }
                BmobFile bmobFile = list.get(0).getProfile();
                if (bmobFile == null) {
//                    showToast("获取头像异常");
                    return;
                }
                String profileURL = bmobFile.getUrl();
                Picasso.with(MyMainPageAcitivity.this).load(profileURL).into(iv_touxiang);
//                if (iv_touxiang == null) return;
//                Glide.with(JueQianAPP.getAppContext()).load(profileURL).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<GlideDrawable>() {
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                        iv_touxiang.setImageDrawable(resource);
//                    }
//                });
            }

            @Override
            public void onError(int i, String s) {
//                showToast("获取头像失败CASE:" + s);
            }
        });
    }

    private void initEditButton() {
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JueQianAPP.getAppContext(), EditInfoActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initToolbar() {
        ActionBar acb = getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("我的主页");
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
        initProfile();
        initCollection();
        initSelfIntro();
        initNickName();
        updateFans();
        updateFollowing();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
