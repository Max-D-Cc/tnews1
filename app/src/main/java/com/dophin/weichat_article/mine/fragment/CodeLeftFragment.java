package com.dophin.weichat_article.mine.fragment;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseFragment;
import com.dophin.weichat_article.base.Constants;
import com.dophin.weichat_article.mine.activity.QrCodeActivity;
import com.dophin.weichat_article.mine.activity.RedPackageActivity;
import com.dophin.weichat_article.utils.EncodingUtils;
import com.dophin.weichat_article.utils.ImageLoader;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.SharePerUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.CircleImageView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CodeLeftFragment extends BaseFragment {


    @BindView(R.id.code_mycode)
    TextView codeMycode;
    @BindView(R.id.code_qrcode)
    TextView codeQrcode;
    @BindView(R.id.code_cricle)
    TextView codeCricle;
    @BindView(R.id.code_weixin)
    TextView codeWeixin;
    @BindView(R.id.code_qq)
    TextView codeQq;
    @BindView(R.id.code_qzone)
    TextView codeQzone;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:

                    break;
            }
        }
    };
    private String appurl;
    private String yqm;
    private UMWeb web;
    private View rootview;
    private String img;
    private String nickName;
    private String idid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_code_left, container, false);
        ButterKnife.bind(this, rootview);
        initData();
        return rootview;
    }


    private void initData() {
        img = (String) SPUtils.getParam(mActivity, "user", "img", "");
        String orderId = (String) SPUtils.getParam(mActivity, "user", "orderid", "");
        nickName = (String) SPUtils.getParam(mActivity, "user", "nickname", "");
        idid = (String) SPUtils.getParam(mActivity, "user", "idid", "");

        appurl = SharePerUtils.getString(mActivity, "appurl", "");

        yqm = (String) SPUtils.getParam(mActivity, "user", "code", "");
        codeMycode.setText("我的邀请码："+ yqm);
        UMImage image = new UMImage(mActivity,R.mipmap.app_icon);
        web = new UMWeb(Constants.SHAREURL + "?qym=" + yqm);
        web.setTitle("悦目精选");
        web.setDescription("来《悦目精选》注册就送现金红包啦，一边阅读，一边赚钱/呲牙/呲牙让知识从此变为财富，让人脉变为钱脉/礼物/礼物");
        web.setThumb(image);

    }

    @OnClick({R.id.code_qrcode, R.id.code_cricle, R.id.code_weixin, R.id.code_qq, R.id.code_qzone})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.code_qrcode:
                nextToActivity(mActivity, QrCodeActivity.class);
                break;
            case R.id.code_cricle:
                ToastUtils.getInstance().ToastShow(mActivity,"正在分享中...");
                try {
                    View view1 = View.inflate(mActivity,R.layout.share_layout,null);
                    TextView shareId = (TextView) view1.findViewById(R.id.share_id);
                    TextView shareName = (TextView) view1.findViewById(R.id.share_name);
                    TextView shareType = (TextView) view1.findViewById(R.id.share_type);
                    TextView shareMoney = (TextView) view1.findViewById(R.id.share_money);
                    shareType.setVisibility(View.GONE);
                    CircleImageView shareImg = (CircleImageView) view1.findViewById(R.id.share_img);
                    ImageView code = (ImageView) view1.findViewById(R.id.share_code);
                    Bitmap qrCode = EncodingUtils.createQRCode( Constants.SHAREURL+ "?yqm=" + yqm, 300, 300, null);
//                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.denglutouxiang);
                    ImageLoader.displayImage(mActivity,img,shareImg,R.mipmap.denglutouxiang);
                    code.setImageBitmap(qrCode);
                    shareId.setText("ID: " + idid);
                    shareMoney.setText("一个现金红包");
                    shareName.setText(nickName);
                    Bitmap b1 = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.code_share1);
                    Bitmap bitmap1 = drawTextToBitmap(mActivity,b1,"邀请码：" +yqm);
                    Bitmap b2 = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.code_share2);
                    Bitmap b3 = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.code_share3);
                    Bitmap rootBitMap = convertViewToBitmap(view1);
                    ArrayList<Uri> uris = new ArrayList<Uri>();
                    uris.add(saveUrl(rootBitMap, "share1.jpg"));
                    uris.add(saveUrl(b2, "share2.jpg"));
                    uris.add(saveUrl(b3, "share3.jpg"));

                    Intent intent = new Intent();
                    ComponentName comp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareToTimeLineUI");
                    intent.setComponent(comp);
                    intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    intent.setType("image/*");
                    intent.putExtra("Kdescription", "来《悦目精选》注册就送现金红包啦，一边阅读，一边赚钱/呲牙/呲牙让知识从此变为财富，让人脉变为钱脉/礼物/礼物" + Constants.SHAREURL+"?yqm=" + yqm);
                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
                    mActivity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().ToastShow(mActivity,"分享失败");
                }
                break;
            case R.id.code_weixin:

                try {
                    Intent intent1 = new Intent();
                    ComponentName comp1 = new ComponentName("com.tencent.mm","com.tencent.mm.ui.tools.ShareImgUI");
                    intent1.setComponent(comp1);
                    intent1.setAction(Intent.ACTION_SEND);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.setType("text/*");
                    intent1.putExtra(Intent.EXTRA_TEXT, "来《悦目精选》注册就送现金红包啦，一边阅读，一边赚钱/呲牙/呲牙让知识从此变为财富，让人脉变为钱脉/礼物/礼物" + Constants.SHAREURL+"?yqm=" + yqm);
                    startActivity(intent1);
                }catch (Exception e){
                    ToastUtils.getInstance().ToastShow(mActivity,"分享失败");
                    e.printStackTrace();
                }

                break;
            case R.id.code_qq:
                new ShareAction(mActivity).setPlatform(SHARE_MEDIA.QQ).withMedia(web).setCallback(uumShareListener).share();
                break;
            case R.id.code_qzone:
                new ShareAction(mActivity).setPlatform(SHARE_MEDIA.QZONE).withMedia(web).setCallback(uumShareListener).share();
                break;
        }
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
        paint.setColor(Color.YELLOW);
        paint.setTextSize((int) (18 * scale));
        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int width = bounds.width();
//        int x = 50;
//        int y = 200;
//		canvas.drawText(gText, x * scale, y * scale, paint);
        canvas.drawText(gText, ((srcWidth - width)/2), srcHeight * 0.25f, paint);
        return bitmap;
    }

    UMShareListener uumShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            ToastUtils.getInstance().ToastShow(mActivity,"开始分享");
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtils.getInstance().ToastShow(mActivity,"分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ToastUtils.getInstance().ToastShow(mActivity,"分享失败");
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ToastUtils.getInstance().ToastShow(mActivity,"取消分享");
        }
    };


    public Bitmap convertViewToBitmap(View mView) {
//        view.destroyDrawingCache();
//        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//        view.setDrawingCacheEnabled(true);
//        return view.getDrawingCache(true);

        mView.setDrawingCacheEnabled(true);
        //图片的宽度为屏幕宽度，高度为wrap_content
        mView.measure(View.MeasureSpec.makeMeasureSpec(mActivity.getResources().getDisplayMetrics().widthPixels, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //放置mView
        mView.layout(0, 0, mView.getMeasuredWidth(), mView.getMeasuredHeight());
        mView.buildDrawingCache();
        Bitmap bitmap = mView.getDrawingCache();
        return bitmap;
//        iv.setImageBitmap(bitmap);
    }

    /**
     * 创建分享Bitmap
     */
    private Bitmap createBitmap(View view) {
        //自定义ViewGroup，一定要手动调用测量，布局的方法
        view.measure(view.getLayoutParams().width, view.getLayoutParams().height);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        //如果图片对透明度无要求，可以设置为RGB_565
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
