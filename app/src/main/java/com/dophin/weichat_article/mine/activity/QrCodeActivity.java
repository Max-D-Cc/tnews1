package com.dophin.weichat_article.mine.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.base.Constants;
import com.dophin.weichat_article.utils.EncodingUtils;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.TitleBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;


public class QrCodeActivity extends BaseActivity {


    @BindView(R.id.qrcode_img)
    ImageView qrcodeImg;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_qr_code);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("我的二维码").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish2Activity();
            }
        }).build();
    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        String yqm = (String) SPUtils.getParam(this, "user", "code", "");
        Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.app_icon);
        Bitmap qrCode = EncodingUtils.createQRCode(Constants.SHAREURL + "?yqm=" + yqm, 500, 500, logoBitmap);
        qrcodeImg.setImageBitmap(qrCode);
        qrcodeImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Bitmap bitmap = snapShotWithoutStatusBarAndTitle(QrCodeActivity.this);
                if (bitmap !=null){
                    saveImageToGallery(QrCodeActivity.this,bitmap);
                    ToastUtils.getInstance().ToastShow(QrCodeActivity.this,"保存图片成功");
                }else{
                    ToastUtils.getInstance().ToastShow(QrCodeActivity.this,"保存图片失败，请自行截图");
                }
//                qrcodeImg.setImageBitmap(bitmap);
                return true;
            }
        });
    }

    @Override
    public void addActivity() {

    }

    public static Bitmap snapShotWithoutStatusBarAndTitle(Activity activity) {
        try {
        WindowManager wm = (WindowManager) activity.
                getSystemService(Context.WINDOW_SERVICE);
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap= view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;//获取状态栏的高
        int titleBarHeight = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();//获取标题栏的高
        int width = wm.getDefaultDisplay().getWidth();//获取屏幕的宽
        int height = wm.getDefaultDisplay().getHeight();//获取屏幕的高
        int totalBarHeight=titleBarHeight+statusBarHeight;
        Bitmap bm= Bitmap.createBitmap(bitmap, 0, totalBarHeight , width, height - totalBarHeight);
        view.destroyDrawingCache();
        return bm;
        }catch (Exception e){
            return null;
        }
    }


    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        String filePath = Environment.getExternalStorageDirectory().getPath();
        File appDir = new File(filePath+File.separator+"qrCode");
//        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
//        File file = new File(appDir, fileName);

        File picFile = new File(appDir.getPath() + File.separator + fileName);
        try {
            FileOutputStream fos = new FileOutputStream(picFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        context.sendBroadcast(
                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri
                        .parse("file://" + picFile)));
//        // 其次把文件插入到系统图库
//        try {
//            MediaStore.Images.Media.insertImage(context.getContentResolver(),
//                    file.getAbsolutePath(), fileName, null);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
    }

}
