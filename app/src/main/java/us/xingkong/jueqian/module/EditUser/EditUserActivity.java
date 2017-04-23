package us.xingkong.jueqian.module.EditUser;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;


public class EditUserActivity extends BaseActivity<EditUserContract.Presenter> implements EditUserContract.View {



    @Override
    protected EditUserContract.Presenter createPresenter() {
        return new EditUserPresenter(this);
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
        setToolbarBackEnable("编辑");

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nick = nick_edit.getText().toString();
//                phone = phone_edit.getText().toString();
//                email = email_edit.getText().toString();
//                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bmp, null, null));
//                try {
//                    String url = URLDecoder.decode(String.valueOf(uri), "UTF-8");
//                    mPresenter.saveUser(context, url, nick, phone, email, sex);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });
//        touxiang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CharSequence[] items = {"相册", "相机"};
//                new AlertDialog.Builder(context).setTitle("选择图片来源").setItems(items, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (which == 0) {
//                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                            intent.setAction(Intent.ACTION_GET_CONTENT);
//                            intent.setType("image/*");
//                            startActivityForResult(Intent.createChooser(intent, "选择图片"), 1000);
//                        } else {
//
//                            File file = new File(Environment.getExternalStorageDirectory() + "/juexian", "testphoto.jpg");
//                            outputFileUri = Uri.fromFile(file);
//
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            startActivityForResult(intent, 1001);
//                        }
//                    }
//                }).create().show();
//            }
//        });
//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                int radioButtonId = group.getCheckedRadioButtonId();
//                RadioButton rb = (RadioButton) EditUserActivity.this.findViewById(radioButtonId);
//                sex = rb.getText().toString();
//            }
//        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1000) {
//
//            if (data == null) {
//                return;
//            } else {
//                Uri uri = data.getData();
//                ContentResolver cr = this.getContentResolver();
//                try {
//                    bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
//                    touxiang.setImageBitmap(bmp);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        } else if (requestCode == 1001) {
//            bmp = (Bitmap) data.getExtras().get("data");
//            touxiang.setImageBitmap(bmp);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
