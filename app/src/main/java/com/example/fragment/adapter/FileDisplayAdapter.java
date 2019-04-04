package com.example.fragment.adapter;


import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.main.R;
import com.squareup.picasso.Picasso;
import java.util.List;
import cn.bmob.v3.datatype.BmobFile;


public class FileDisplayAdapter  extends BaseQuickAdapter<BmobFile,BaseViewHolder> {


    public FileDisplayAdapter(int layoutResId, @Nullable List<BmobFile> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BmobFile item) {

        Picasso.with(mContext).load(item.getUrl()).into((ImageView) helper.getView(R.id.iv_content));

        helper.addOnClickListener(R.id.iv_delete);
    }
}
