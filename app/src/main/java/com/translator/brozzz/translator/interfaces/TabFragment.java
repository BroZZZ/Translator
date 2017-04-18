package com.translator.brozzz.translator.interfaces;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

public abstract class TabFragment extends Fragment implements TabLayout.OnTabSelectedListener{
    @Override
    public abstract void onTabSelected(TabLayout.Tab tab);

    @Override
    public abstract void onTabUnselected(TabLayout.Tab tab);

    @Override
    public abstract void onTabReselected(TabLayout.Tab tab);

}
