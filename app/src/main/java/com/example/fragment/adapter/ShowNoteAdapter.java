package com.example.fragment.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bean.ShowNote;
import com.example.main.R;
import com.example.util.GlobalVariables;
import com.luck.picture.lib.photoview.PhotoView;
import com.squareup.picasso.Picasso;

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
        helper.setText(R.id.tv_content, item.getContent());
        helper.setText(R.id.tv_dateCrate, item.getCreatedAt());
        helper.setText(R.id.tv_displayName, "测试用户");
        if (TextUtils.isEmpty(item.getAuthor().getHeadimage())) {
            Picasso.with(mContext).load(R.mipmap.ic_head1)
                    .into((ImageView) helper.getView(R.id.iv_profile));
        } else {
            Picasso.with(mContext).load(item.getAuthor().getHeadimage())
                    .into((ImageView) helper.getView(R.id.iv_profile));
        }

        if (item.getAuthor().getObjectId().equals(GlobalVariables.getUserObjectId())) {//我的发布
            helper.setVisible(R.id.iv_trash, true);
        } else {//不是我的
            helper.setVisible(R.id.iv_trash, false);
        }

        //分享的图片
        if (item.getPicture().size() !=0) {
            helper.setGone(R.id.ll_file, true);
            Picasso.with(mContext).load(item.getPicture().get(0).getUrl()).into((PhotoView) helper.getView(R.id.ll_file));
           /* PhotoView photoView=helper.getView(R.id.ll_file);
            photoView.enable();*/

        } else {
            helper.setGone(R.id.ll_file, false);
        }
        helper.addOnClickListener(R.id.iv_trash);
    }
}
