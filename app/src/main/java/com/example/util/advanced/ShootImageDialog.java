package com.example.util.advanced;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.main.R;

/**
 * @author MW
 * @date 2019/5/6
 * <p>
 * 描述：弹出图片dialog
 */

public class ShootImageDialog extends Dialog {

    private View mDialogView;
    private ImageView mIvShoot;//弹出图片
    private String mUrl;//图片URL



    public ShootImageDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_shoot_image);
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        setCanceledOnTouchOutside(true);//点击外面消失  dialog
        mIvShoot=findViewById(R.id.iv_shoot);
        setImageUrl(mUrl);
    }

    public void setImageUrl(String url) {
        mUrl=url;
        if(mIvShoot !=null && url !=null){
            Glide.with(getContext()).load(url).into(mIvShoot);
        }
    }


    @Override
    public void show() {
        super.show();

        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mDialogView.setPadding(0, 0, 0, 0);
        // getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);  //动画  先不加
        getWindow().setBackgroundDrawableResource(android.R.color.white);
       //getWindow().setBackgroundDrawableResource();
        getWindow().setAttributes(layoutParams);
        getWindow().setDimAmount(0.5f);//设置dialog外部透明
    }
}
