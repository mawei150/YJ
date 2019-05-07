package com.example.fragment.usercenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bean.note;
import com.example.fragment.MainFragment;
import com.example.fragment.allnote.searchnote.SearchNoteListFragment;
import com.example.fragment.note.AddNoteFragment;
import com.example.fragment.note.NoteDetailFragment;
import com.example.fragment.note.TodayNotesFragment;
import com.example.login.password.ResetPasswordFragment;
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
    public static final String  OPERATION_TYPE="yj_operation_note_type";//对笔记操作类型  1，表示查看  2.表示修改

    private Fragment mFragment;
    private String mResId;
    private int mNoteType;//1.文字 2.图片  3.视频   4.录音
    private int mOperationType;//1.查看  2.修改
    private note mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        mResId=getIntent().getStringExtra(Constant.FRAGMENT_ID);
        mNoteType=getIntent().getIntExtra(UserCenterActivity.NOTE_TYPE, 5);//5 什么都不是
        mOperationType=getIntent().getIntExtra(UserCenterActivity.OPERATION_TYPE,5);
        mNote= (note) getIntent().getSerializableExtra(note.class.getCanonicalName());
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
            case TodayNotesFragment.TAG://今日笔记页面
                mFragment= TodayNotesFragment.newInstance();
                break;
            case AddNoteFragment.TAG://添加笔记
                mFragment=AddNoteFragment.newInstance(mNoteType);
                break;
            case NoteDetailFragment.TAG://笔记详情
                mFragment=NoteDetailFragment.newInstance(mNote,mOperationType);
                break;
            case SearchNoteListFragment.TAG://查找笔记
                mFragment=SearchNoteListFragment.newInstance();
                break;
            case ResetPasswordFragment.TAG://重置密码
                mFragment=ResetPasswordFragment.newInstance();
                break;
            case MainFragment.TAG://主Fragment
                mFragment=MainFragment.newInstance();
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
