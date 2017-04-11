package com.translator.brozzz.translator.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.translator.brozzz.translator.interfaces.ActionBarFragment;

import java.util.ArrayList;
import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private final List<ActionBarFragment> fragmentList = new ArrayList<>();
    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(ActionBarFragment fragment) {
        this.fragmentList.add(fragment);
    }

}
