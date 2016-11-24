package com.aboluo.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;

import java.util.List;

/**
 * Created by CJ on 2016/11/24.
 */

public class SecKillDetailAdapter extends FragmentPagerAdapter {
    private List<Fragment> mfragments;
    private List<String> mlist_title;
    public SecKillDetailAdapter(FragmentManager fm, List<Fragment> fragments, List<String> list_title) {
        super(fm);
        this.mfragments = fragments;
        this.mlist_title = list_title;
    }

    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position);
    }

    @Override
    public int getCount() {
        return mlist_title.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mlist_title.get(position);
    }
}
