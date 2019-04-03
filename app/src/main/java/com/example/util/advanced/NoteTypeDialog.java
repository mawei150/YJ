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
 * @date 2019/4/1
 * <p>
 * 描述：笔记类型dialog
 */

public class NoteTypeDialog extends Dialog {

    private Context mContext;
    private View mDialogView;
    private TextView mTvImg;//图片笔记
    private TextView mTvWord;//文字笔记
    private TextView mTvVideo;//视频笔记
    private TextView mTvSoundRecord;//音频笔记
    private TextView mTvCancel;//取消
    public NoteTypeDialogClickLister onteTypeClickLister;

    public NoteTypeDialog(@NonNull Context context) {
        super(context);
    }


    public interface NoteTypeDialogClickLister {
        void ImgOnClickLister();

        void WordOnClickLister();

        void VideoOnClickLister();

        void SoundRecordOnClickLister();

        void CancelOnClickLister();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_note_type);
        setCanceledOnTouchOutside(true);//点击外面消失  dialog
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);

        mTvImg = findViewById(R.id.tv_img);
        mTvWord = findViewById(R.id.tv_word);
        mTvVideo = findViewById(R.id.tv_video);
        mTvSoundRecord = findViewById(R.id.tv_sound_record);
        mTvCancel = findViewById(R.id.tvSelectPhotoCancel);

        mTvImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onteTypeClickLister != null) {
                    onteTypeClickLister.ImgOnClickLister();
                }
            }
        });
        mTvWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onteTypeClickLister != null) {
                    onteTypeClickLister.WordOnClickLister();
                }
            }
        });
        mTvVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onteTypeClickLister != null) {
                    onteTypeClickLister.VideoOnClickLister();
                }
            }
        });
        mTvSoundRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onteTypeClickLister != null) {
                    onteTypeClickLister.SoundRecordOnClickLister();
                }
            }
        });
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onteTypeClickLister != null) {
                    onteTypeClickLister.CancelOnClickLister();
                }
            }
        });
    }

    public NoteTypeDialogClickLister getOnteTypeClickLister() {
        return onteTypeClickLister;
    }

    public void setOnteTypeClickLister(NoteTypeDialogClickLister onteTypeClickLister) {
        this.onteTypeClickLister = onteTypeClickLister;
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
