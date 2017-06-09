package us.xingkong.jueqian.module.Forum.NewPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.module.main.MainActivity;
import us.xingkong.jueqian.widget.PictureAndTextEditorView;


public class NewActivity extends BaseActivity<NewContract.Presenter> implements NewContract.View {


    @BindView(R.id.bt_tag_newpage)
    Button btTagNewpage;
    @BindView(R.id.tv_tag_newpage)
    TextView tvTagNewpage;
    private Context mContext;
    @BindView(R.id.title_new)
    EditText title;
    @BindView(R.id.new_answercontent)
    PictureAndTextEditorView content;
    @BindView(R.id.tag1_new)
    TextView tag1;
    @BindView(R.id.tag2_new)
    TextView tag2;
    List<String> imageFiles = new ArrayList<>();
    private String newQuestionID;
    private String newQuestionContent;
    private String newtitle;
    private String newTag1;
    private String newTag2;
    public static NewActivity close = null;
    private int isExistImag;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:                                   //添加新问题成功得到后会得到新问题的ID
                    String myQuestionID = (String) msg.obj;
                    newQuestionID = msg.getData().getString("newQuestionID");
                    if (isExistImag == 0) {                 //添加到我的问题后判断是否有图片，0是无，1是有
                        MainActivity.instance.finish();
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (isExistImag == 1) {
                        mPresenter.upLoadImage(mContext, newQuestionContent, handler);//发现图片上传到服务器
                    }

                    mPresenter.addMyQuestion(mContext, myQuestionID, handler);//添加到我的问题

                    break;
                case 2:
                    imageFiles = (List<String>) msg.obj;                      //得到files后保存图片URL到问题列表
                    mPresenter.saveURL(imageFiles, newQuestionID, mContext, handler);
                    break;
                case 3:
//                    if (isExistImag == 0) {
//                        MainActivity.instance.finish();
//                        Intent intent = new Intent(mContext, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else if (isExistImag == 1) {
//                        mPresenter.upLoadImage(mContext, newQuestionContent, handler);//发现图片上传到服务器
//                    }
                    break;
                case 4://上传成功后进行跳转
//                    MainActivity.instance.finish();
//                    Intent intent = new Intent(mContext, MainActivity.class);
//                    startActivity(intent);
//                    finish();
                    break;
            }
        }
    };

    @Override
    protected NewContract.Presenter createPresenter() {
        return new NewPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_new;
    }

    @Override
    protected void prepareData() {
        mContext = this;
        close = this;

    }

    @Override
    protected void initView() {
        setToolbarBackEnable("提问");
    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.sendMessage:
                new MaterialDialog.Builder(this)
                        .title("提示")
                        .content("是否提交")
                        .negativeText("取消")
                        .positiveText("确认")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                newtitle = title.getText().toString();
                                newQuestionContent = content.getText().toString();
                                newTag1 = tag1.getText().toString();
                                newTag2 = tag2.getText().toString();
                                if (newtitle.length() != 0 && newQuestionContent.length() != 0 && newTag1.length() != 0 && newTag1 != null) {
                                    Pattern p = Pattern.compile("\\/[^ .]+.(gif|jpg|jpeg|png)");
                                    Matcher matcher = p.matcher(newQuestionContent);
                                    if (matcher.find()) {
                                        isExistImag = 1;
                                    } else {
                                        isExistImag = 0;
                                    }
                                    mPresenter.addQuestion(mContext, newtitle, newQuestionContent, newTag1, newTag2, handler);//添加新问题
                                } else {
                                    showToast("内容没有填写完整");
                                }
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                showToast("已取消");
                            }
                        }).show();
                break;
            case R.id.insert_image:
                PhotoPicker.builder()
                        .setPhotoCount(5)
                        .setShowCamera(true)
                        .setShowGif(false)
                        .setPreviewEnabled(false)
                        .start(NewActivity.this, PhotoPicker.REQUEST_CODE);
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                intent.setType("image/*");
//                startActivityForResult(intent, 0);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.bt_tag_newpage)
    public void onClick() {

        new MaterialDialog.Builder(this)
                .title("请选择标签")
                .items(R.array.tag)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        boolean allowSelectionChange = which.length <= 2;
                        if (!allowSelectionChange) {
                            showToast("至多选择2个tag");
                        } else if (which.length != 0) {
                            tvTagNewpage.setText("   ");
//                            tvTagNewpage.setText(which.length == 2 ? text[0].toString() + "\n" + text[1].toString() : text[0]);
                            int length = which.length;
                            if (length == 2) {
                                tag1.setText(text[0]);
                                tag2.setText(text[1]);
                            } else {
                                tag1.setText(text[0]);
                                tag2.setText("  ");
                            }

                        }
                        return allowSelectionChange;
                    }
                })
                .positiveText("确定")
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.newpage, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                String a = photos.toString();
                Pattern p = Pattern.compile("\\/[^ .]+.(gif|jpg|jpeg|png)");
                Matcher matcher = p.matcher(a);
                ArrayList<String> arrayList = new ArrayList<>();
                while (matcher.find()) {
                    arrayList.add(matcher.group());
                }
                for (int i = 0; i < arrayList.size(); i++) {
                    content.insertBitmap(arrayList.get(i));
                }

                //\/[^ .]+.(gif|jpg|jpeg|png)
//                String photo = photos.get(0);
//                File file = new File(photo);
//                File zipFile = Compressor.getDefault(JueQianAPP.getAppContext()).compressToFile(file);
//                bmobFile = new BmobFile(zipFile);
//                bmobFile.uploadblock(JueQianAPP.getAppContext(), new UploadFileListener() {
//                    @Override
//                    public void onSuccess() {
////                        showToast("上传到block成功");
//                        handler.sendEmptyMessage(1);
//                    }
//
//                    @Override
//                    public void onFailure(int i, String s) {
//                        showToast("上传图片到服务器失败，请重试。CASE:" + s);
//                    }
//                });
            }
        }

//        if (resultCode == RESULT_OK && requestCode == 0) {
//            ContentResolver resolver = getContentResolver();
//            // 获得图片的uri
//            Uri originalUri = data.getData();
//            System.out.println("---------uri--"+originalUri);
//            try {
//                Bitmap originalBitmap = BitmapFactory.decodeStream(resolver.openInputStream(originalUri));
//                // 将原始图片的bitmap转换为文件
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
//        }

    }
}
