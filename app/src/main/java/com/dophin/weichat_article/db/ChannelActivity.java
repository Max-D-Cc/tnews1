package com.dophin.weichat_article.db;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dophin.weichat_article.base.Application;
import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.widget.TitleBuilder;

import java.util.ArrayList;
/**
 * Ƶ������
 */
public class ChannelActivity extends Activity implements OnItemClickListener {
	public static String TAG = "ChannelActivity";
	/** �û���Ŀ��GRIDVIEW */
	private DragGrid userGridView;
	/** ������Ŀ��GRIDVIEW */
	private OtherGridView otherGridView;
	/** �û���Ŀ��Ӧ���������������϶� */
	DragAdapter userAdapter;
	/** ������Ŀ��Ӧ�������� */
	OtherAdapter otherAdapter;
	/** ������Ŀ�б� */
	ArrayList<ChannelItem> otherChannelList = new ArrayList<ChannelItem>();
	/** �û���Ŀ�б� */
	ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
	/** �Ƿ����ƶ�����������Ƕ���������Ž��е����ݸ��棬�����������Ϊ�˱������̫Ƶ����ɵ����ݴ��ҡ� */	
	boolean isMove = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel);
		initView1();
		initData1();
	}

	/** ��ʼ������*/
	private void initData1() {

		new TitleBuilder(this).setTitleText("频道管理").setLeftImage(R.mipmap.leftarrow).setLeftOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.left_center, R.anim.center_right);
			}
		}).setRightText("保存").setRightOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveChannel();
				overridePendingTransition(R.anim.left_center, R.anim.center_right);
				finish();
			}
		});

	    userChannelList = ((ArrayList<ChannelItem>)ChannelManage.getManage(Application.getInstance().getSQLHelper()).getUserChannel());
	    otherChannelList = ((ArrayList<ChannelItem>)ChannelManage.getManage(Application.getInstance().getSQLHelper()).getOtherChannel());
	    userAdapter = new DragAdapter(this, userChannelList);
	    userGridView.setAdapter(userAdapter);
	    otherAdapter = new OtherAdapter(this, otherChannelList);
	    otherGridView.setAdapter(otherAdapter);
	    //����GRIDVIEW��ITEM�ĵ������
	    otherGridView.setOnItemClickListener(this);
	    userGridView.setOnItemClickListener(this);
	}
	
	/** ��ʼ������*/
	private void initView1() {
//		new TitleBuilder(this).setTitleText("频道管理").setLeftImage(R.drawable.leftarrow).setLeftOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				saveChannel();
//				Intent intent = new Intent(getApplicationContext(), TabRadioBActivity.class);
////				setResult(MainActivity.CHANNELRESULT, intent);
//				intent.putExtra("fxtype", 1);
//				startActivity(intent);
//				finish();
//			}
//		}).build();
		userGridView = (DragGrid) findViewById(R.id.userGridView);
		otherGridView = (OtherGridView) findViewById(R.id.otherGridView);
	}

	
	
	/** GRIDVIEW��Ӧ��ITEM��������ӿ�  */
	@Override
	public void onItemClick(AdapterView<?> parent, final View view, final int position,long id) {
		//��������ʱ��֮ǰ������û��������ô���õ���¼���Ч
		if(isMove){
			return;
		}
		switch (parent.getId()) {
		case R.id.userGridView:
			//positionΪ 0��1 �Ĳ����Խ����κβ���
			if (position != 0 && position != 1) {
				final ImageView moveImageView = getView(view);
				if (moveImageView != null) {
					TextView newTextView = (TextView) view.findViewById(R.id.text_item);
					final int[] startLocation = new int[2];
					newTextView.getLocationInWindow(startLocation);
					final ChannelItem channel = ((DragAdapter) parent.getAdapter()).getItem(position);//��ȡ�����Ƶ������
					otherAdapter.setVisible(false);
					//��ӵ����һ��
					otherAdapter.addItem(channel);
					new Handler().postDelayed(new Runnable() {
						public void run() {
							try {
								int[] endLocation = new int[2];
								//��ȡ�յ������
								otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
								MoveAnim(moveImageView, startLocation , endLocation, channel,userGridView);
								userAdapter.setRemove(position);
							} catch (Exception localException) {
							}
						}
					}, 50L);
				}
			}
			break;
		case R.id.otherGridView:
			final ImageView moveImageView = getView(view);
			if (moveImageView != null){
				TextView newTextView = (TextView) view.findViewById(R.id.text_item);
				final int[] startLocation = new int[2];
				newTextView.getLocationInWindow(startLocation);
				final ChannelItem channel = ((OtherAdapter) parent.getAdapter()).getItem(position);
				userAdapter.setVisible(false);
				//��ӵ����һ��
				userAdapter.addItem(channel);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						try {
							int[] endLocation = new int[2];
							//��ȡ�յ������
							userGridView.getChildAt(userGridView.getLastVisiblePosition()).getLocationInWindow(endLocation);
							MoveAnim(moveImageView, startLocation , endLocation, channel,otherGridView);
							otherAdapter.setRemove(position);
						} catch (Exception localException) {
						}
					}
				}, 50L);
			}
			break;
		default:
			break;
		}
	}
	/**
	 * ���ITEM�ƶ�����
	 * @param moveView
	 * @param startLocation
	 * @param endLocation
	 * @param moveChannel
	 * @param clickGridView
	 */
	private void MoveAnim(View moveView, int[] startLocation,int[] endLocation, final ChannelItem moveChannel,
			final GridView clickGridView) {
		int[] initLocation = new int[2];
		//��ȡ���ݹ�����VIEW������
		moveView.getLocationInWindow(initLocation);
		//�õ�Ҫ�ƶ���VIEW,�������Ӧ��������
		final ViewGroup moveViewGroup = getMoveViewGroup();
		final View mMoveView = getMoveView(moveViewGroup, moveView, initLocation);
		//�����ƶ�����
		TranslateAnimation moveAnimation = new TranslateAnimation(
				startLocation[0], endLocation[0], startLocation[1],
				endLocation[1]);
		moveAnimation.setDuration(300L);//����ʱ��
		//��������
		AnimationSet moveAnimationSet = new AnimationSet(true);
		moveAnimationSet.setFillAfter(false);//����Ч��ִ����Ϻ�View���󲻱�������ֹ��λ��
		moveAnimationSet.addAnimation(moveAnimation);
		mMoveView.startAnimation(moveAnimationSet);
		moveAnimationSet.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				isMove = true;
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				moveViewGroup.removeView(mMoveView);
				// instanceof �����ж�2��ʵ���ǲ���һ�����жϵ������DragGrid����OtherGridView
				if (clickGridView instanceof DragGrid) {
					otherAdapter.setVisible(true);
					otherAdapter.notifyDataSetChanged();
					userAdapter.remove();
				}else{
					userAdapter.setVisible(true);
					userAdapter.notifyDataSetChanged();
					otherAdapter.remove();
				}
				isMove = false;
			}
		});
	}
	
	/**
	 * ��ȡ�ƶ���VIEW�������ӦViewGroup��������
	 * @param viewGroup
	 * @param view
	 * @param initLocation
	 * @return
	 */
	private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
		int x = initLocation[0];
		int y = initLocation[1];
		viewGroup.addView(view);
		LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mLayoutParams.leftMargin = x;
		mLayoutParams.topMargin = y;
		view.setLayoutParams(mLayoutParams);
		return view;
	}
	
	/**
	 * �����ƶ���ITEM��Ӧ��ViewGroup��������
	 */
	private ViewGroup getMoveViewGroup() {
		ViewGroup moveViewGroup = (ViewGroup) getWindow().getDecorView();
		LinearLayout moveLinearLayout = new LinearLayout(this);
		moveLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		moveViewGroup.addView(moveLinearLayout);
		return moveLinearLayout;
	}
	
	/**
	 * ��ȡ�����Item�Ķ�ӦView��
	 * @param view
	 * @return
	 */
	private ImageView getView(View view) {
		view.destroyDrawingCache();
		view.setDrawingCacheEnabled(true);
		Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		ImageView iv = new ImageView(this);
		iv.setImageBitmap(cache);
		return iv;
	}
	
	private void saveChannel() {
		ChannelManage.getManage(Application.getInstance().getSQLHelper()).deleteAllChannel();
		ChannelManage.getManage(Application.getInstance().getSQLHelper()).saveUserChannel(userAdapter.getChannnelLst());
		ChannelManage.getManage(Application.getInstance().getSQLHelper()).saveOtherChannel(otherAdapter.getChannnelLst());
	}
	
	@Override
	public void onBackPressed() {
		saveChannel();
//		if(userAdapter.isListChanged()){
//			Intent intent = new Intent(getApplicationContext(), TabRadioBActivity.class);
////			setResult(MainActivity.CHANNELRESULT, intent);
//			intent.putExtra("fxtype", 1);
//			startActivity(intent);
//			finish();
//		}else{
//			super.onBackPressed();
//			System.out.println("没改变");
//		}
		overridePendingTransition(R.anim.left_center, R.anim.center_right);
		finish();
	}


}
