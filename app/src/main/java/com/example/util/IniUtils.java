package com.example.util;


import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class IniUtils {

    private static SharedPreferences sShare = null;

   /* public static void createFile(String proName) {
        //Context context = NetworkUtils.getApplicationContext();
        if (sShare == null) {
            sShare = context.getSharedPreferences(proName, 0);
        }
    }*/

    // 设置string
    public static boolean putString(String key, String value) {
        if (null == sShare) return false;

        Editor edit = sShare.edit();
        edit.putString(key, value);

        return edit.commit();
    }

    // 设置boolean
    public static boolean putBoolean(String key, boolean value) {
        if (null == sShare) return false;

        Editor edit = sShare.edit();
        edit.putBoolean(key, value);
        return edit.commit();
    }

    // 设置integer
    public static boolean putInt(String key, int value) {
        if (null == sShare) return false;

        Editor edit = sShare.edit();
        edit.putInt(key, value);
        return edit.commit();
    }


    // 设置long
    public static boolean putLong(String key, long value) {
        if (null == sShare) return false;

        Editor edit = sShare.edit();
        edit.putLong(key, value);
        return edit.commit();
    }

    // 设置float
    public static boolean putFloat(String key, float value) {
        if (null == sShare) return false;

        Editor edit = sShare.edit();
        edit.putFloat(key, value);
        return edit.commit();
    }

    // 获取string
    public static String getString(String key, String defValue) {
        if (null == sShare) return defValue;

        return sShare.getString(key, defValue);
    }

    // 获取integer
    public static int getInt(String key, int defValue) {
        if (null == sShare) return defValue;

        return sShare.getInt(key, defValue);
    }

    // 获取long
    public static long getLong(String key, long defValue) {
        if (null == sShare) return defValue;

        return sShare.getLong(key, defValue);
    }

    // 获取boolean
    public static boolean getBoolean(String key, boolean defValue) {
        if (null == sShare) return defValue;

        return sShare.getBoolean(key, defValue);
    }

    // 获取float
    public static float getFloat(String key, float defValue) {
        if (null == sShare) return defValue;

        return sShare.getFloat(key, defValue);
    }

    //清除数据
    public static boolean clear(String key) {
        if (sShare != null) {
            Editor edit = sShare.edit();
            edit.remove(key).commit();
        }
        return false;
    }

    //清除数据
    public static void clear() {
        if (sShare != null) {
            Editor edit = sShare.edit();
            edit.clear();
            edit.commit();
        }
    }
}
