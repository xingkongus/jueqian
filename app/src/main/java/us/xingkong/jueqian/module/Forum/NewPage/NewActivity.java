package us.xingkong.jueqian.module.Forum.NewPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.OnClick;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;
import us.xingkong.jueqian.module.main.MainActivity;


/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class NewActivity extends BaseActivity<NewContract.Presenter> implements NewContract.View {


    @BindView(R.id.bt_tag_newpage)
    Button btTagNewpage;
    @BindView(R.id.tv_tag_newpage)
    TextView tvTagNewpage;
    private Context mContext;
    @BindView(R.id.title_new)
    EditText title;
    @BindView(R.id.new_answercontent)
    EditText content;
    @BindView(R.id.tag1_new)
    TextView tag1;
    @BindView(R.id.tag2_new)
    TextView tag2;

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
                                String t=title.getText().toString();
                                String c=content.getText().toString();
                                String t1=tag1.getText().toString();
                                String t2=tag2.getText().toString();
                                if(!t.isEmpty()&&!c.isEmpty()&&!t1.isEmpty()){
                                    mPresenter.addQuestion(mContext,t,c,t1,t2);
                                    Intent intent=new Intent(mContext,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
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
                            int length=which.length;
                            if(length==2){
                                tag1.setText(text[0]);
                                tag2.setText(text[1]);
                            }else{
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


}
