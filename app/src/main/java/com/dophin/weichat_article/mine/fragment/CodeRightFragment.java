package com.dophin.weichat_article.mine.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.base.BaseActivity;
import com.dophin.weichat_article.base.BaseFragment;
import com.dophin.weichat_article.mine.activity.OffLineActivity;
import com.dophin.weichat_article.mine.activity.OffLineDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CodeRightFragment extends BaseFragment {


    @BindView(R.id.ol_img1)
    ImageView olImg1;
    @BindView(R.id.ol_one)
    RelativeLayout olOne;
    @BindView(R.id.ol_img2)
    ImageView olImg2;
    @BindView(R.id.ol_two)
    RelativeLayout olTwo;
    @BindView(R.id.activity_off_line)
    LinearLayout activityOffLine;

    public CodeRightFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_off_line, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.ol_one, R.id.ol_two})
    public void onClick(View view) {
        Intent intent = new Intent(mActivity,OffLineDetailActivity.class);
        switch (view.getId()) {
            case R.id.ol_one:
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.ol_two:
                intent.putExtra("type",2);
                startActivity(intent);
                break;
        }
        mActivity.overridePendingTransition(R.anim.right_center, R.anim.center_left);
    }
}
