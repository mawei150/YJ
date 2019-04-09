package com.example.fragment.allnote;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fragment.adapter.FixPagerAdapter;
import com.example.fragment.allnote.notelist.NoteListFragment;
import com.example.fragment.note.NoteDetailFragment;
import com.example.fragment.note.TodayNotesFragment;
import com.example.main.R;
import com.example.util.TabLayoutUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_note);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvIncludeHeaderTitle.setText("所有笔记");
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

    @OnClick({R.id.li_includeHeaderLeft})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.li_includeHeaderLeft:
                onBackPressed();
                break;
            default:
                break;
        }
    }

}
