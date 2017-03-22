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

    @BindView(R.id.edit_touxiang)
    ImageView touxiang;
    @BindView(R.id.nick_layout)
    TextInputLayout nick_layout;
    @BindView(R.id.phone_layout)
    TextInputLayout phone_layout;
    @BindView(R.id.email_layout)
    TextInputLayout email_layout;
    @BindView(R.id.edit_nick)
    EditText nick_edit;
    @BindView(R.id.edit_phone)
    EditText phone_edit;
    @BindView(R.id.edit_email)
    EditText email_edit;
    @BindView(R.id.save)
    Button save;
    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;
    String nick;
    String phone;
    String email;
    String sex;
    Context context;
    Bitmap bmp;
    Uri outputFileUri;

    @Override
    protected EditUserContract.Presenter createPresenter() {
        return new EditUserPresenter(this);
    }

    @Override
    protected int bindLayout() {
        //TODO:添加视图，记得添加androidmanifest
        return R.layout.activity_edit_user;
    }

    @Override
    protected void prepareData() {
        //TODO:准备数据 比如：从数据库加载数据，或者网络请求数据等等
    }

    @Override
    protected void initView() {
        //TODO:初始化视图 比如：recycleview的准备，添加adapter等等
        nick_layout.setHint("昵称");
        phone_layout.setHint("手机号");
        email_layout.setHint("邮箱");
        context=this;

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //TODO:初始化数据 比如：将数据加入到view中
    }

    @Override
    protected void initEvent() {
        //TODO:初始化事件监听 比如：增加监听器，下拉刷新，加载更多等等
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nick=nick_edit.getText().toString();
                phone=phone_edit.getText().toString();
                email=email_edit.getText().toString();
                Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bmp, null,null));
                try {
                    String url= URLDecoder.decode(String.valueOf(uri),"UTF-8");
                    mPresenter.saveUser(context,url,nick,phone,email,sex);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

//                mPresenter.pushURL(context,uri);

            }
        });
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence[] items = { "相册", "相机" };
                new AlertDialog.Builder(context).setTitle("选择图片来源").setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "选择图片"), 1000);
                        } else {

                            File file = new File(Environment.getExternalStorageDirectory()+"/juexian", "testphoto.jpg");
                            outputFileUri = Uri.fromFile(file);

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 1001);
                        }
                    }
                }).create().show();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId=group.getCheckedRadioButtonId();
                RadioButton rb= (RadioButton) EditUserActivity.this.findViewById(radioButtonId);
                sex=rb.getText().toString();
            }
        });
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {

            if (data == null) {
                return;
            }
            else {
                Uri uri = data.getData();
                ContentResolver cr=this.getContentResolver();
                try {
                    bmp=BitmapFactory.decodeStream(cr.openInputStream(uri));
                    touxiang.setImageBitmap(bmp);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
        else if(requestCode==1001){
            bmp= (Bitmap) data.getExtras().get("data");
            touxiang.setImageBitmap(bmp);
        }
    }
}
