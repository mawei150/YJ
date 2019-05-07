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
 * @date 2019/5/7
 * <p>
 * 描述： 选择拍照&相片&录制视频&选择视频
 */

public class ChoiceModeDialog extends Dialog implements View.OnClickListener {

    private View mDialogView;
    private TextView mTvRecord;//录制
    private TextView mTvSelect;//选择
    private TextView mTvCancel;//取消
    private ChoiceModeDialogLister mChoiceModeDialogLister;
    private String mRecord;//记录
    private String mSelect;//选择

    public interface ChoiceModeDialogLister {

        void onRecord();//录制

        void onSelect();//选择

        void onCancel();//取消

    }

    public ChoiceModeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choice_mode);
        setCanceledOnTouchOutside(true);
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mTvRecord = findViewById(R.id.tv_record);
        mTvSelect = findViewById(R.id.tv_select);
        mTvCancel = findViewById(R.id.tvSelectPhotoCancel);

        mTvCancel.setOnClickListener(this);
        mTvSelect.setOnClickListener(this);
        mTvRecord.setOnClickListener(this);

        setTextRecord(mRecord);
        setTextSelect(mSelect);
    }


    private void setTextRecord(String record) {
        mRecord=record;
        if(mRecord !=null && mTvRecord !=null){
            mTvRecord.setText(record);
        }
    }

    private void setTextSelect(String select) {
        mSelect=select;
        if(mSelect !=null && mTvSelect !=null){
            mTvSelect.setText(mSelect);
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_record:
                if(mChoiceModeDialogLister !=null){
                     mChoiceModeDialogLister.onRecord();
                }
                break;
            case R.id.tv_select:
                if(mChoiceModeDialogLister !=null){
                    mChoiceModeDialogLister.onSelect();
                }
                break;
            case R.id.tvSelectPhotoCancel:
                if(mChoiceModeDialogLister !=null){
                    mChoiceModeDialogLister.onCancel();
                }
                break;
            default:
                break;
        }
    }


    public ChoiceModeDialogLister getmChoiceModeDialogLister() {
        return mChoiceModeDialogLister;
    }

    public void setmChoiceModeDialogLister(ChoiceModeDialogLister mChoiceModeDialogLister) {
        this.mChoiceModeDialogLister = mChoiceModeDialogLister;
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
