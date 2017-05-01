package us.xingkong.jueqian.module.me.mainpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.module.me.mymainpage.MyMainPagePresenter;
import us.xingkong.jueqian.module.me.mymainpage.editinfo.EditInfoActivity;

/**
 * Created by PERFECTLIN on 2017/4/30 0030.
 */

public class MainPageAcitivity extends BaseActivity<MainPageContract.Presenter> implements MainPageContract.View {
    @BindView(R.id.mainpage_bt_edit)
    Button bt_edit;
    @BindView(R.id.mainpage_nickname)
    TextView tv_nickname;
    @BindView(R.id.following)
    TextView tv_following;
    @BindView(R.id.followers)
    TextView tv_followers;
    @BindView(R.id.mainpage_touxiang)
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
    protected MainPageContract.Presenter createPresenter() {
        return new MainPagePresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_mainpage;
    }

    @Override
    protected void prepareData() {
        Intent intent = getIntent();
        intentUserID = intent.getStringExtra("intentUserID");
    }

    @Override
    protected void initView() {
        setToolbar();
        initNickName();
        setFocusButton();

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

    private void setToolbar() {
        ActionBar acb = getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("个人主页");
    }

    private void setFocusButton() {
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
            //判断当前用户是否存关注已经关注此用户
            BmobQuery<_User> query = new BmobQuery<>();
            follow_user = new _User();
            befollowed_user = new _User();
            follow_user.setObjectId(BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId());
            befollowed_user.setObjectId(intentUserID);

            query.addWhereRelatedTo("following", new BmobPointer(befollowed_user));
            query.findObjects(JueQianAPP.getAppContext(), new FindListener<_User>() {
                @Override
                public void onSuccess(List<_User> list) {
                    if (list.size() == 0) {
                        bt_edit.setText("关注+");
                        showToast("未关注此用户" + list.size());
                    } else if (list.size() == 1) {
                        bt_edit.setText("取消关注");
                        bt_edit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        showToast("已关注此用户" + list.size());
                    }

                }

                @Override
                public void onError(int i, String s) {
                    showToast("查询是否存在关注关系失败CASE:" + s);
                }
            });

            bt_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bt_edit.getText().equals("取消关注")) {
                        BmobRelation bmobRelation = new BmobRelation();
                        bmobRelation.remove(befollowed_user);
                        follow_user.setFollowing(bmobRelation);
                        follow_user.update(JueQianAPP.getAppContext(), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                bt_edit.setText("关注+");
                                bt_edit.setTextColor(getResources().getColor(R.color.text_primary));
                                bt_edit.setBackgroundColor(getResources().getColor(R.color.text_white));
                                showToast("取消关注成功！");
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                showToast("取消关注失败！CASE:" + s);
                            }
                        });
                    }
                    if (bt_edit.getText().equals("关注+")) {
                        showToast("befollowed_ID:" + befollowed_user.getObjectId() + "  follow_ID:" + follow_user.getObjectId());
                        //添加关注者的多对多关联
                        BmobRelation bmobRelation1 = new BmobRelation();
                        bmobRelation1.add(befollowed_user);
                        follow_user.setFollowing(bmobRelation1);
                        follow_user.update(JueQianAPP.getAppContext(), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                bt_edit.setText("取消关注");
                                bt_edit.setTextColor(getResources().getColor(R.color.text_white));
                                bt_edit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                showToast("关注成功");
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                showToast("关注失败CASE:" + s);
                            }
                        });
                        //添加被关注者的多对多关联
                        BmobRelation bmobRelation = new BmobRelation();
                        bmobRelation.add(follow_user);
                        befollowed_user.setFollowers(bmobRelation);
                        befollowed_user.update(JueQianAPP.getAppContext(), new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                showToast("添加被关注者的多对多关联成功");
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                showToast("添加被关注者的多对多关联失败CASE:" + s);
                            }
                        });


                    }
                }
            });
        }
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
    }
}
