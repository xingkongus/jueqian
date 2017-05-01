package us.xingkong.jueqian.module.me.mymainpage.editinfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import me.iwf.photopicker.PhotoPicker;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;

/**
 * Created by PERFECTLIN on 2017/4/20 0020.
 */

public class EditInfoActivity extends BaseActivity<EditInfoContract.Presenter> implements EditInfoContract.View {
    @BindView(R.id.layout_touxiang)
    CardView touxiang;
    @BindView(R.id.layout_nickname)
    CardView nickname;
    @BindView(R.id.layout_selfintro)
    CardView selfintro;
    @BindView(R.id.layout_blog)
    CardView blog;
    @BindView(R.id.iv_touxiang)
    CircleImageView iv_touxiang;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_selfintro)
    TextView tv_selfintro;
    @BindView(R.id.tv_blog)
    TextView tv_blog;


    private Activity activity = this;
    private BmobFile bmobFile;
    private File profile;
    private String inputNickName;
    private String inputSelfIntro;
    private String inputBlog;
    private _User update_user;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    _User user = new _User();
                    user.setProfile(bmobFile);
                    user.update(JueQianAPP.getAppContext(), BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            showToast("上传头像成功");
                            setProfile();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            showToast("上传头像失败" + s);
                        }
                    });
                    break;
                case 2:
                    prepareData();
                    break;
                case 3:
                    _User u = new _User();
                    u.setNickname(inputNickName);
                    u.update(JueQianAPP.getAppContext(), BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            String nickname = BmobUser.getCurrentUser(JueQianAPP.getAppContext(), _User.class).getNickname();
                            showToast("修改昵称成功");
                            handler.sendEmptyMessage(2);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            showToast("修改昵称失败CASE:" + s);
                        }
                    });
                    break;
                case 4:
                    _User u1 = new _User();
                    u1.setSelfsign(inputSelfIntro);
                    u1.update(JueQianAPP.getAppContext(), BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            String nickname = BmobUser.getCurrentUser(JueQianAPP.getAppContext(), _User.class).getNickname();
                            showToast("修改简介成功");
                            handler.sendEmptyMessage(2);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            showToast("修改简介失败CASE:" + s);
                        }
                    });
                    break;
                case 5:
                    _User u2 = new _User();
                    u2.setBlog(inputBlog);
                    u2.update(JueQianAPP.getAppContext(), BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId(), new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            String nickname = BmobUser.getCurrentUser(JueQianAPP.getAppContext(), _User.class).getNickname();
                            showToast("修改blog成功");
                            handler.sendEmptyMessage(2);
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            showToast("修改blog失败CASE:" + s);
                        }
                    });
                    break;
            }

        }
    };

    @Override
    protected EditInfoContract.Presenter createPresenter() {
        return new EditInfoPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_editinfo;
    }

    @Override
    protected void prepareData() {
        BmobQuery<_User> bmobQuery = new BmobQuery<>();
        bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        bmobQuery.getObject(JueQianAPP.getAppContext(), BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId(), new GetListener<_User>() {
            @Override
            public void onSuccess(_User user) {
                update_user = user;
                showToast("更新用户成功");
                tv_nickname.setText(user.getNickname());
                tv_selfintro.setText(user.getSelfsign());
                tv_blog.setText(user.getBlog());
            }

            @Override
            public void onFailure(int i, String s) {
                showToast("更新用户失败CASE:" + s);
            }
        });
    }

    @Override
    protected void initView() {
        setToolbar();
        setProfile();
        setPhotoPick();
        setNickName();
        setSelfIntro();
        setBlog();
    }

    private void setBlog() {
        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(view.getContext())
                        .title("修改网站")
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                inputBlog = input.toString();
                            }
                        }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        handler.sendEmptyMessage(5);
                    }
                }).show();
            }
        });
    }

    private void setSelfIntro() {
        selfintro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(view.getContext())
                        .title("修改简介")
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                inputSelfIntro = input.toString();
                            }
                        }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        handler.sendEmptyMessage(4);
                    }
                }).show();
            }
        });
    }

    private void setNickName() {
        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(view.getContext())
                        .title("修改昵称")
                        .negativeText("取消")
                        .positiveText("确认")
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                inputNickName = input.toString();
                            }
                        }).onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        handler.sendEmptyMessage(3);
                    }
                }).show();
            }
        });
    }

    private void setProfile() {
        BmobQuery<_User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId());
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
                    showToast("bmobfile is null");
                    return;
                }
                String profileURL = bmobFile.getUrl();
                Glide.with(JueQianAPP.getAppContext()).load(profileURL).into(iv_touxiang);
            }

            @Override
            public void onError(int i, String s) {
                showToast("获取头像失败");
            }
        });
    }

    private void setPhotoPick() {
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(false)
                        .start(activity, PhotoPicker.REQUEST_CODE);
            }
        });
    }

    private void setToolbar() {
        ActionBar acb = getSupportActionBar();
        acb.setDisplayHomeAsUpEnabled(true);
        acb.setTitle("编辑个人资料");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                String photo = photos.get(0);
                File file=new File(photo);
                File zipFile= Compressor.getDefault(JueQianAPP.getAppContext()).compressToFile(file);
                bmobFile = new BmobFile(zipFile);
                bmobFile.uploadblock(JueQianAPP.getAppContext(), new UploadFileListener() {
                    @Override
                    public void onSuccess() {
                        showToast("上传到block成功");
                        handler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        showToast("上传到block失败" + s);
                    }
                });


            }
        }
    }

    private Bitmap zipPic(String path) {
        Bitmap zippic = BitmapFactory.decodeFile(path);
        int width = zippic.getWidth();
        int height = zippic.getHeight();
        // 设置想要的大小
        int newWidth = 50;
        int newHeight = 50;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(zippic, 0, 0, width, height, matrix, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
