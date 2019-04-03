package com.example.fragment.usercenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fragment.note.AddNoteFragment;
import com.example.fragment.note.UserWriteNotesFragment;
import com.example.main.R;
import com.example.util.Constant;


/**
 * @author MW
 * @date 2019/3/25
 * <p>
 * 描述： 个人中心托管Activity
 */

public class UserCenterActivity extends AppCompatActivity {
    public static final String  NOTE_TYPE="yj_note_type";//1.文字 2.图片  3.视频   4.录音

    private Fragment mFragment;
    private String mResId;
    private int mNoteType;//1.文字 2.图片  3.视频   4.录音

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        mResId=getIntent().getStringExtra(Constant.FRAGMENT_ID);
        mNoteType=getIntent().getIntExtra(UserCenterActivity.NOTE_TYPE, 5);//5 什么
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
            case ModifyUserCenterFragment.TAG://修改个人信息界面
                mFragment=ModifyUserCenterFragment.newInstance();
                break;
            case UserWriteNotesFragment.TAG://写笔记页面
                mFragment=UserWriteNotesFragment.newInstance();
                break;
            case AddNoteFragment.TAG:
                mFragment=AddNoteFragment.newInstance(mNoteType);
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
