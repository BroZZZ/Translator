package com.translator.brozzz.translator.adapters;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.translator.brozzz.translator.interfaces.TabFragment;

import java.util.ArrayList;
import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private final List<TabFragment> fragmentList = new ArrayList<>();
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    public TabFragment getItem(TabLayout.Tab tab){
        return fragmentList.get(tab.getPosition());
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(TabFragment fragment) {
        this.fragmentList.add(fragment);
    }

}
