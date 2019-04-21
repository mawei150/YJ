package com.example.util.advanced;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.main.R;

public class FeedbackStatePopView extends PopupWindow implements View.OnClickListener {

    private TextView mTvNotHandle;//未处理
    private TextView mTvBeginHandle;//开始处理
    private TextView mTvEndHandle;//结束处理
    private View mView;
    private Context mContext;
    private FeedbackStatePopViewOnClickLister feedbackStatePopViewOnClickLister;


    public interface FeedbackStatePopViewOnClickLister {

        void NotHandle();

        void BeginHandle();

        void EndHandle();
    }

    public FeedbackStatePopView(Context context) {
        super(context);
        this.mContext=context;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = layoutInflater.inflate(R.layout.view_feedback_sort, null);
        setContentView(mView);

        mTvNotHandle = mView.findViewById(R.id.tv_not_handle);
        mTvBeginHandle = mView.findViewById(R.id.tv_begin_handle);
        mTvEndHandle = mView.findViewById(R.id.tv_end_handle);

        mTvEndHandle.setOnClickListener(this);
        mTvBeginHandle.setOnClickListener(this);
        mTvNotHandle.setOnClickListener(this);

        setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_not_handle://还未处理
                if (feedbackStatePopViewOnClickLister != null) {
                    feedbackStatePopViewOnClickLister.NotHandle();
                }
                break;
            case R.id.tv_begin_handle://开始处理
                if (feedbackStatePopViewOnClickLister != null) {
                    feedbackStatePopViewOnClickLister.BeginHandle();
                }
                break;
            case R.id.tv_end_handle://结束处理
                if (feedbackStatePopViewOnClickLister != null) {
                    feedbackStatePopViewOnClickLister.EndHandle();
                }
                break;
            default:
                break;
        }
    }

    public FeedbackStatePopViewOnClickLister getFeedbackStatePopViewOnClickLister() {
        return feedbackStatePopViewOnClickLister;
    }

    public void setFeedbackStatePopViewOnClickLister(FeedbackStatePopViewOnClickLister feedbackStatePopViewOnClickLister) {
        this.feedbackStatePopViewOnClickLister = feedbackStatePopViewOnClickLister;
    }
}
