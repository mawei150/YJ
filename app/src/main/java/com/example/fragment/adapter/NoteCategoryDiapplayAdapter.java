package com.example.fragment.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bean.note;
import com.example.main.R;

import java.util.List;

/**
 * @author MW
 * @date 2019/4/3
 * <p>
 * 描述： 类别展示
 */

public class NoteCategoryDiapplayAdapter  extends BaseQuickAdapter<note, BaseViewHolder> {
    public NoteCategoryDiapplayAdapter(int layoutResId, @Nullable List<note> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, note item) {

        helper.setText(R.id.tv_message_news_content, item.getNoteTitle());
        if (TextUtils.isEmpty(item.getNoteWords())) {
            helper.setText(R.id.tv_home_content, "暂无消息");
        } else {
            helper.setText(R.id.tv_home_content, item.getNoteWords());
        }

        //作业&考试
        if (item.getNoteType() == 1) {
            helper.setText(R.id.tv_home_type, "文字");
            helper.setBackgroundRes(R.id.tv_home_type, R.drawable.layer_message_home_bg);
        } else if(item.getNoteType()==2) {
           /* helper.setBackgroundRes(R.id.tv_home_type, R.drawable.layer_message_exam_bg);
            helper.setText(R.id.tv_home_type, "考");*/
            helper.setText(R.id.tv_home_type, "图片");
            helper.setBackgroundRes(R.id.tv_home_type, R.drawable.layer_message_home_bg);
        }else  if(item.getNoteType()==3){
            helper.setText(R.id.tv_home_type, "视频");
            helper.setBackgroundRes(R.id.tv_home_type, R.drawable.layer_message_home_bg);
        }else{
            helper.setText(R.id.tv_home_type, "音频");
            helper.setBackgroundRes(R.id.tv_home_type, R.drawable.layer_message_home_bg);
        }

    }
}
