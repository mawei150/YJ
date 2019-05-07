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
 * @date 2019/4/30
 * <p>
 * 描述： 权限Dialog
 */

public class PowerDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private View mDialogView;
    private TextView mTvBlackList;//黑名单
    private TextView mTvForbidPost;//禁止发帖
    private TextView mTvNormal;//正常
    private TextView mTvCancel;//取消
    private OnPowerDialog onPowerDialog;


    public interface OnPowerDialog {

        void onBlackList();

        void onNormal();

        void onForbidPost();

        void onCancel();

    }

    public PowerDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_power);
        setCanceledOnTouchOutside(true);//点击外面消失  dialog
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);

        mTvBlackList = findViewById(R.id.tv_black_list);
        mTvForbidPost = findViewById(R.id.tv_forbid_post);
        mTvNormal = findViewById(R.id.tv_normal);
        mTvCancel = findViewById(R.id.tv_cancel);


        mTvBlackList.setOnClickListener(this);
        mTvForbidPost.setOnClickListener(this);
        mTvNormal.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_black_list:
                if (onPowerDialog != null) {
                    onPowerDialog.onBlackList();
                }
                break;
            case R.id.tv_forbid_post:
                if (onPowerDialog != null) {
                    onPowerDialog.onForbidPost();
                }
                break;
            case R.id.tv_normal:
                if (onPowerDialog != null) {
                    onPowerDialog.onNormal();
                }
                break;
            case R.id.tv_cancel:
                if (onPowerDialog != null) {
                    onPowerDialog.onCancel();
                }
                break;
            default:
                break;
        }
    }

    public OnPowerDialog getOnPowerDialog() {
        return onPowerDialog;
    }

    public void setOnPowerDialog(OnPowerDialog onPowerDialog) {
        this.onPowerDialog = onPowerDialog;
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
