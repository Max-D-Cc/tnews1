package com.dophin.weichat_article.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dophin.weichat_article.R;
import com.dophin.weichat_article.mine.bean.Task;

import java.util.List;

/**
 * Created by caiguoqing on 2017/3/20.
 */

public class TaskAdapter extends BaseAdapter{

    private Context context;
    private List<Task> list;

    private int[] imgs = {R.mipmap.taski1,R.mipmap.taski2,R.mipmap.taski3,R.mipmap.taski4,R.mipmap.taski1,R.mipmap.taski2,R.mipmap.taski3,R.mipmap.taski4};

    public TaskAdapter(Context context,List<Task> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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

        View view = View.inflate(context, R.layout.item_task, null);
        ImageView img = (ImageView) view.findViewById(R.id.item_task_img);
        TextView name = (TextView) view.findViewById(R.id.item_task_name);
        img.setImageResource(imgs[position]);
        name.setText(list.get(position).getNAME());
        return view;
    }

}
