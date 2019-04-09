package com.example.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author MW
 * @date 2019/4/8
 * <p>
 * 描述：Viewpager适配
 */


public class FixPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;

    private List<Fragment> fragments = null;

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public FixPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (fragments.isEmpty()) {
            return 0;
        }
        return fragments.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) super.instantiateItem(container, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        try {
            super.finishUpdate(container);
        } catch (NullPointerException nullPointerException) {
            System.out.println("Catch the NullPointerException in FragmentPagerAdapter.finishUpdate");
        }
    }
}
