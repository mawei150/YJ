package com.example.fragment.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.bean.BeanUserBase;
import com.example.main.R;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * @author MW
 * @date 2019/4/25
 * <p>
 * 描述： 用户管理Adapter
 */
public class UserListAdapter extends BaseQuickAdapter<BeanUserBase, BaseViewHolder> {

    public UserListAdapter(int layoutResId, @Nullable List<BeanUserBase> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BeanUserBase item) {
        helper.setText(R.id.tv_name, "用户名:" + item.getUsername());
        helper.setText(R.id.tv_nick_name, "用户昵称:" + item.getUsername());
        helper.setText(R.id.tv_mobile,TextUtils.isEmpty(item.getMobilePhoneNumber())?"暂无手机号码":item.getMobilePhoneNumber());
        Picasso.with(mContext).load(item.getHeadimage())
                .error(R.mipmap.defult_avatar)
                .placeholder(R.mipmap.defult_avatar)
                .into((ImageView) helper.getView(R.id.iv_head_image));
    }

}
