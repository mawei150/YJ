package com.example.util;


import com.example.application.BaseApplication;

/**
 * @author MW
 * @date 2019/3/27
 * <p>
 * 描述： 用户信息存储
 */

public class GlobalVariables {


    public static final String USERNAME = "YJ_UserName";//用户姓名

    public static final String USER_OBJECTID = "yj_user_object";//用户表  一列的objectId


    public static void setUsername(String username) {

        SharedPreferencesClass.putstring(BaseApplication.getContext(),USERNAME, username);
    }

    public static String getUsername() {

        return SharedPreferencesClass.getstring(BaseApplication.getContext(), USERNAME, "");
    }

    public static void setUserObjectId(String userObjectId) {
        SharedPreferencesClass.putstring(BaseApplication.getContext(),USER_OBJECTID, userObjectId);
    }

    public static String getUserObjectId() {
        return SharedPreferencesClass.getstring(BaseApplication.getContext(),USER_OBJECTID, "");
    }


}
