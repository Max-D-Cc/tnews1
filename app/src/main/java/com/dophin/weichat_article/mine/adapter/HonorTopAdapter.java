package com.dophin.weichat_article.mine.adapter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dophin.weichat_article.R;

import java.util.List;

/**
 * Created by caiguoqing on 2017/3/8.
 */

public class HonorTopAdapter extends BaseAdapter {

    private Context context;
    private List<String> imgs;

    public HonorTopAdapter(Context context, List<String> imgs){
        this.context = context;
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView  = View.inflate(context, R.layout.item_honor,null);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.item_honor_img);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        String str = imgs.get(position);

        if (str.equals("QTDS")){
            holder.img.setImageResource(R.mipmap.qingtingdianshuihuode);
        }else if(str.equals("CHHY")){
            holder.img.setImageResource(R.mipmap.caihuahengyihuode);
        }else if(str.equals("XYMQ")){
            holder.img.setImageResource(R.mipmap.xiaoyoumingqihuode);
        }else if(str.equals("FKDG")){
            holder.img.setImageResource(R.mipmap.fukediguohuode);
        }else if(str.equals("CLFM")){
            holder.img.setImageResource(R.mipmap.chulufengmanghuode);
        }else if(str.equals("DGQB")){
            holder.img.setImageResource(R.mipmap.duguqiubaihuode);
        }else if(str.equals("DBYF")){
            holder.img.setImageResource(R.mipmap.dubayifanghuode);
        }else if(str.equals("MFJL")){
            holder.img.setImageResource(R.mipmap.manfujinglunhuode);
        }else if(str.equals("XBXK")){
            holder.img.setImageResource(R.mipmap.xibenxiaokanghuode);
        }else if(str.equals("FJYF")){
            holder.img.setImageResource(R.mipmap.fujiayifanghuode);
        }else if(str.equals("JYYS")){
            holder.img.setImageResource(R.mipmap.jinyiyushihuode);
        }else if(str.equals("NZXS")){
            holder.img.setImageResource(R.mipmap.nangzhongxiusehuode);
        }else if(str.equals("SBSJ")){
            holder.img.setImageResource(R.mipmap.shoubushijuanhuode);
        }else if(str.equals("YZBF")){
            holder.img.setImageResource(R.mipmap.yingzibofahuode);
        }else if(str.equals("CKCZ")){
            holder.img.setImageResource(R.mipmap.chukouchengzhanghuode);
        }else if(str.equals("CLBC")){
            holder.img.setImageResource(R.mipmap.chuleibacuihuode);
        }else if(str.equals("PMTX")){
            holder.img.setImageResource(R.mipmap.pengmantianxiahuode);
        }else if(str.equals("GJSY")){
            holder.img.setImageResource(R.mipmap.guangjieshanyuanhuode);
        }else if(str.equals("SXPM")){
            holder.img.setImageResource(R.mipmap.suoxiangpimihuode);
        }else if(str.equals("YHBY")){
            holder.img.setImageResource(R.mipmap.yihubaiyinghuode);
        }

//        holder.img.setImageResource(imgs.get(position));

        return convertView;
    }

    class ViewHolder {
        ImageView img;
    }
}
