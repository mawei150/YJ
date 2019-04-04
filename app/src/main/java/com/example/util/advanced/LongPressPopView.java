package com.example.util.advanced;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.main.R;

/**
 * @author MW
 * @date 2019/4/4
 * <p>
 * 描述： 长按显示  编辑、删除
 */

public class LongPressPopView extends PopupWindow implements View.OnClickListener {


    private TextView mTvEdit;
    private TextView mTvDelete;
    private TextView mTvCancel;
    private View mContentView;
    private Context mContext;

    private LongPressOnClickLister  longPressOnClickLister;

    public  interface LongPressOnClickLister{

        void  cancel();

        void  Edit();

        void  Delete();
    }


    public LongPressPopView(Context context) {
        this.mContext = context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = layoutInflater.inflate(R.layout.ppw_edit_layout, null);
        setContentView(mContentView);


        mTvEdit=mContentView.findViewById(R.id.tv_edit);
        mTvDelete=mContentView.findViewById(R.id.tv_delete);
        mTvCancel=mContentView.findViewById(R.id.tv_cancel);

        mTvDelete.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        mTvEdit.setOnClickListener(this);

        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);
        setOutsideTouchable(true);
        setFocusable(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_edit:
                if(longPressOnClickLister !=null){
                    longPressOnClickLister.Edit();
                }
                break;
            case R.id.tv_delete:
                if(longPressOnClickLister !=null){
                    longPressOnClickLister.Delete();
                }
                break;
            case R.id.tv_cancel:
                if(longPressOnClickLister !=null){
                    longPressOnClickLister.cancel();
                }
                break;
                default:
                    break;
        }
        dismiss();
    }

    public LongPressOnClickLister getLongPressOnClickLister() {
        return longPressOnClickLister;
    }

    public void setLongPressOnClickLister(LongPressOnClickLister longPressOnClickLister) {
        this.longPressOnClickLister = longPressOnClickLister;
    }

    public TextView getmTvEdit() {
        return mTvEdit;
    }

    public TextView getmTvDelete() {
        return mTvDelete;
    }

    public TextView getmTvCancel() {
        return mTvCancel;
    }
}
