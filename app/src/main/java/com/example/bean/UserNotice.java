package com.example.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class UserNotice extends BmobObject  {

    private  String  noticeId;//公告id
    private  String  userId;//用户Id


    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
