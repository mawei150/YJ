package com.example.fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.main.R;
import com.example.util.ToastUtil;
import com.squareup.picasso.Picasso;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;


/**
 * @author MW
 * @date 2019/4/3
 * <p>
 * 描述： 文件上传展示Adapter   图片  视频   自己造的轮子
 */

public class MyFileDisplayAdapter extends RecyclerView.Adapter<MyFileDisplayAdapter.FileViewHolder> {


    private Context mContext;
    private List<BmobFile> mFiles;

    public MyFileDisplayAdapter(Context mContext, List<BmobFile> mFiles) {
        this.mContext = mContext;
        this.mFiles = mFiles;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upload_view, parent, false);
        FileViewHolder holder = new FileViewHolder(view);

        holder.mIvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mContext, "我是图片");

            }
        });

        holder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(mContext, "我是删除按钮");
            }
        });

        return holder;
    }


    //赋值
    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        String path = mFiles.get(position).getUrl();
        Picasso.with(mContext).load(path).into(holder.mIvContent);
    }


    @Override
    public int getItemCount() {
        return mFiles.size();
    }


    static class FileViewHolder extends RecyclerView.ViewHolder {

        ImageView mIvContent;
        ImageView mIvDelete;

        public FileViewHolder(View itemView) {
            super(itemView);
            mIvContent = itemView.findViewById(R.id.iv_content);
            mIvDelete = itemView.findViewById(R.id.iv_delete);
        }
    }
}
