package com.dophin.weichat_article.mine.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.http.HttpMethods;
import com.dophin.weichat_article.http.ProgressSubscriber;
import com.dophin.weichat_article.http.SubscriberOnNextListener;
import com.dophin.weichat_article.mine.bean.Statue;
import com.dophin.weichat_article.mine.crop.SettingImageUtils;
import com.dophin.weichat_article.utils.ImageLoader;
import com.dophin.weichat_article.utils.SPUtils;
import com.dophin.weichat_article.utils.StringUtils;
import com.dophin.weichat_article.utils.ToastUtils;
import com.dophin.weichat_article.widget.CircleImageView;
import com.dophin.weichat_article.widget.TitleBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class UserInfoActivity extends BaseActivity {


    private static final int REQUEST_IMAGE = 10;
    @BindView(R.id.update_img)
    RelativeLayout updateImg;
    @BindView(R.id.update_name)
    RelativeLayout updateName;
    @BindView(R.id.update_phone)
    RelativeLayout updatePhone;
    @BindView(R.id.update_qq)
    RelativeLayout updateQq;
    @BindView(R.id.update_zfb)
    RelativeLayout updateZfb;
    @BindView(R.id.update_zfbname)
    RelativeLayout updateZfbname;
    @BindView(R.id.update_pwd)
    RelativeLayout updatePwd;
    @BindView(R.id.ui_photo)
    CircleImageView uiPhoto;
    @BindView(R.id.ui_id)
    TextView uiId;
    @BindView(R.id.ui_name)
    TextView uiName;
    @BindView(R.id.ui_phone)
    TextView uiPhone;
    @BindView(R.id.ui_qq)
    TextView uiQq;
    @BindView(R.id.ui_zfb)
    TextView uiZfb;
    @BindView(R.id.ui_zfbname)
    TextView uiZfbname;
    private SubscriberOnNextListener<Statue> statueSubscriberOnNextListener;
    private String orderId;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        orderId = (String) SPUtils.getParam(this, "user", "orderid", "");
    }

    @Override
    public void initViews() {
        new TitleBuilder(this).setTitleText("账户绑定设置").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
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
//        updatePwd.setVisibility(View.GONE);

        statueSubscriberOnNextListener = new SubscriberOnNextListener<Statue>() {
            @Override
            public void onNext(Statue statue) {
                Log.e("cai","1");
                if (statue.getSTATUS() == 1){
                    Log.e("cai","2");
                    ToastUtils.getInstance().ToastShow(UserInfoActivity.this,"修改成功");
                    SPUtils.setParam(UserInfoActivity.this,"user","img",statue.getIMG());
                    ImageLoader.displayImage(UserInfoActivity.this,statue.getIMG(),uiPhoto,R.mipmap.denglutouxiang);
                }else{
                    ToastUtils.getInstance().ToastShow(UserInfoActivity.this,"修改失败");
                }
            }
        };
    }

    @Override
    public void addActivity() {

    }
    
    

    @Override
    protected void onStart() {
        super.onStart();

        String nickName = (String) SPUtils.getParam(this, "user", "nickname", "");
        String zfb = (String) SPUtils.getParam(this, "user", "zfb", "");
        String qq = (String) SPUtils.getParam(this, "user", "qq", "");
        String tel = (String) SPUtils.getParam(this, "user", "tel", "");
        String img = (String) SPUtils.getParam(this, "user", "img", "");
        String zfbName = (String) SPUtils.getParam(this, "user", "zfbname", "");
        String username = (String) SPUtils.getParam(this, "user", "username", "");
        String userId = (String) SPUtils.getParam(this, "user", "userid", "");

        ImageLoader.displayImage(this,img,uiPhoto,R.mipmap.denglutouxiang);
        uiId.setText(String.valueOf(userId));
        if (!StringUtils.isEmptyString(nickName)) {
            uiName.setText(nickName);
        }

        if (!StringUtils.isEmptyString(zfb)) {
            uiZfb.setText(zfb);
        }

        if (!StringUtils.isEmptyString(zfbName)) {
            uiZfbname.setText(zfbName);
        }

        if (!StringUtils.isEmptyString(qq)) {
            uiQq.setText(qq);
        }

        if (!StringUtils.isEmptyString(tel)) {
            uiPhone.setText(tel);
        }
    }

    @OnClick({R.id.ui_photo, R.id.update_img, R.id.update_name, R.id.update_phone, R.id.update_qq, R.id.update_zfb, R.id.update_zfbname, R.id.update_pwd})
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.update_img:
                SettingImageUtils.showImagePickDialog(UserInfoActivity.this);
//                showPopu();

//                Intent intentC = new Intent(UserInfoActivity.this, MultiImageSelectorActivity.class);
//// 是否显示调用相机拍照
//                intentC.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
////                intentC.putExtra(MultiImageSelectorActivity.)
//// 最大图片选择数量
//                intentC.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
//// 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
//                intentC.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);

                
//                startActivityForResult(intentC, REQUEST_IMAGE);
                break;
            case R.id.update_name:
                Intent intent = new Intent(this,UpdateUserInfoActivity.class);
                intent.putExtra("type","name");
                startActivity(intent);
                break;
            case R.id.update_phone:
                Intent intent1 = new Intent(this,UpdateUserInfoActivity.class);
                intent1.putExtra("type","phone");
                startActivity(intent1);
                break;
            case R.id.update_qq:
                Intent intent2 = new Intent(this,UpdateUserInfoActivity.class);
                intent2.putExtra("type","qq");
                startActivity(intent2);
                break;
            case R.id.update_zfb:
                Intent intent3 = new Intent(this,UpdateUserInfoActivity.class);
                intent3.putExtra("type","zfb");
                startActivity(intent3);
                break;
            case R.id.update_zfbname:
                Intent intent4 = new Intent(this,UpdateUserInfoActivity.class);
                intent4.putExtra("type","zfbname");
                startActivity(intent4);
                break;
            case R.id.update_pwd:
                Intent intent5 = new Intent(this,UpdateUserInfoActivity.class);
                intent5.putExtra("type","pwd");
                startActivity(intent5);
                break;
        }
        overridePendingTransition(R.anim.right_center, R.anim.center_left);
    }


    private Button confirmButton;
    private Button cancleButton;
    private PopupWindow popupWindow;
    private View popupWindowView;
    private String yname;

    private static String IMAGE_FILE_LOCATION = null;
    private File cameraFile;// 照相机拍照的图片
    private String name;
    private CircleImageView pphoto;
    private String nice;
    private String imim;


    private static final int INTENT_CAMERA = 1;
    private static final int INTENT_PICTURE = 2;
    private static final int INTENT_CROP = 3;
    private String img;



    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 弹出选择动画
     */
    private void showPopu() {

        if (!isExitsSdcard()) {
            Toast.makeText(UserInfoActivity.this, "SD卡不存在，不能更改头像",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Random random = new Random();
        int i = random.nextInt(20000);
        cameraFile = new File(getExternalFilesDir(IMAGE_FILE_LOCATION), i
                + name + System.currentTimeMillis() + ".jpg");
        if (cameraFile == null && !cameraFile.exists()) {// 如果文件存在就不在创建
            cameraFile.getParentFile().mkdirs();
        }

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        popupWindowView = inflater.inflate(R.layout.popupwindow, null);
        popupWindow = new PopupWindow(popupWindowView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置PopupWindow的弹出和消失效果
        popupWindow.setAnimationStyle(R.style.popupAnimation);
        confirmButton = (Button) popupWindowView
                .findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("image/*");
                startActivityForResult(intent1, INTENT_PICTURE);
                popupWindow.dismiss();
            }
        });
        cancleButton = (Button) popupWindowView.findViewById(R.id.cancleButton);
        Button pz = (Button) popupWindowView.findViewById(R.id.pz);
        pz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, INTENT_CAMERA);
                popupWindow.dismiss();
            }
        });
        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(confirmButton, Gravity.CENTER, 0, 0);
        popupWindowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void startImageZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, INTENT_CROP);
    }

    private Uri saveUrl(Bitmap bitmap) {
        File tmDir = new File(Environment.getExternalStorageDirectory()
                + "/com.zk");
        if (!tmDir.exists()) {
            tmDir.mkdir();
        }
        File img = new File(tmDir.getAbsolutePath() + "eza.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
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

    private Uri convertUri(Uri uri) {
        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return saveUrl(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File saveJPGE_After(Bitmap bitmap, File cameraFile2) {
        // File file = new File(cameraFile2);
        try {
            FileOutputStream out = new FileOutputStream(cameraFile2);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cameraFile2;
    }


    private Bitmap bm;

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == INTENT_CAMERA) {
//            if (data == null) {
//                return;
//            } else {
//                Bundle bundle = data.getExtras();
//                if (bundle != null) {
//                    Bitmap bm = bundle.getParcelable("data");
//                    Uri uri = saveUrl(bm);
//                    startImageZoom(uri);
//                }
//            }
//        } else if (requestCode == INTENT_PICTURE) {
//            if (data == null) {
//                return;
//            }
//            Uri uri;
//            uri = data.getData();
//            Uri fileUri = convertUri(uri);
//            startImageZoom(fileUri);
//        } else if (requestCode == INTENT_CROP) {
//            if (data == null) {
//                return;
//            }
//            Bundle bundle = data.getExtras();
//            if (bundle != null) {
//                bm = bundle.getParcelable("data");
////				pphoto.setImageBitmap(bm);
////                File file = saveJPGE_After(bm, cameraFile);
//                sendImage(bm);
////                uiPhoto.setImageBitmap(bm);
//            }
//        }
//    }

    Uri imageUri;
    File imageFile;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SettingImageUtils.REQUEST_CODE_FROM_ALBUM: {

                if (resultCode == RESULT_CANCELED) {   //取消操作
                    return;
                }

                Uri imageUri = data.getData();
                if (imageUri != null) {
                    SettingImageUtils.copyImageUri(this, imageUri);
                    SettingImageUtils.cropImageUri(this, SettingImageUtils.getCurrentUri(), 200, 200);
                }
                break;
            }
            case SettingImageUtils.REQUEST_CODE_FROM_CAMERA: {

                if (resultCode == RESULT_CANCELED) {     //取消操作
//                    if(SettingImageUtils.getCurrentUri()!=null){
//                        SettingImageUtils.deleteImageUri(this, SettingImageUtils.getCurrentUri());   //删除Uri
//                    }
                    return;
                }
                if (SettingImageUtils.getCurrentUri() != null) {
                    SettingImageUtils.cropImageUri(this, SettingImageUtils.getCurrentUri(), 200, 200);
                }
                break;
            }
            case SettingImageUtils.REQUEST_CODE_CROP: {

                if (resultCode == RESULT_CANCELED) {     //取消操作
                    return;
                }

                imageUri = SettingImageUtils.getCurrentUri();
                if (imageUri != null) {
                    imageFile = new File(imageUri.getPath());
//                    uploadPhoto(imageFile);
//                    BitmapFactory.
                    Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath());
                    sendImage(bitmap);
                }
                break;
            }
            default:
                break;
        }

    }



    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 500, 500);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_IMAGE){
//            if(resultCode == RESULT_OK){
//                // 获取返回的图片列表
//                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
//                // 处理你自己的逻辑 ....
//                String s = path.get(0);
//                Log.e("cai","imgpath: "+ s);
//                File file = new File(s);
//                startImageZoom(Uri.fromFile(file));
//            }
//        }else if(requestCode == INTENT_CROP){
//            if (data == null) {
//                return;
//            }
//            Bundle bundle = data.getExtras();
//            if (bundle != null) {
//                bm = bundle.getParcelable("data");
////				pphoto.setImageBitmap(bm);
////                File file = saveJPGE_After(bm, cameraFile);
////                getSmallBitmap(bm);
//                sendImage(bm);
////                uiPhoto.setImageBitmap(bm);
//            }
//        }
//    }

    private void sendImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] bytes = stream.toByteArray();
        img = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        Log.e("cai","3");
//        Bitmap bmt = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        HttpMethods.getInstance().getUpdateImg(new ProgressSubscriber<Statue>(statueSubscriberOnNextListener,UserInfoActivity.this),orderId,img);
//        UploadTxThread txThread = new UploadTxThread();
//        Thread thread = new Thread(txThread);
//        thread.start();
    }



}
