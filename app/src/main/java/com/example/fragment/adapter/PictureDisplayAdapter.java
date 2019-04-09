package com.example.fragment.adapter;


import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.application.BaseApplication;
import com.example.bean.note;
import com.example.main.R;

import java.util.List;

import cn.bmob.v3.datatype.BmobFile;


public class PictureDisplayAdapter  extends BaseQuickAdapter<BmobFile, BaseViewHolder> {

    public PictureDisplayAdapter(int layoutResId, @Nullable List<BmobFile> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BmobFile item) {
        Glide.with(BaseApplication.getContext())
                .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.default_coursecover).placeholder(R.drawable.default_coursecover))
                . load(item.getUrl()).into((ImageView) helper.getView(R.id.iv_picture));
    }


}
