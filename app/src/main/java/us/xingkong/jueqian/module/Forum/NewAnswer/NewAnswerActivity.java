package us.xingkong.jueqian.module.Forum.NewAnswer;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.base.BaseActivity;

/**
 * Created by lenovo on 2017/3/30.
 */

public class NewAnswerActivity extends BaseActivity <NewAnswerContract.Presenter> implements NewAnswerContract.View{

    @BindView(R.id.new_huida)
    Button new_Answer;
    @BindView(R.id.new_huidacontent)
    EditText new_huidacontent;





    @Override
    protected NewAnswerContract.Presenter createPresenter() {
        return new NewAnswerPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_newanswer;
    }

    @Override
    protected void prepareData() {

    }

    @Override
    protected void initView() {
        setToolbarBackEnable("回答");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

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
