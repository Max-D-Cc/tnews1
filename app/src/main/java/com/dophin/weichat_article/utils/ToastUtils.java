package com.dophin.weichat_article.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dophin.weichat_article.R;

/**
 * Created by caiguoqing on 2017/2/28.
 */

public class ToastUtils {

    private static ToastUtils toastCommom;

    private Toast toast;

    private ToastUtils() {
    }

    public static ToastUtils getInstance() {
        if (toastCommom == null) {
            toastCommom = new ToastUtils();
        }
        return toastCommom;
    }

    /**
     * 显示Toast
     * @param context
     * @param
     * @param tvString
     */

    public void ToastShow(Context context, String tvString){
        View view = View.inflate(context, R.layout.toast_view, null);
        TextView content = (TextView) view.findViewById(R.id.toast_tv);
        content.setText(tvString);
        toast = new Toast(context);
//        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 500);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }


    public void ToastWzShow(Context context,String tvString){
        View view = View.inflate(context, R.layout.toast_wz, null);
        TextView content = (TextView) view.findViewById(R.id.toast_wzm);
        content.setText(tvString);
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
