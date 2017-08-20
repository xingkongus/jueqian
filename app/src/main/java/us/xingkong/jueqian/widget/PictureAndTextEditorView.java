package us.xingkong.jueqian.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

/**
 * Created by Jying on 2017/5/18.
 */

public class PictureAndTextEditorView extends android.support.v7.widget.AppCompatEditText {
    private final String TAG = "PATEditorView";
    private Context mContext;
//    private List<String> mContentList;

    public static final String mBitmapTag = "☆";
    private String mNewLineTag = "\n";

    public PictureAndTextEditorView(Context context) {
        super(context);
        init(context);
    }

    public PictureAndTextEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PictureAndTextEditorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
//        mContentList = getmContentList();
//        insertData();
    }

    /**
     * 设置数据
     */
//    private void insertData() {
//        if (mContentList.size() > 0) {
//            for (String str : mContentList) {
//                if (str.indexOf(mBitmapTag) != -1) {//判断是否是图片地址
//                    String path = str.replace(mBitmapTag, "");//还原地址字符串
//                    Bitmap bitmap = getSmallBitmap(path, 200,360);
//                    //插入图片
//                    insertBitmap(path, bitmap);
//                } else {
//                    //插入文字
//                    SpannableString ss = new SpannableString(str);
//                    append(ss);
//                }
//            }
//        }
//    }

    /**
     * 插入图片
     *
     * @param bitmap
     * @param path
     * @return
     */
    private SpannableString insertBitmap(String path, Bitmap bitmap) {
        Editable edit_text = getEditableText();
        int index = getSelectionStart(); // 获取光标所在位置
        //插入换行符，使图片单独占一行
        SpannableString newLine = new SpannableString("\n");
        edit_text.insert(index, newLine);//插入图片前换行
        // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
        path = "<br /><img src=\"" + path + "\" /><br />";
        //"<img src=\''+*+"\">，
        SpannableString spannableString = new SpannableString(path);
        // 根据Bitmap对象创建ImageSpan对象
        ImageSpan imageSpan = new ImageSpan(mContext, bitmap);
        // 用ImageSpan对象替换你指定的字符串
        spannableString.setSpan(imageSpan, 0, path.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 将选择的图片追加到EditText中光标所在位置
        if (index < 0 || index >= edit_text.length()) {
            edit_text.append(spannableString);
        } else {
            edit_text.insert(index, spannableString);
        }
        edit_text.insert(index+path.length(), "\n");//插入图片后换行
        return spannableString;
    }


    /**
     * 插入图片
     *
     * @param path
     */
    public void insertBitmap(String path) {
        Bitmap bitmap = getSmallBitmap(path, 480,800);
        insertBitmap(path, bitmap);
    }

    /**
     * 用集合的形式获取控件里的内容
     *
     * @return
     */
//    public List<String> getmContentList() {
//        if (mContentList == null) {
//            mContentList = new ArrayList<String>();
//        }
//        String content = getText().toString().replaceAll(mNewLineTag, "");
//        if (content.length() > 0 && content.contains(mBitmapTag)) {
//            String[] split = content.split("☆");
//            mContentList.clear();
//            for (String str : split) {
//                mContentList.add(str);
//            }
//        } else {
//            mContentList.add(content);
//        }
//
//        return mContentList;
//    }

    /**
     * 设置显示的内容集合
     *
     * @param contentList
     */
//    public void setmContentList(List<String> contentList) {
//        if (mContentList == null) {
//            mContentList = new ArrayList<>();
//        }
//        this.mContentList.clear();
//        this.mContentList.addAll(contentList);
//        insertData();
//    }

    float oldY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldY = event.getY();
                requestFocus();
                break;
            case MotionEvent.ACTION_MOVE:
                float newY = event.getY();
                if (Math.abs(oldY - newY) > 20) {
                    clearFocus();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

//        // 计算图片的缩放值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int w_width = (int) (w_screen*0.9);
        int b_width = bitmap.getWidth();
        int b_height = bitmap.getHeight();
        int w_height =w_width * b_height / b_width;
        bitmap = Bitmap.createScaledBitmap(bitmap, w_width, w_height, false);

//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//        //计算宽、高缩放率
//        float scanleWidth = (float)reqWidth/width;
//        float scanleHeight = (float)reqHeight/height;
//        //创建操作图片用的matrix对象 Matrix
//        Matrix matrix = new Matrix();
//        // 缩放图片动作
//        matrix.postScale(scanleWidth,scanleHeight);
//        //旋转图片 动作
//        //matrix.postRotate(45);
//        // 创建新的图片Bitmap
//        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);

        return bitmap;
    }

    //计算图片的缩放值
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
