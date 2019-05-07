package com.example.fragment.adapter;


import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.application.BaseApplication;
import com.example.main.R;


import java.util.List;
import cn.bmob.v3.datatype.BmobFile;
import uk.co.senab.photoview.PhotoViewAttacher;


public class PictureDisplayAdapter extends BaseQuickAdapter<BmobFile, BaseViewHolder> {

    public PictureDisplayAdapter(int layoutResId, @Nullable List<BmobFile> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BmobFile item) {
       /* ImageView photoView=helper.getView(R.id.iv_picture);
        PhotoViewAttacher attacher=new PhotoViewAttacher(photoView);*/

       /* Glide.with(BaseApplication.getContext()).load(item.getUrl()).apply(new RequestOptions().override(800,800))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        photoView.setImageDrawable(resource);
                        attacher.update();
                    }
                });*/

         Glide.with(BaseApplication.getContext())
                .applyDefaultRequestOptions(new RequestOptions().error(R.drawable.default_coursecover)
                        .placeholder(R.drawable.default_coursecover))
                .load(item.getUrl()).into((ImageView) helper.getView(R.id.iv_picture));
      /*  attacher.update();*/
    }


}
