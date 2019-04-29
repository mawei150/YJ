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

    public static final String USER_PASSWORD="YJ_password";

    public static final String USER_OBJECTID = "yj_user_object";//用户表  一列的objectId

    public static final String USER_ROLE="yj_user_role";//用户角色  1.用户  2.管理员

    public static final String USER_NICK_NAME="yj_user_nick_name";//用户昵称

    public static final String USER_PHONE="yj_user_phone";//用户电话


    public static void setUserPhone(String phone) {
        SharedPreferencesClass.putstring(BaseApplication.getContext(),USER_PHONE,phone);
    }

    public static String getUserPhone() {
        return SharedPreferencesClass.getstring(BaseApplication.getContext(),USER_PHONE, "");
    }

    public static void setUserPassword(String password) {
        SharedPreferencesClass.putstring(BaseApplication.getContext(),USER_PASSWORD,password);
    }

    public static String getUserPassword() {
        return SharedPreferencesClass.getstring(BaseApplication.getContext(),USER_PASSWORD, "");
    }


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

    public static void setRole(int role) {
        SharedPreferencesClass.putInt(BaseApplication.getContext(),USER_ROLE, role);
    }

    public static int getRole() {
        return SharedPreferencesClass.getInt(BaseApplication.getContext(),USER_ROLE,1);
    }

    public static String getUserNickName() {
        return SharedPreferencesClass.getstring(BaseApplication.getContext(),USER_NICK_NAME, "");
    }

    public static void setUserNickName(String nickName) {
        SharedPreferencesClass.putstring(BaseApplication.getContext(),USER_NICK_NAME,  nickName);
    }

}
