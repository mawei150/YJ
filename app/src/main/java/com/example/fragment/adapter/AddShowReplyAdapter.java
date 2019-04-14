package com.example.fragment.adapter;


import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bean.ShowNoteReply;
import com.example.main.R;
import com.example.util.GlobalVariables;
import com.luck.picture.lib.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @author MW
 * @date 2019/4/13
 * <p>
 * 描述：新增回复Adapter
 */

public class AddShowReplyAdapter  extends BaseQuickAdapter<ShowNoteReply, BaseViewHolder> {

    public AddShowReplyAdapter(int layoutResId, @Nullable List<ShowNoteReply> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, ShowNoteReply item) {
        helper.setText(R.id.tv_content, item.getContent());
        helper.setText(R.id.tv_dateCrate, item.getCreatedAt());
        helper.setText(R.id.tv_displayName, item.getPost().getAuthor().getNickname());
        if (TextUtils.isEmpty(item.getPost().getAuthor().getHeadimage())) {
            Picasso.with(mContext).load(R.mipmap.ic_head1)
                    .into((ImageView) helper.getView(R.id.iv_profile));
        } else {
            Picasso.with(mContext).load(item.getPost().getAuthor().getHeadimage())
                    .into((ImageView) helper.getView(R.id.iv_profile));
        }
        if (item.getPost().getAuthor().getObjectId().equals(GlobalVariables.getUserObjectId())) {//我的发布
            helper.setVisible(R.id.iv_trash, true);
        } else {//不是我的
            helper.setVisible(R.id.iv_trash, false);
        }

        helper.addOnClickListener(R.id.iv_trash);
    }
}
