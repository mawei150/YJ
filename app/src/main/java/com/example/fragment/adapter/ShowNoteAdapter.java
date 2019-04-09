package com.example.fragment.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bean.ShowNote;
import com.example.main.R;

import java.util.List;

/**
 * @author MW
 * @date 2019/4/9
 * <p>
 * 描述： 社区  adapter
 */

public class ShowNoteAdapter extends BaseQuickAdapter<ShowNote, BaseViewHolder> {

    public ShowNoteAdapter(int layoutResId, @Nullable List<ShowNote> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShowNote item) {
             helper.setText(R.id.tv_content,item.getContent());
             helper.setText(R.id.tv_dateCrate,item.getCreatedAt());
             helper.setText(R.id.tv_displayName,"王小明");
    }
}
