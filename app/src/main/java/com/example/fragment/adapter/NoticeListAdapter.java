package com.example.fragment.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bean.Notice;
import com.example.main.R;
import com.example.util.GlobalVariables;

import java.util.List;
/**
 * @author MW
 * @date 2019/4/10
 * <p>
 * 描述：公告列表  adapter
 */

public class NoticeListAdapter extends BaseQuickAdapter<Notice, BaseViewHolder> {

    public NoticeListAdapter(int layoutResId, @Nullable List<Notice> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Notice item) {
        helper.setText(R.id.tv_message_news_content,item.getTitle());
        helper.setText(R.id.tv_home_date,item.getCreatedAt());
        helper.setText(R.id.tv_home_content,item.getContent());
        if(GlobalVariables.getRole()==2 || item.isRead()){//如果  角色是管理员  且已读  那么不显示
              helper.setVisible(R.id.tv_home_count,false);
        }else{
            helper.setVisible(R.id.tv_home_count,true);
        }
    }
}
