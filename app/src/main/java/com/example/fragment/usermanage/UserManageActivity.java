package com.example.fragment.usermanage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fragment.usermanage.userlist.UserListFragment;
import com.example.main.R;
import com.example.util.Constant;

/**
 * @author MW
 * @date 2019/4/25
 * <p>
 * 描述： 用户管理托管界面
 */

public class UserManageActivity extends AppCompatActivity {
    private Fragment mFragment;
    private String mResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        mResId = getIntent().getStringExtra(Constant.FRAGMENT_ID);
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
            case UserListFragment.TAG://用户管理
                mFragment=UserListFragment.newInstance();
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
