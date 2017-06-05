package us.xingkong.jueqian.module.Forum.NewPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import id.zelory.compressor.Compressor;
import us.xingkong.jueqian.JueQianAPP;
import us.xingkong.jueqian.base.BasePresenterImpl;
import us.xingkong.jueqian.bean.ForumBean.BombBean.Question;
import us.xingkong.jueqian.bean.ForumBean.BombBean._User;
import us.xingkong.jueqian.module.main.MainActivity;


public class NewPresenter extends BasePresenterImpl implements NewContract.Presenter {

    private final NewContract.View mView;

    public NewPresenter(NewContract.View view) {
        mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void addQuestion(final Context context, String title, final String content, String tag1, String tag2, final Handler handler) {
        _User user = BmobUser.getCurrentUser(context, _User.class);
        if (user != null) {
            final Question question = new Question();
            question.setUser(user);
            question.setMcontent(content);
            question.setMtitle(title);
            question.setTAG1_ID(tag1);
            question.setTAG2_ID(tag2);
            question.setState(0);
            question.setAnswer_count(0);
            question.setShouzanFlag(0);
            question.save(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    mView.showToast("添加成功");
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("newQuestionID", question.getObjectId());
                    msg.obj = question.getObjectId();
                    msg.what = 1;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }

                @Override
                public void onFailure(int i, String s) {
                    mView.showToast("网络状态有点差哦，添加新问题失败了");
                    MainActivity.instance.finish();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public void addMyQuestion(final Context context, String myquestionID, final Handler handler) {
        _User user = BmobUser.getCurrentUser(context, _User.class);
        Question question = new Question();
        question.setObjectId(myquestionID);
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.add(question);
        user.setQuestions(bmobRelation);
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
//                handler.sendEmptyMessage(3);
            }

            @Override
            public void onFailure(int i, String s) {
                mView.showToast("网络连接超时，保存到我的问题失败了呀");
//                MainActivity.instance.finish();
//                Intent intent = new Intent(context, MainActivity.class);
//                context.startActivity(intent);
//                NewActivity.close.finish();
            }
        });
    }

    @Override
    public int upLoadImage(final Context context, String content, final Handler handler) {
        Pattern p = Pattern.compile("\\/[^ .]+.(gif|jpg|jpeg|png)");
        Matcher matcher = p.matcher(content);
        final ArrayList<String> arrayList = new ArrayList<>();
        while (matcher.find()) {
            arrayList.add(matcher.group());
        }
        if (arrayList.size() != 0) {
            String files[] = new String[arrayList.size()];
            for (int i = 0; i < arrayList.size(); i++) {
                File file = new File(arrayList.get(i));
                File zipFile = Compressor.getDefault(JueQianAPP.getAppContext()).compressToFile(file);
                files[i] = zipFile.getPath();
            }
            BmobFile.uploadBatch(context, files, new UploadBatchListener() {
                @Override
                public void onSuccess(List<BmobFile> list, List<String> list1) {
                    if (list1.size() == arrayList.size()) {
                        Message msg = new Message();
                        msg.obj = list1;
                        msg.what = 2;
                        handler.sendMessage(msg);
                    }

                }

                @Override
                public void onProgress(int i, int i1, int i2, int i3) {

                }

                @Override
                public void onError(int i, String s) {
                    mView.showToast("插图上传失败了哦！");
                    MainActivity.instance.finish();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    NewActivity.close.finish();
                }
            });
            return 1;
        }
        return 0;
    }

    @Override
    public void saveURL(List<String> imageFiles, String newQuestionID, final Context context, final Handler handler) {
        Question question = new Question();
        question.setImageFiles(imageFiles);
        question.update(context, newQuestionID, new UpdateListener() {
            @Override
            public void onSuccess() {
//                handler.sendEmptyMessage(4);
                MainActivity.instance.finish();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                NewActivity.close.finish();
            }

            @Override
            public void onFailure(int i, String s) {
                mView.showToast("网络状态有点差哦,上传图片失败了");
                MainActivity.instance.finish();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                NewActivity.close.finish();
            }
        });
    }

    @Override
    public void changeURL(String newQuestionID, Context mContext, final Handler handler, final String newQuestionContent) {
        final List<String> list = new ArrayList<>();
        BmobQuery<Question> query = new BmobQuery<>();
        query.getObject(mContext, newQuestionID, new GetListener<Question>() {
            @Override
            public void onSuccess(Question question) {
                for (int i = 0; i < question.getImageFiles().size(); i++) {
                    list.add(question.getImageFiles().get(i));
                }
                Pattern p = Pattern.compile("\\/[^ .]+.(gif|jpg|jpeg|png)");
                Matcher matcher = p.matcher(newQuestionContent);
                while (matcher.find()) {
                    matcher.group().replace(matcher.group(), list.get(0));
                    list.remove(0);
                }
                Message msg = new Message();
                msg.obj = newQuestionContent;
                msg.what = 4;
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }
}
