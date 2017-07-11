package com.dophin.weichat_article.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

/**
 * Created by caiguoqing on 2017/2/21.
 */

public class ImageLoader {

    /**
     * 加载图片
     * @param context
     * @param url
     * @param imageView
     * @param defaultIamge
     */
    public static void displayImage(Context context,String url , ImageView imageView, int defaultIamge){
        if (url == null ||  url.equals("") || url.equals("null")){
            imageView.setImageResource(defaultIamge);
        }else{
//            if (url.length() >= 4){
//                if(!url.substring(0,4).equals("http")){
//                    if (url.substring(0,2).equals("//")){
//                        Picasso.with(context).load("http:" +url).into(imageView);
//                    }else{
//                        Picasso.with(context).load("http://" +url).into(imageView);
//                    }
//                }else{
//                    Picasso.with(context).load(url).into(imageView);
//                }
//            }
//            else{
//                Picasso.with(context).load(url).into(imageView);
//            }

            Picasso.with(context).load(url).into(imageView);

        }
    }
    // green 4.61m  blue 5.66  354
}
