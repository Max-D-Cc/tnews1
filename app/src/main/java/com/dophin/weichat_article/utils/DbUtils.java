package com.dophin.weichat_article.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by caiguoqing on 2017/3/20.
 */

public class DbUtils {


    class DbHelp extends SQLiteOpenHelper {

        public DbHelp(Context context) {
            super(context, "newsid.db", null, 1);

        }

//        String sql = "create table messages(id integer primary key autoincrement,receiver text,sender text,mcontent text,type integer,isread integer,tximg text,time text,sessionid text,user1 text,user2 text,user3 text);";
        String sql1 = "create table news(id integer primary key autoincrement,newsid integer);";

        @Override
        public void onCreate(SQLiteDatabase db) {
//            db.execSQL(sql);
            db.execSQL(sql1);
//            Log.e("EzhuanSQL", "创建表成功");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }

    private DbHelp mMyHelper = null;
    private Context context;

    public DbUtils(Context context) {
        super();
        this.context = context;
        mMyHelper = new DbHelp(context);
    }

    /**
     * 添加新闻id
     *
     * @param
     * @param
     */
    public void addNews(String id) {
        // 获取数据库实例
        SQLiteDatabase db = mMyHelper.getWritableDatabase();
        // 添加
        ContentValues values = new ContentValues();
        values.put("newsid", id);
        db.insert("news", "", values);
        // 关闭
        db.close();
    }

    //
    // 查询是否有id
    // select * from numbers order by id desc;

    public boolean isExistId(String id) {
        boolean flag = false;
        // select * from numbers where phone='10010';
        // 获取数据库实例
        SQLiteDatabase db = mMyHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from news where newsid = ?;",
                new String[] { id });
        if (cursor.getCount() > 0) {
            flag = true;
        } else {
            ContentValues values = new ContentValues();
            values.put("newsid", id);
            db.insert("news", "", values);
        }
        cursor.close();
        // 关闭
        db.close();
        return flag;
    }

}
