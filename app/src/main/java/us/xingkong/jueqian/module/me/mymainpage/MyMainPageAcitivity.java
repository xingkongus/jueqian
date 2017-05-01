package us.xingkong.jueqian.module.me.mymainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.module.me.mycollection.MyCollectionActivity;
import us.xingkong.jueqian.module.me.mymainpage.editinfo.EditInfoActivity;
import us.xingkong.jueqian.module.me.myrecentlook.MyRecentLookActivity;

/**
 * Created by PERFECTLIN on 2017/4/19 0019.
 */

public class MyMainPageAcitivity extends BaseActivity<MyMainPageContract.Presenter> implements MyMainPageContract.View {
    @BindView(R.id.mymainpage_bt_edit)
    Button bt_edit;
    @BindView(R.id.mymainpage_nickname)
    TextView tv_nickname;
    @BindView(R.id.following)
    TextView tv_following;
    @BindView(R.id.followers)
    TextView tv_followers;
    @BindView(R.id.mymainpage_touxiang)
    CircleImageView iv_touxiang;
    @BindView(R.id.collections)
    CardView collections;
    @BindView(R.id.recentlooks)
    CardView rencentlooks;
    @BindView(R.id.collectioncount)
    TextView tv_collectioncount;

    private String intentUserID = null;
    private _User follow_user;
    private _User befollowed_user;

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

    }

    @Override
    protected void initView() {
        setToolbar();
        initNickName();
        setEditButton();
        setCollection();
        setRencentLooks();
        setProfile();
    }

    private void initNickName() {
        BmobQuery<_User> bmobQuery = new BmobQuery<>();
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        bmobQuery.getObject(JueQianAPP.getAppContext(), BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId(), new GetListener<_User>() {
            @Override
            public void onSuccess(_User user) {
                showToast("更新用户昵称成功");
                tv_nickname.setText(user.getNickname());
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("更新用户昵称失败CASE:" + s);
            }
        });
    }

    private void setRencentLooks() {
        rencentlooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JueQianAPP.getAppContext(), MyRecentLookActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setCollection() {
        BmobUser bmobUser = BmobUser.getCurrentUser(JueQianAPP.getAppContext());
        BmobQuery<Question> query = new BmobQuery<Question>();
        query.addWhereRelatedTo("collections", new BmobPointer(bmobUser));
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
                startActivity(intent);
            }
        });

    }

    private void setProfile() {

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

    private void setEditButton() {
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JueQianAPP.getAppContext(), EditInfoActivity.class);
                startActivity(intent);
            }
        });
    }


    private void setToolbar() {
        ActionBar acb = getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("个人主页");
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
        setProfile();
        setCollection();
        initNickName();
    }
}
