package us.xingkong.jueqian.module.me.mymainpage.editinfo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import de.hdodenhof.circleimageview.CircleImageView;
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
    @BindView(R.id.iv_touxiang)
    CircleImageView iv_touxiang;
    private Activity activity = this;
    private BmobFile bmobFile;
    private File profile;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    _User user = new _User();
                    user.setValue("profile", bmobFile);
                    user.update(JueQianAPP.getAppContext(), "7xl30002", new UpdateListener() {
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

    }

    @Override
    protected void initView() {
        setToolbar();
        setProfile();
        setPhotoPick();
    }

    private void setProfile() {
        BmobQuery<_User> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", BmobUser.getCurrentUser(JueQianAPP.getAppContext()).getObjectId());
        query.addQueryKeys("profile");
        query.findObjects(JueQianAPP.getAppContext(), new FindListener<_User>() {
            @Override
            public void onSuccess(List<_User> list) {

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
                bmobFile = new BmobFile(new File(photo));
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
