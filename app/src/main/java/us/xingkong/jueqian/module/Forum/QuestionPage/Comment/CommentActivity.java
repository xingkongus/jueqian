package us.xingkong.jueqian.module.Forum.QuestionPage.Comment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import butterknife.BindView;
import us.xingkong.jueqian.R;
import us.xingkong.jueqian.adapter.CommentRecyclerViewAdapter;
import us.xingkong.jueqian.base.BaseActivity;

/**
 * Created by boluoxiaomo
 * Date: 17/1/9
 */

public class CommentActivity extends BaseActivity<CommentContract.Presenter> implements CommentContract.View {


    @BindView(R.id.recyclerview_commentpage)
    RecyclerView recyclerviewCommentpage;
    private CommentRecyclerViewAdapter recyclerViewAdapter;
    private ArrayList questionSets;
    private ArrayList<ArrayList> answerSetsArr;
    private Handler mHandler;
    private Context mContext;


    @Override
    protected CommentContract.Presenter createPresenter() {
        return new CommentPresenter(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_commentpage;
    }

    @Override
    protected void prepareData() {
        mContext = this;
        questionSets = new ArrayList();
        questionSets.add("header");
        answerSetsArr = new ArrayList();
        for (int i = 0; i < 30; ++i) {
            ArrayList answerSets = new ArrayList();
            answerSets.add("position " + i);
            answerSetsArr.add(answerSets);
        }

        mHandler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        new MaterialDialog.Builder(mContext)
                                .items(R.array.option_head)
                                .itemsCallback(new MaterialDialog.ListCallback() {
                                    @Override
                                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .show();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }
        };
    }

    @Override
    protected void initView() {
        setToolbarBackEnable("评论");
        recyclerViewAdapter = new CommentRecyclerViewAdapter(mHandler);
        recyclerviewCommentpage.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerviewCommentpage.setAdapter(recyclerViewAdapter);
        recyclerviewCommentpage.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

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
        return super.onOptionsItemSelected(item);
    }


}
