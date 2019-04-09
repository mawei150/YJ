package com.example.fragment.shownote;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fragment.shownote.addshow.AddShowNoteFragment;
import com.example.fragment.shownote.show.ShowNoteFragment;
import com.example.main.R;
import com.example.util.Constant;

/**
 * @author MW
 * @date 2019/4/9
 * <p>
 * 描述： 社区  托管Activity
 */

public class ShowNoteActivity extends AppCompatActivity {

    private Fragment mFragment;
    private String mResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        mResId=getIntent().getStringExtra(Constant.FRAGMENT_ID);
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
            case ShowNoteFragment.TAG://展示社区
                mFragment=ShowNoteFragment.newInstance();
                break;
            case AddShowNoteFragment.TAG://添加意见
                mFragment=AddShowNoteFragment.newInstance();
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
