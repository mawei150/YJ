package com.example.fragment.seting;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fragment.seting.password.ModifyPasswordFragment;
import com.example.fragment.seting.phone.BindPasswordFragment;
import com.example.fragment.seting.setlist.SetListFragment;
import com.example.main.R;
import com.example.util.Constant;

/**
 * @author MW
 * @date 2019/4/23
 * <p>
 * 描述： 设置托管界面
 */

public class SetActivity extends AppCompatActivity {

    public static final String PHONE_STATE="YJ_phone_state";//手机号码  存在状态   1.存在  2，不存在  3.显示绑定手机号

    private Fragment mFragment;
    private String mResId;
    private int mPhoneState;//账号电话状态  1.存在 2.不存在 3.显示绑定手机号
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        mResId = getIntent().getStringExtra(Constant.FRAGMENT_ID);
        mPhoneState=getIntent().getIntExtra(PHONE_STATE,5);//5 什么也不是
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
            case  SetListFragment.TAG://设置列表
                mFragment=SetListFragment.newInstance();
                break;
            case  ModifyPasswordFragment.TAG://修改密码
                mFragment= ModifyPasswordFragment.newInstance();
                break;
            case BindPasswordFragment.TAG://绑定手机号
                mFragment=BindPasswordFragment.newInstance(mPhoneState);
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
