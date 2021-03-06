package com.example.bean;


import java.io.Serializable;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author MW
 * @date 2019/4/2
 * <p>
 * 描述： 笔记
 */

public class note extends BmobObject implements Serializable {

    private String noteTitle;//笔记标题
    private String noteWords;//文字笔记

    private Integer noteType;//笔记类型

    private List<BmobFile> notePicture;//图片笔记

    private BeanUserBase author;

    private List<BmobFile> bmobFileList;

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteWords() {
        return noteWords;
    }

    public void setNoteWords(String noteWord) {
        this.noteWords = noteWord;
    }

    public Integer getNoteType() {
        return noteType;
    }

    public void setNoteType(Integer noteType) {
        this.noteType = noteType;
    }

    public BeanUserBase getAuthor() {
        return author;
    }

    public void setAuthor(BeanUserBase author) {
        this.author = author;
    }

    /*public List getNotePicture() {
        return notePicture;
    }

    public void setNotePicture(List notePicture) {
        this.notePicture = notePicture;
    }*/

    public List<BmobFile> getNotePicture() {
        return notePicture;
    }

    public void setNotePicture(List<BmobFile> notePicture) {
        this.notePicture = notePicture;
    }

    public List<BmobFile> getBmobFileList() {
        return bmobFileList;
    }

    public void setBmobFileList(List<BmobFile> bmobFileList) {
        this.bmobFileList = bmobFileList;
    }


}
