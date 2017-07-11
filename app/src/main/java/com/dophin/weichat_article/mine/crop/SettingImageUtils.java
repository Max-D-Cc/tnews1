package com.dophin.weichat_article.mine.crop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liupeng on 16/6/20.
 */

public class SettingImageUtils {
	Context context;
	public static final int REQUEST_CODE_FROM_CAMERA = 5001;
	public static final int REQUEST_CODE_FROM_ALBUM = 5002;
	public static final int REQUEST_CODE_CROP = 5003;
	/**
	 * 存放拍照图片的uri地址
	 */
	private static Uri imageUriFromALBUM;
	private static Uri imageUriFromCamera;

	/**
	 * 记录是处于什么状态：拍照or相册
	 */
	private static int state = 0;

	/**
	 * 显示获取照片不同方式对话框
	 */
	public static void showImagePickDialog(final Activity activity){

		String title = "选择获取图片方式";
		String[] items = new String[]{"拍照","相册"};

		new AlertDialog.Builder(activity)
				.setTitle(title)
				.setItems(items, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
						switch (which){
							case 0:
								state = 1;
								pickImageFromCamera(activity);
								break;
							case 1:
								state = 2;
								pickImageFromAlbum(activity);
								break;
							default:
								break;
						}
					}
				}).setPositiveButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
	}


	/**
	 * 打开本地相册选取图片
	 */
	public static void pickImageFromAlbum(final Activity activity){

		//隐式调用，可能出现多种选择
		/*Intent intent = appnew Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("image*//*");
		activity.startActivityForResult(intent,REQUEST_CODE_FROM_ALBUM);*/


		 Intent intent = new Intent();
		 intent.setAction(Intent.ACTION_PICK);
		 intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		 activity.startActivityForResult(intent,REQUEST_CODE_FROM_ALBUM);
	}


	/**
	 * 打开相机拍照获取图片
	 */
	public static void pickImageFromCamera(final Activity activity){

		imageUriFromCamera = getImageUri(activity);

		Intent intent = new Intent();
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUriFromCamera);    //指定拍照的图片存在imageUriFromCamera下,如果直接在返回时使用getData()获取的是压缩过的Bitmap数据
		activity.startActivityForResult(intent,REQUEST_CODE_FROM_CAMERA);
	}


	/**
	 * 根据指定目录产生一条图片Uri
	 */
	private static Uri getImageUri( Context context){
		String imageName = new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".png";
		File file=context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		return SettingUriUtils.getUriFromFilePath(file,imageName);
	}

	/**
	 * 复制一条Uri，避免因为操作而对原图片产生影响
	 */
	public static void copyImageUri(Activity activity, Uri uri){

		String imageName = new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".png";

		File copyFile=activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		SettingFileUtils.copyfile(SettingUriUtils.getRealFilePath(activity,uri),copyFile,imageName,true);
		imageUriFromALBUM = SettingUriUtils.getUriFromFilePath(copyFile,imageName);
	}

	/**
	 * 删除一条图片Uri
	 */
	public static void deleteImageUri(Context context, Uri uri){
		context.getContentResolver().delete(uri, null, null);
	}


	/**
	 * 裁剪图片返回
	 */
	public static void cropImageUri(Activity activity, Uri uri, int outputX, int outputY){

		Intent intent = new Intent("com.android.camera.action.CROP");

		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection

		activity.startActivityForResult(intent, REQUEST_CODE_CROP);

	}


	/**
	 * 根据状态返回图片Uri
	 */
	public static Uri getCurrentUri(){

		if (state == 1){
			return imageUriFromCamera;
		}
		else if (state == 2){
			return imageUriFromALBUM;
		}
		else return null;
	}
}
