package com.example.bean;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobUser;

/**
 * @author MW
 * @date 2019/3/19
 * <p>
 * 描述：个人基本类
 */

public class BeanUserBase extends BmobUser  {

    /*private String username;//账号名称
    private String password;//密码


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }*/
    private  boolean sex;//性别
    private  String  desc;//简介

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
