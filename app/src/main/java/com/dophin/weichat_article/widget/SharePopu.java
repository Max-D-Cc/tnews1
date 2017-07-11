package com.dophin.weichat_article.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.utils.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class SharePopu extends PopupWindow implements OnClickListener {

	private Context context;
	private String imgUrl;
	private String url;
	private String content;
	private String title;
	private View view;
	private UMImage image;
	private UMWeb web;

	/**
	 * 
	 * @param context
	 * @param
	 * @param title
	 * @param content
	 * @param url
	 */
	public SharePopu(Context context, UMImage image, String title,
					 String content, String url, String imgUrl) {
		super(context);
		this.context = context;
		this.content = content;
		this.image = image;
		this.title = title;
		this.url = url;
		this.context = context;
		this.imgUrl = imgUrl;
		showSharePopu();
	}

	private void showSharePopu() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.popu_publicshare, null);
		TextView cancel = (TextView) view.findViewById(R.id.card_p_cancel1);
		TextView circle = (TextView) view.findViewById(R.id.card_p_circle1);
		TextView wx = (TextView) view.findViewById(R.id.card_p_wx1);
		TextView qq = (TextView) view.findViewById(R.id.card_p_qq1);
		TextView qqzone = (TextView) view.findViewById(R.id.card_p_qqzone1);
		circle.setOnClickListener(this);
		wx.setOnClickListener(this);
		qq.setOnClickListener(this);
		qqzone.setOnClickListener(this);
		cancel.setOnClickListener(this);
		// 添加布局
		this.setContentView(view);
		// 设置SharePopupWindow宽度
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SharePopupWindow高度
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置setFocusable可获取焦点
		this.setTouchable(true);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		// 设置setFocusable动画风格
		this.setAnimationStyle(R.style.popupAnimation);
		// 画背景
		ColorDrawable dw = new ColorDrawable(0x00000000); // 设置背景
		this.setBackgroundDrawable(dw);
		web = new UMWeb(url);
		web.setTitle(title);
		web.setDescription(content);
		web.setThumb(image);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.card_p_cancel1:
			this.dismiss();
			break;
		case R.id.card_p_wx1:
			new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN).withMedia(web).setCallback(uumShareListener).share();
			this.dismiss();
			break;
		case R.id.card_p_circle1:
			new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).withMedia(web).setCallback(uumShareListener).share();
			this.dismiss();
			break;
		case R.id.card_p_qq1:
			new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.QQ).withMedia(web).setCallback(uumShareListener).share();
			this.dismiss();
			break;
		case R.id.card_p_qqzone1:
			new ShareAction((Activity) context).setPlatform(SHARE_MEDIA.QZONE).withMedia(web).setCallback(uumShareListener).share();
			this.dismiss();
			break;
		default:
			break;
		}
	}

	UMShareListener uumShareListener = new UMShareListener() {
		@Override
		public void onStart(SHARE_MEDIA share_media) {
			ToastUtils.getInstance().ToastShow(context,"开始分享");
		}

		@Override
		public void onResult(SHARE_MEDIA share_media) {
			ToastUtils.getInstance().ToastShow(context,"分享成功");
		}

		@Override
		public void onError(SHARE_MEDIA share_media, Throwable throwable) {
			ToastUtils.getInstance().ToastShow(context,"分享失败");
		}

		@Override
		public void onCancel(SHARE_MEDIA share_media) {
			ToastUtils.getInstance().ToastShow(context,"取消分享");
		}
	};

}
