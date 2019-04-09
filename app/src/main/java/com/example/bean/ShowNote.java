package com.example.bean;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class ShowNote extends BmobObject implements Serializable {

    private String content;//内容
    private List<BmobFile> picture;//图片
    private boolean isSelfVisible;//是仅自己可见
    private BeanUserBase author;//作者

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<BmobFile> getPicture() {
        return picture;
    }

    public void setPicture(List<BmobFile> picture) {
        this.picture = picture;
    }

    public boolean isSelfVisible() {
        return isSelfVisible;
    }

    public void setSelfVisible(boolean selfVisible) {
        isSelfVisible = selfVisible;
    }

    public BeanUserBase getAuthor() {
        return author;
    }

    public void setAuthor(BeanUserBase author) {
        this.author = author;
    }
}
