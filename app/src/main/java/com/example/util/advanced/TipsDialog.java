package com.example.util.advanced;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.main.R;

import butterknife.BindView;

/**
 * @author MW
 * @date 2019/4/4
 * <p>
 * 描述： 提示Dialog
 */

public class TipsDialog extends Dialog {



    private TextView mTitleText;
    private TextView mContentText;
    private TextView mCancelButton;
    private TextView mConfirmButton;
    private String  mTitle;
    private String  mContent;
    private TipsDialogOnclickLister tipsDialogOnclickLister;

    private View mDialogView;
    private Context mContext;


    public TipsDialog(@NonNull Context context) {
        super(context);
        //this.mContext = mContext;
    }

    public interface  TipsDialogOnclickLister{

        void   OnCancel();

        void   OnConfirm();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);
        setCanceledOnTouchOutside(true);//点击外面消失  dialog
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);//这句话是什么意思
        mTitleText=findViewById(R.id.title_text);//标题
        mContentText=findViewById(R.id.content_text);//内容
        mCancelButton=findViewById(R.id.cancel_button);//取消
        mConfirmButton=findViewById(R.id.confirm_button);//确定


        setTitleText(mTitle);//设置标题
        setContent(mContent);

        mCancelButton.setOnClickListener(new View.OnClickListener() {//取消的点击事件
            @Override
            public void onClick(View v) {
                     if(tipsDialogOnclickLister !=null){
                         tipsDialogOnclickLister.OnCancel();
                     }
            }
        });

        mConfirmButton.setOnClickListener(new View.OnClickListener() {//确定的点击事件
            @Override
            public void onClick(View v) {
                if(tipsDialogOnclickLister !=null){
                    tipsDialogOnclickLister.OnConfirm();
                }
            }
        });

    }

    public void setContent(String content) {
        mContent=content;
        if(mContent !=null && mContentText !=null){
            mContentText.setText(mContent);
        }
    }


    public void  setTitleText(String title) {
        mTitle=title;
        if(mTitleText !=null &&  mTitle !=null){
            mTitleText.setText(mTitle);
        }
    }


    public TipsDialogOnclickLister getTipsDialogOnclickLister() {
        return tipsDialogOnclickLister;
    }

    public void setTipsDialogOnclickLister(TipsDialogOnclickLister tipsDialogOnclickLister) {
        this.tipsDialogOnclickLister = tipsDialogOnclickLister;
    }

/*
    @Override
    public void show() {
        super.show();

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mDialogView.setPadding(0, 0, 0, 0);
        // getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);  //动画  先不加
        getWindow().setBackgroundDrawableResource(android.R.color.white);
        getWindow().setAttributes(layoutParams);
        getWindow().setDimAmount(0.5f);//设置dialog外部透明
    }*/
}
