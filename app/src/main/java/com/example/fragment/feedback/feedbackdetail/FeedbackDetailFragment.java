package com.example.fragment.feedback.feedbackdetail;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bean.Feedback;
import com.example.main.R;
import com.example.util.GlobalVariables;
import com.example.util.ToastUtil;
import com.example.util.advanced.FeedbackStatePopView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


/**
 * @author MW
 * @date 2019/4/21
 * <p>
 * 描述： 反馈信息详情
 */

public class FeedbackDetailFragment extends Fragment {

    public static final String TAG = "FeedbackDetailFragment";
    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.tv_includeHeaderRight)
    TextView mTvIncludeHeaderRight;
    @BindView(R.id.li_includeHeaderRight)
    LinearLayout mLiIncludeHeaderRight;
    @BindView(R.id.tv_founder)
    TextView mTvFounder;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_handle)
    TextView mTvHandle;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.iv_feedbacks)
    ImageView mIvFeedbacks;
    Unbinder unbinder;
    @BindView(R.id.iv_includeHeaderRight)
    ImageView mIvIncludeHeaderRight;


    private Feedback mFeedback;

    public FeedbackDetailFragment() {
        // Required empty public constructor
    }


    public static FeedbackDetailFragment newInstance(Feedback feedback) {
        FeedbackDetailFragment fragment = new FeedbackDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putSerializable(Feedback.class.getCanonicalName(), feedback);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mFeedback = (Feedback) bundle.getSerializable(Feedback.class.getCanonicalName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mTvIncludeHeaderTitle.setText("用户反馈");
        mLiIncludeHeaderRight.setVisibility(View.VISIBLE);
        mIvIncludeHeaderRight.setVisibility(View.VISIBLE);
        mTvTime.setText(mFeedback.getCreatedAt());
        mTvFounder.setText("创建人: " + mFeedback.getRealName());
        mTvPhone.setText("联系电话: " + mFeedback.getPhone());
        if (mFeedback.getState() == 1) {
            mTvHandle.setText("处理人: 暂无");
            mTvState.setText("处理状态: 暂未处理");
        } else {
            if (mFeedback.getState() == 2) {
                mTvHandle.setText("处理人:" + mFeedback.getReadName());
                mTvState.setText("处理状态: 正在处理");
            } else {
                mTvHandle.setText("处理人: " + mFeedback.getReadName());
                mTvState.setText("处理状态: 已处理");
            }
        }
        mTvContent.setText(mFeedback.getContent());
        if (mFeedback.getPicture().size() == 0) {
            mIvFeedbacks.setVisibility(View.GONE);
        } else {
            mIvFeedbacks.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(mFeedback.getPicture().get(0).getUrl())
                    .into(mIvFeedbacks);
        }
    }

    @OnClick({R.id.li_includeHeaderLeft, R.id.li_includeHeaderRight})
    protected void onClickView(View view) {
        switch (view.getId()) {
            case R.id.li_includeHeaderLeft://返回
                getActivity().onBackPressed();
                break;
            case R.id.li_includeHeaderRight://点击右边  更改问题状态
                FeedbackStatePopView feedbackStatePopView = new FeedbackStatePopView(getContext());
                feedbackStatePopView.setBackgroundDrawable(new ColorDrawable(0));
                feedbackStatePopView.setFeedbackStatePopViewOnClickLister(new FeedbackStatePopView.FeedbackStatePopViewOnClickLister() {
                    @Override
                    public void NotHandle() {//更改状态  未处理

                        Feedback feedback = new Feedback();
                        feedback.setReadName("");
                        feedback.setState(1);
                        feedback.update(mFeedback.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtil.showToast(getContext(), "修改成功");
                                    mTvHandle.setText("处理人: 暂无");
                                    mTvState.setText("处理状态: 暂未处理");
                                    feedbackStatePopView.dismiss();
                                } else {
                                    ToastUtil.showToast(getContext(), "更改状态成功");
                                }
                            }
                        });
                    }

                    @Override
                    public void BeginHandle() {//更改状态  开始处理
                        Feedback feedback = new Feedback();
                        feedback.setReadName(GlobalVariables.getUserNickName());
                        feedback.setState(2);
                        feedback.update(mFeedback.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtil.showToast(getContext(), "修改成功");
                                    mTvHandle.setText("处理人:" + GlobalVariables.getUserNickName());
                                    mTvState.setText("处理状态: 正在处理");
                                    feedbackStatePopView.dismiss();
                                } else {
                                    ToastUtil.showToast(getContext(), "更改状态成功");
                                }
                            }
                        });
                    }

                    @Override
                    public void EndHandle() {//更改状态    结束处理
                        Feedback feedback = new Feedback();
                        feedback.setReadName(GlobalVariables.getUserNickName());
                        feedback.setState(3);
                        feedback.update(mFeedback.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ToastUtil.showToast(getContext(), "修改成功");
                                    mTvHandle.setText("处理人:" + GlobalVariables.getUserNickName());
                                    mTvState.setText("处理状态: 已处理");
                                    feedbackStatePopView.dismiss();
                                } else {
                                    ToastUtil.showToast(getContext(), "更改状态成功");
                                }
                            }
                        });
                    }
                });
                //feedbackStatePopView.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.RIGHT+Gravity.TOP,0,0);
                feedbackStatePopView.showAsDropDown(getActivity().getWindow().findViewById(R.id.li_includeHeaderRight), 10, 10);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
