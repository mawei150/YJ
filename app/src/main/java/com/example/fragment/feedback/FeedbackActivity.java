package com.example.fragment.feedback;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bean.Feedback;
import com.example.fragment.feedback.addfeedback.AddFeedbackFragment;
import com.example.fragment.feedback.feedbackdetail.FeedbackDetailFragment;
import com.example.fragment.feedback.feedbacklist.FeedbackListFragment;
import com.example.main.R;
import com.example.util.Constant;

/**
 * @author MW
 * @date 2019/4/21
 * <p>
 * 描述：  意见反馈托管Activity
 */

public class FeedbackActivity extends AppCompatActivity {

    private Fragment mFragment;
    private String mResId;
    private Feedback mFeedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        mResId = getIntent().getStringExtra(Constant.FRAGMENT_ID);
        mFeedback= (Feedback) getIntent().getSerializableExtra(Feedback.class.getCanonicalName());
        initView();
    }

    private void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_content, crateFragmentByResId(mResId));
        fragmentTransaction.commit();
    }

    private Fragment crateFragmentByResId(String mResId) {
        mFragment=null;
        switch (mResId) {
            case AddFeedbackFragment.TAG://添加反馈
                mFragment=AddFeedbackFragment.newInstance();
                break;
            case FeedbackListFragment.TAG://反馈列表
                mFragment=FeedbackListFragment.newInstance();
                break;
            case FeedbackDetailFragment.TAG://反馈详情
                mFragment=FeedbackDetailFragment.newInstance(mFeedback);
                break;
            default:
                break;
        }
        return mFragment;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if (mFragment != null) {
            mFragment = null;
        }*/
    }
}
