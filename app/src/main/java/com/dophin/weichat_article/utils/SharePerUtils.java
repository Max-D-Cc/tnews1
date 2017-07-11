package com.dophin.weichat_article.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Base64;

public class SharePerUtils {
	private static final String CONFIG = "config";

	public static void putString(Context context, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		sp.edit().putString(key, value).commit();
	}
	
	public static void putKeyString(Context context, String config, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		sp.edit().putString(key, value).commit();
	}
	public static void putKeyLong(Context context, String config, String key, Long value) {
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		sp.edit().putLong(key, value).commit();
	}

	public static String getKeyString(Context context, String config, String key, String defValues) {
		SharedPreferences sp = context.getSharedPreferences(config, 0);
		return sp.getString(key, defValues);
	}
	
	// ��ȡstring���͵�����
	public static String getString(Context context, String key, String defValues) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.getString(key, defValues);
	}

	// �洢boolean���͵�����
	public static void putBoolean(Context context, String key, boolean value) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		sp.edit().putBoolean(key, value).commit();
	}

	// ��ȡboolean���͵�����
	public static boolean getBoolean(Context context, String key,
			boolean defValues) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.getBoolean(key, defValues);
	}

	// �洢int���͵�����
	public static void putInt(Context context, String key, int value) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		sp.edit().putInt(key, value).commit();
	}

	// ��ȡint���͵�����
	public static int getInt(Context context, String key, int defValues) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.getInt(key, defValues);
	}

	// long
	public static void putLong(Context context, String key, long value) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		sp.edit().putLong(key, value).commit();
	}

	// long
	public static long getLong(Context context, String key, long defValues) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG, 0);
		return sp.getLong(key, defValues);
	}
	
	
	  /**
     * list to sp
     * @param SceneList
     * @return
     * @throws IOException
     */
    public static String SceneList2String(List SceneList)
            throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(SceneList);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    /**
     * string to list
     * @param SceneListString
     * @return
     * @throws StreamCorruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static List String2SceneList(String SceneListString)
            throws StreamCorruptedException, IOException,
            ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        List SceneList = (List) objectInputStream
                .readObject();
        objectInputStream.close();
        return SceneList;
    }
	
    
    /**
     * 保存list数据
     */
    public static void setListSP(Context context ,String spName, String key, List SceneList){
        SharedPreferences mySharedPreferences=context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = mySharedPreferences.edit();
        try {
            String liststr = SceneList2String(SceneList);
            edit.putString(key,liststr);
            edit.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *获取list sp数据
     */
    public static Object getListSP(Context context ,String fileName, String key, List SceneList){
        List string2SceneList = null;
        SharedPreferences sharedPreferences= context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String liststr = sharedPreferences.getString(key, "");
        try {
             string2SceneList = String2SceneList(liststr);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return string2SceneList;
    }

}
