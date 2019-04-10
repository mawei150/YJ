package com.example.fragment.notice;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bean.Notice;
import com.example.fragment.notice.noticedetail.NoticeDetailFragment;
import com.example.fragment.notice.noticelist.NoticeListFragment;
import com.example.fragment.shownote.addshow.AddShowNoteFragment;
import com.example.fragment.shownote.show.ShowNoteFragment;
import com.example.main.R;
import com.example.util.Constant;

public class NoticeActivity extends AppCompatActivity {

    private Fragment mFragment;
    private String mResId;
    private Notice mNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        mResId = getIntent().getStringExtra(Constant.FRAGMENT_ID);
        mNotice= (Notice) getIntent().getSerializableExtra(Notice.class.getCanonicalName());
        initView();
    }

    private void initView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_content, crateFragmentByResId(mResId));
        fragmentTransaction.commit();
    }

    private Fragment crateFragmentByResId(String mResId) {
        switch (mResId) {
            case NoticeListFragment.TAG://公告列表
                mFragment=NoticeListFragment.newInstance();
                break;
            case NoticeDetailFragment.TAG:
                mFragment=NoticeDetailFragment.newInstance(mNotice);
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
