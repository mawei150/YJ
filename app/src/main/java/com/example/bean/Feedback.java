package com.example.bean;

import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author MW
 * @date 2019/4/21
 * <p>
 * 描述： 反馈人的信息
 */

public class Feedback   extends BmobObject implements Serializable {

    private  String  content;//内容
    private  String  phone;//联系电话
    private  String  readName;//处理人姓名
    private List<BmobFile> picture;//图片
    private  String  realName;//反馈者的真实姓名
    private  int state;//处理 状态  1.暂未处理  2.正在处理  3.已处理

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReadName() {
        return readName;
    }

    public void setReadName(String readName) {
        this.readName = readName;
    }

    public List<BmobFile> getPicture() {
        return picture;
    }

    public void setPicture(List<BmobFile> picture) {
        this.picture = picture;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
