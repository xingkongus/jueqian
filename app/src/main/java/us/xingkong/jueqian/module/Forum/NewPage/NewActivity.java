package us.xingkong.jueqian.module.Forum.NewPage;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;

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
                        .title("警告")
                        .content("你的手机即将爆炸，请迅速关机扔出窗外")
                        .positiveText("同意")
                        .negativeText("赞同")
                        .show();
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
                        boolean allowSelectionChange = which.length <= 2; // limit selection to 2, the new (un)selection is included in the which array
                        if (!allowSelectionChange) {
                            showToast("至多选择2个tag");
                        } else if (which.length != 0) {
                            tvTagNewpage.setText(which.length == 2 ? text[0].toString() + "\n" + text[1].toString() : text[0]);
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
