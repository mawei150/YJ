package com.example.fragment.allnote;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fragment.adapter.FixPagerAdapter;
import com.example.fragment.allnote.notelist.NoteListFragment;
import com.example.fragment.allnote.searchnote.SearchNoteListFragment;
import com.example.fragment.usercenter.UserCenterActivity;
import com.example.main.R;
import com.example.util.Constant;
import com.example.util.TabLayoutUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.util.V;

public class AllNoteActivity extends AppCompatActivity {

    public static final String ALL_NOTE_TYPE = "All_note_type";

    @BindView(R.id.tv_includeHeaderTitle)
    TextView mTvIncludeHeaderTitle;
    @BindView(R.id.li_includeHeaderLeft)
    LinearLayout mLiIncludeHeaderLeft;
    @BindView(R.id.tab_face_layout)
    TabLayout mTabLayout;
    @BindView(R.id.fragment_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.iv_includeHeaderRight)
    ImageView mIvIncludeHeaderRight;
    @BindView(R.id.li_includeHeaderRight)
    LinearLayout mLiIncludeHeaderRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_note);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvIncludeHeaderTitle.setText("所有笔记");
        mLiIncludeHeaderRight.setVisibility(View.VISIBLE);
        mIvIncludeHeaderRight.setVisibility(View.VISIBLE);
        mIvIncludeHeaderRight.setImageDrawable(getResources().getDrawable(R.drawable.ic_svg_note_search));

        String[] titles = {"文字", "图片", "视频"};
        FixPagerAdapter pagerAdapter = new FixPagerAdapter(getSupportFragmentManager());
        List<Fragment> fragmentList = new ArrayList<>(titles.length);
        fragmentList.add(NoteListFragment.newInstance(1));//文字
        fragmentList.add(NoteListFragment.newInstance(2));//文字
        fragmentList.add(NoteListFragment.newInstance(3));//文字
      /*  fragmentList.add(TodayNotesFragment.newInstance());//图片
        fragmentList.add(TodayNotesFragment.newInstance());//视频*/

     /*   for (int i = 0; i < titles.length; i++) {
            fragmentList.add(NoteListFragment.newInstance(i + 1));
        }*/


        pagerAdapter.setFragments(fragmentList);
        pagerAdapter.setTitles(titles);

        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.post(() -> TabLayoutUtil.setIndicator(mTabLayout, 40, 40));
        //loading.setVisibility(View.GONE);

        mViewPager.setCurrentItem(0);

    }

    @OnClick({R.id.li_includeHeaderLeft,R.id.li_includeHeaderRight})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_includeHeaderLeft://返回按钮
                onBackPressed();
                break;
            case R.id.li_includeHeaderRight://搜索按钮
                Intent intent=new Intent(getApplicationContext(), UserCenterActivity.class);
                intent.putExtra(Constant.FRAGMENT_ID, SearchNoteListFragment.TAG);
                startActivity(intent);
            default:
                break;
        }
    }

}
