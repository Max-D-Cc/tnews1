package com.dophin.weichat_article.mine.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RedPackageShareActivity extends BaseActivity {

    @BindView(R.id.rps_img)
    CircleImageView rpsImg;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.activity_red_package_share)
    RelativeLayout activityRedPackageShare;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_red_package_share);
        ButterKnife.bind(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {

        // 图片合成-画布 先去画A 再去画B
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.mipmap.rp_share_bg); // bitmap为只读的

        Bitmap alterBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(alterBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        canvas.drawBitmap(bitmap, new Matrix(), paint);

        Bitmap ic_luncher = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher);

        canvas.drawBitmap(ic_luncher, new Matrix(), paint);
        imageView.setImageBitmap(alterBitmap);
    }

    @Override
    public void addActivity() {

    }
}
