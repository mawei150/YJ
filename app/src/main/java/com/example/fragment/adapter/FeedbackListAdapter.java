package com.example.fragment.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bean.Feedback;
import com.example.main.R;

import java.util.List;

public class FeedbackListAdapter extends BaseQuickAdapter<Feedback, BaseViewHolder> {
    public FeedbackListAdapter(int layoutResId, @Nullable List<Feedback> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Feedback item) {

        helper.setText(R.id.tv_title, item.getRealName());
        helper.setText(R.id.tv_time, item.getCreatedAt());
        helper.setText(R.id.tv_content, item.getContent());
        //信息已处理
        if (item.getState() == 3) {
            helper.setText(R.id.tv_state, "已处理");
            helper.setTextColor(R.id.tv_content, mContext.getResources().getColor(R.color.color6));
            helper.setTextColor(R.id.tv_state, mContext.getResources().getColor(R.color.color6));
            helper.setBackgroundRes(R.id.tv_state, R.drawable.bg_news_read_already);
        } else {
            if(item.getState()==1) {
                helper.setText(R.id.tv_state, "未处理");
            }else{
                helper.setText(R.id.tv_state,"正在处理");
            }
            helper.setTextColor(R.id.tv_content, mContext.getResources().getColor(R.color.color1));
            helper.setTextColor(R.id.tv_state, mContext.getResources().getColor(R.color.color7));
            helper.setBackgroundRes(R.id.tv_state, R.drawable.bg_news_read_un);
        }
    }
}
