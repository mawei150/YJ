package com.example.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * @author MW
 * @date 2019/4/12
 * <p>
 * 描述：贴子回复类
 */

public class ShowNoteReply  extends BmobObject implements Serializable {


    private  String  content;
    private  BeanUserBase  author;
    private  ShowNote  post;//这个表示  评论的帖子

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BeanUserBase getAuthor() {
        return author;
    }

    public void setAuthor(BeanUserBase author) {
        this.author = author;
    }

    public ShowNote getPost() {
        return post;
    }

    public void setPost(ShowNote post) {
        this.post = post;
    }
}
