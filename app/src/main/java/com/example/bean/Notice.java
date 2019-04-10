package com.example.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * @author MW
 * @date 2019/4/10
 * <p>
 * 描述： 公告实体类
 */

public class Notice  extends BmobObject implements Serializable {

    private   String  title;//标题
    private   String  content;//内容
    private   boolean isRead;//公告是否已读

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
