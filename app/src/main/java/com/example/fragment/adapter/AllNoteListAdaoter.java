package com.example.fragment.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bean.note;
import com.example.main.R;

import java.util.List;

public class AllNoteListAdaoter  extends BaseQuickAdapter<note, BaseViewHolder> {


    public AllNoteListAdaoter(int layoutResId, @Nullable List<note> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, note item) {

        helper.setText(R.id.tv_home_date, item.getCreatedAt() + "");
        helper.setText(R.id.tv_message_news_content, "标题: " + item.getNoteTitle());
        if (TextUtils.isEmpty(item.getNoteWords())) {
            helper.setText(R.id.tv_home_content, "暂无内容");
        } else {
            helper.setText(R.id.tv_home_content, "内容: "+item.getNoteWords());
        }
    }
}
