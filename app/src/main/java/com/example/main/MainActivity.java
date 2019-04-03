package com.example.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.fragment.MainFragment;
import com.example.util.GlobalVariables;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author MW
 * @date 2019/3/19
 * <p>
 * 描述：暂时作为主页面
 */

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.tv_temporary)
    TextView mTvTemporary;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private MainFragment mLeftFragment;
    private FragmentManager mFManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFManager = getSupportFragmentManager();
        mLeftFragment = (MainFragment) mFManager.findFragmentById(R.id.fg_left_menu);//左滑的fragment

        initView();
    }

    private void initView() {
        mDrawerLayout.setScrimColor(getResources().getColor(R.color.white));
        mLeftFragment.setDrawerLayout(mDrawerLayout);


        //获取个人信息

        mTvTemporary.setText(GlobalVariables.getUsername()+"");

    }

}
