package com.example.util.advanced;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.main.R;

/**
 * @author MW
 * @date 2019/3/25
 * <p>
 * 描述： 自定义dialog   拍照上传图片dialog
 */

public class ImageDialog extends Dialog {


    private OnImageDialogClickLister mImageDialogClickLister;
    private View mDialogView;
    private Context mContext;
    private TextView mSelectCamera;//选择相机
    private TextView mSelectPhoto;//选择相册
    private TextView mCancel;//取消


    public interface OnImageDialogClickLister {

        void OnCancelLister();//取消

        void OnSelectCameraLister();//选择相机

        void OnSelectPhotoLister();//选择相册
    }


    public ImageDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ppw_select_photo);
        setCanceledOnTouchOutside(true);//点击外面消失  dialog
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);

        mSelectCamera = findViewById(R.id.tvSelectPhotoCamera);
        mSelectPhoto = findViewById(R.id.tvSelectPhotoSelect);
        mCancel = findViewById(R.id.tvSelectPhotoCancel);

        mSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageDialogClickLister != null) {
                    mImageDialogClickLister.OnSelectPhotoLister();
                }
            }
        });
        mSelectCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageDialogClickLister != null) {
                    mImageDialogClickLister.OnSelectCameraLister();
                }
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageDialogClickLister != null) {
                    mImageDialogClickLister.OnCancelLister();
                }
            }
        });
    }


    public OnImageDialogClickLister getmImageDialogClickLister() {
        return mImageDialogClickLister;
    }

    public ImageDialog setmImageDialogClickLister(OnImageDialogClickLister mImageDialogClickLister) {
        this.mImageDialogClickLister = mImageDialogClickLister;
        return this;
    }

    @Override
    public void show() {
        super.show();

        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mDialogView.setPadding(0, 0, 0, 0);
        // getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);  //动画  先不加
        getWindow().setBackgroundDrawableResource(android.R.color.white);
        getWindow().setAttributes(layoutParams);
        getWindow().setDimAmount(0.5f);//设置dialog外部透明
    }
}
