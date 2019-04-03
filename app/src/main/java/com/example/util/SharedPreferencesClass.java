package com.example.util;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author MW
 * @date 2019/3/21
 * <p>
 * 描述：SharedPreferences的封装
 */

public class SharedPreferencesClass {

    public  static  final String NAME="config";

    //放数据  键  值
    public  static  void  putstring(Context mContext,String key,String value){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);//简答的说就是，获得一个SharedPreferences对象，第一个参数为对象文件的名字，第二个参数为对此对象的操作权限，MODE_PRIVATE权限是指只能够被本应用所读写。
        sp.edit().putString(key,value).commit();
    }
    //拿数据  键  默认值（如果拿不到  就默认值）
    public  static  String  getstring(Context mContext,String key,String devalue){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);//简答的说就是，获得一个SharedPreferences对象，第一个参数为对象文件的名字，第二个参数为对此对象的操作权限，MODE_PRIVATE权限是指只能够被本应用所读写。
        return  sp.getString(key,devalue);
    }



    //放数据  键  值
    public  static  void  putInt(Context mContext,String key,int value){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);//简答的说就是，获得一个SharedPreferences对象，第一个参数为对象文件的名字，第二个参数为对此对象的操作权限，MODE_PRIVATE权限是指只能够被本应用所读写。
        sp.edit().putInt(key,value).commit();
    }
    //拿数据  键  默认值（如果拿不到  就默认值）
    public  static  int  getInt(Context mContext,String key,int devalue){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);//简答的说就是，获得一个SharedPreferences对象，第一个参数为对象文件的名字，第二个参数为对此对象的操作权限，MODE_PRIVATE权限是指只能够被本应用所读写。
        return  sp.getInt(key,devalue);
    }




    //放数据  键  值
    public  static  void  putBoolean(Context mContext,String key,boolean value){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);//简答的说就是，获得一个SharedPreferences对象，第一个参数为对象文件的名字，第二个参数为对此对象的操作权限，MODE_PRIVATE权限是指只能够被本应用所读写。
        sp.edit().putBoolean(key,value).commit();
    }
    //拿数据  键  默认值（如果拿不到  就默认值）
    public  static  boolean  getBoolean(Context mContext,String key,boolean devalue){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);//简答的说就是，获得一个SharedPreferences对象，第一个参数为对象文件的名字，第二个参数为对此对象的操作权限，MODE_PRIVATE权限是指只能够被本应用所读写。
        return  sp.getBoolean(key,devalue);
    }



    //删除单个
    public static  void  deleshare(Context mContext, String key){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);//简答的说就是，获得一个SharedPreferences对象，第一个参数为对象文件的名字，第二个参数为对此对象的操作权限，MODE_PRIVATE权限是指只能够被本应用所读写。
        sp.edit().remove(key);
    }


    //删除全部

    public  static  void  deleAll(Context mContext){
        SharedPreferences sp=mContext.getSharedPreferences(NAME,Context.MODE_PRIVATE);//简答的说就是，获得一个SharedPreferences对象，第一个参数为对象文件的名字，第二个参数为对此对象的操作权限，MODE_PRIVATE权限是指只能够被本应用所读写。
        sp.edit().clear().commit();
    }

}
