package com.dophin.weichat_article.mine.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.base.Constants;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.bean.RedPackage;
import com.dophin.weichat_article.utils.DateUtils;
import com.dophin.weichat_article.utils.EncodingUtils;
import com.dophin.weichat_article.utils.ImageLoader;
import com.dophin.weichat_article.utils.MyAnimation;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.SharePerUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.CircleImageView;
import com.dophin.weichat_article.widget.TitleBuilder;
import com.dophin.weichat_article.widget.WaringDialog;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedPackageActivity extends BaseActivity {


    @BindView(R.id.rp_open)
    ImageView rpOpen;
    @BindView(R.id.rp_time)
    TextView rpTime;
    private TimeCount timeCount;
    private boolean isOpen = false;
    private SubscriberOnNextListener listener;
    private String orderId;
    private String yqm;
    private String appurl;
    private String img;
    private Bitmap imgBitmap;
    private boolean isShare = false;

    private int score;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (imgBitmap != null){
                        ToastUtils.getInstance().ToastShow(RedPackageActivity.this,"正在分享中...");
                        try {
                            View view = View.inflate(RedPackageActivity.this,R.layout.share_layout,null);
                            TextView shareId = (TextView) view.findViewById(R.id.share_id);
                            TextView shareName = (TextView) view.findViewById(R.id.share_name);
                            TextView shareMoney = (TextView) view.findViewById(R.id.share_money);
                            CircleImageView shareImg = (CircleImageView) view.findViewById(R.id.share_img);
                            ImageView code = (ImageView) view.findViewById(R.id.share_code);
                            Bitmap qrCode = EncodingUtils.createQRCode( Constants.SHAREURL+ "?yqm=" + yqm, 300, 300, null);
//                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.denglutouxiang);
                            ImageLoader.displayImage(RedPackageActivity.this,img,shareImg,R.mipmap.denglutouxiang);
                            code.setImageBitmap(qrCode);
                            shareId.setText("ID: " + idid);
                            shareMoney.setText(String.valueOf(score));
                            shareName.setText(nickName);
                            Bitmap bitmap = convertViewToBitmap(view);
                            ArrayList<Uri> uris = new ArrayList<Uri>();
                            uris.add(saveUrl(bitmap, "redshare1.jpg"));

                            Intent intent = new Intent();
                            ComponentName comp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareToTimeLineUI");
                            intent.setComponent(comp);
                            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                            intent.setType("image/*");
                            intent.putExtra("Kdescription", "《悦目精选》注册就送现金红包啦，一边阅读，一边赚钱/呲牙/呲牙让知识从此变为财富，让人脉变为钱脉/礼物/礼物" + Constants.SHAREURL+"?yqm=" + yqm);
                            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                            startActivity(intent);
                            isShare = true;
                        } catch (Exception e) {
                            ToastUtils.getInstance().ToastShow(RedPackageActivity.this,"分享失败");
                        }

                    }

                    break;
            }
        }
    };
    private String nickName;
    private String idid;

    @Override
    protected void onResume() {
        super.onResume();
        if (isShare){
            isShare = false;
            if (listener != null){
                HttpMethods.getInstance().getRedPackage(new ProgressSubscriber<RedPackage>(listener,RedPackageActivity.this,1), orderId,String.valueOf(score),String.valueOf(System.currentTimeMillis()));
            }
        }
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_red_package);
        ButterKnife.bind(this);
    }

    public static String formatData(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(date);
        return time;
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("拆红包").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish2Activity();
            }
        }).build();

        String recordTime = SharePerUtils.getString(this, "recordTime", "");
        String curDate = formatData(new Date());
        if (!recordTime.equals(curDate)) {
            SharePerUtils.putString(this, "recordTime", curDate);
            SharePerUtils.putInt(this, "rpTimes", 0);
        }
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {
        img = (String) SPUtils.getParam(this, "user", "img", "");
        appurl = SharePerUtils.getString(this, "appurl", "");
        yqm = (String) SPUtils.getParam(RedPackageActivity.this, "user", "code", "");
        orderId = (String) SPUtils.getParam(this, "user", "orderid", "");
        nickName = (String) SPUtils.getParam(this, "user", "nickname", "");
        idid = (String) SPUtils.getParam(this, "user", "idid", "");

        listener = new SubscriberOnNextListener<RedPackage>() {
            @Override
            public void onNext(RedPackage redPackage) {
                if (redPackage.getSTATUS() == 1) {
                    SharePerUtils.putLong(RedPackageActivity.this, "getTime", System.currentTimeMillis());
                    ToastUtils.getInstance().ToastShow(RedPackageActivity.this, "恭喜您获得" + score + "星币");
                } else if (redPackage.getSTATUS() == 3) {
                    ToastUtils.getInstance().ToastShow(RedPackageActivity.this, "今日领取次数已达上限,请明日再来");
                }
            }
        };

        long curTime = System.currentTimeMillis();
        long redTime = SharePerUtils.getLong(RedPackageActivity.this, "redTime", 0);
        if (redTime != 0 && redTime - curTime > 0) {
            long syTime = redTime - curTime;

            timeCount = new TimeCount(syTime, 1000);
            timeCount.start();
        } else {
            rpTime.setText("当前可以领取红包");
            isOpen = true;
        }
    }

    @Override
    public void addActivity() {

    }

    @OnClick(R.id.rp_open)
    public void onClick() {
        int rpTimes = SharePerUtils.getInt(this, "rpTimes", 0);




        if (rpTimes < 6) {



            if (isOpen) {
                MyAnimation animation = new MyAnimation();
                animation.setRepeatCount(0);
                rpOpen.startAnimation(animation);
                isOpen = false;
                SharePerUtils.putInt(this, "rpTimes", (rpTimes + 1));
                SharePerUtils.putLong(RedPackageActivity.this, "redTime", (System.currentTimeMillis() + 3600000));
//                ToastUtils.getInstance().ToastShow(RedPackageActivity.this, "恭喜您获得50星币");
                final WaringDialog dialog = new WaringDialog(RedPackageActivity.this);
                dialog.setCanceledOnTouchOutside(false);
                Random random = new Random();
                int i = random.nextInt(10);
                score = i + 5;
                dialog.setTitle("红包奖励").setContent("恭喜您获得"+ score +"星币,分享后可领取").setDisAgree("取消").setOnDisAgreeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }).setAgree("分享领取").setOnAgreeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        final WaringDialog dialog1 = new WaringDialog(RedPackageActivity.this);
                        dialog1.setTitle("分享领奖励").setContent("快快分享吧").setAgree("朋友圈分享").setOnAgreeListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                                if (TextUtils.isEmpty(img)){
                                    ToastUtils.getInstance().ToastShow(RedPackageActivity.this,"正在分享中...");
                                    try {
                                        View view = View.inflate(RedPackageActivity.this,R.layout.share_layout,null);
                                        TextView shareId = (TextView) view.findViewById(R.id.share_id);
                                        TextView shareName = (TextView) view.findViewById(R.id.share_name);
                                        TextView shareMoney = (TextView) view.findViewById(R.id.share_money);
                                        CircleImageView shareImg = (CircleImageView) view.findViewById(R.id.share_img);
                                        ImageView code = (ImageView) view.findViewById(R.id.share_code);
                                        Bitmap qrCode = EncodingUtils.createQRCode( Constants.SHAREURL+ "?yqm=" + yqm, 300, 300, null);
//                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.denglutouxiang);
                                        ImageLoader.displayImage(RedPackageActivity.this,img,shareImg,R.mipmap.denglutouxiang);
                                        code.setImageBitmap(qrCode);
                                        shareId.setText("ID: " + idid);
                                        shareMoney.setText(String.valueOf(score));
                                        shareName.setText(nickName);
                                        Bitmap bitmap = convertViewToBitmap(view);
                                        ArrayList<Uri> uris = new ArrayList<Uri>();
                                        uris.add(saveUrl(bitmap, "redshare1.jpg"));

                                        Intent intent = new Intent();
                                        ComponentName comp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareToTimeLineUI");
                                        intent.setComponent(comp);
                                        intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                                        intent.setType("image/*");
                                        intent.putExtra("Kdescription", "《悦目精选》注册就送现金红包啦，一边阅读，一边赚钱/呲牙/呲牙让知识从此变为财富，让人脉变为钱脉/礼物/礼物" + Constants.SHAREURL+"?yqm=" + yqm);
                                        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                                        startActivity(intent);
                                        isShare = true;
                                    } catch (Exception e) {
                                        ToastUtils.getInstance().ToastShow(RedPackageActivity.this,"分享失败");
                                    }
                                }else{
                                    new Thread(){
                                        @Override
                                        public void run() {
                                            super.run();
                                            imgBitmap = GetLocalOrNetBitmap(img);
                                            Message msg = handler.obtainMessage();
                                            msg.what =1;
                                            handler.sendMessage(msg);
                                        }
                                    }.start();
                                }
                            }
                        }).setDisAgree("微信分享").setOnDisAgreeListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                                try {
                                    Intent intent1 = new Intent();
                                    ComponentName comp1 = new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareImgUI");
                                    intent1.setComponent(comp1);
                                    intent1.setAction(Intent.ACTION_SEND);
                                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent1.setType("text/*");
                                    intent1.putExtra(Intent.EXTRA_TEXT, "《悦目精选》注册就送现金红包啦，一边阅读，一边赚钱/呲牙/呲牙让知识从此变为财富，让人脉变为钱脉/礼物/礼物" + Constants.SHAREURL+"?yqm=" + yqm);
                                    startActivity(intent1);

                                    isShare = true;
                                }catch (Exception e){
                                    ToastUtils.getInstance().ToastShow(RedPackageActivity.this,"分享失败");
                                    e.printStackTrace();
                                }
                            }
                        }).show();
//                        HttpMethods.getInstance().getRedPackage(new ProgressSubscriber<RedPackage>(listener, RedPackageActivity.this, 1), orderId,score,String.valueOf(System.currentTimeMillis()));
                    }
                }).show();
                timeCount = new TimeCount(3600 * 1000, 1000);
                timeCount.start();
            } else {
                ToastUtils.getInstance().ToastShow(RedPackageActivity.this, "现在不可以领取红包");
            }
        } else {
            rpTime.setText("红包次数上限");
            ToastUtils.getInstance().ToastShow(RedPackageActivity.this, "您今天已达红包领取次数上限");
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            rpTime.setText("现在可以领取红包");
//            regGetCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            rpTime.setText("距离下次拆红红包时间：" + getTimeFromInt((int) (millisUntilFinished)));


        }
    }

    /**
     * 时间的处理
     *
     * @param time
     * @return
     */
    public static String getTimeFromInt(int time) {

        if (time <= 0) {
            return "0:00";
        }
        int secondnd = (time / 1000) / 60;
        int million = (time / 1000) % 60;
        String f = String.valueOf(secondnd);
        String m = million >= 10 ? String.valueOf(million) : "0"
                + String.valueOf(million);
        return f + "分" + m + "秒";
    }

    @Override
    protected void onDestroy() {
        if (timeCount != null) {
            timeCount.cancel();
        }
        rpOpen.clearAnimation();
        super.onDestroy();
    }

    private Uri saveUrl(Bitmap bitmap, String str) {
        File tmDir = new File(Environment.getExternalStorageDirectory()
                + "/com.yuemu");
        if (!tmDir.exists()) {
            tmDir.mkdir();
        }
        File img = new File(tmDir.getAbsolutePath() + str);
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Bitmap drawTextToBitmap(Context gContext, Bitmap bitmap, String gText) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
//		Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

//		bitmap = scaleWithWH(bitmap, 320 * scale, 480 * scale);

        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.RGB_565;
        }
        int srcWidth = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.WHITE);
        paint.setTextSize((int) (18 * scale));
        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int width = bounds.width();
//        int x = 50;
//        int y = 200;
//		canvas.drawText(gText, x * scale, y * scale, paint);
        canvas.drawText(gText, ((srcWidth - width)/2), srcHeight * 0.30f, paint);
        return bitmap;
    }

    public Bitmap drawTextToBitmap1(Context gContext, Bitmap bitmap, String gText) {
        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;
        android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.RGB_565;
        }
        int srcWidth = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.WHITE);
        paint.setTextSize((int) (18 * scale));
        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int width = bounds.width();
//        int x = 50;
//        int y = 200;
//		canvas.drawText(gText, x * scale, y * scale, paint);
        canvas.drawText(gText, ((srcWidth - width)/2), srcHeight * 0.90f, paint);
        return bitmap;
    }




    private static Bitmap addLogo1(Bitmap src, Bitmap logo,float height) {
        if (src == null) {
            return null;
        }
        if (logo == null) {
            return src;
        }
        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }
        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }
        //logo大小为二维码整体大小的1/5
//        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
//            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight)*0.70f, null);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }

    private static Bitmap addLogo2(Bitmap src, Bitmap logo,float height) {
        if (src == null) {
            return null;
        }
        if (logo == null) {
            return src;
        }
        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();
        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }
        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }
        //logo大小为二维码整体大小的1/5
//        float scaleFactor = srcWidth * 1.0f / 5 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
//            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight)*0.12f, null);
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }
        return bitmap;
    }


    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[2 * 1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    //将网络图片的地址转换成bitmap
    public static Bitmap GetLocalOrNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), 2 * 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 2 * 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 将图片切成圆形
     * @param bitmap
     * @return
     */
    public Bitmap getCircleBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        try {
            Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(circleBitmap);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight()));
            float roundPx = 0.0f;
            // 以较短的边为标准
            if (bitmap.getWidth() > bitmap.getHeight()) {
                roundPx = bitmap.getHeight() / 2.0f;
            } else {
                roundPx = bitmap.getWidth() / 2.0f;
            }
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.WHITE);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            final Rect src = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            canvas.drawBitmap(bitmap, src, rect, paint);
            return circleBitmap;
        } catch (Exception e) {
            return bitmap;
        }
    }


    public Bitmap convertViewToBitmap(View mView) {
//        view.destroyDrawingCache();
//        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//        view.setDrawingCacheEnabled(true);
//        return view.getDrawingCache(true);

        mView.setDrawingCacheEnabled(true);
        //图片的宽度为屏幕宽度，高度为wrap_content
        mView.measure(View.MeasureSpec.makeMeasureSpec(getResources().getDisplayMetrics().widthPixels, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //放置mView
        mView.layout(0, 0, mView.getMeasuredWidth(), mView.getMeasuredHeight());
        mView.buildDrawingCache();
        Bitmap bitmap = mView.getDrawingCache();
        return bitmap;
//        iv.setImageBitmap(bitmap);
    }
}
