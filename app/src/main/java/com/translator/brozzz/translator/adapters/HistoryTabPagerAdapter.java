package com.translator.brozzz.translator.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.translator.brozzz.translator.fragments.history.HistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class HistoryTabPagerAdapter extends FragmentPagerAdapter {
    private final List<HistoryFragment> fragmentList = new ArrayList<>();

    public HistoryTabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public HistoryFragment getHistoryItem(int position){
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(HistoryFragment fragment) {
        this.fragmentList.add(fragment);
    }

}
