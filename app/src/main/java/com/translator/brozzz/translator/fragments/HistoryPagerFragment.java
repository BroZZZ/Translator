package com.translator.brozzz.translator.fragments;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.activities.MainActivity;
import com.translator.brozzz.translator.adapters.HistoryTabPagerAdapter;
import com.translator.brozzz.translator.interfaces.ActionBarFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryPagerFragment extends ActionBarFragment implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private HistoryTabPagerAdapter historyTabPagerAdapter;
    private TabLayout mTabLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        ButterKnife.bind(this, view);
        initViewPager();
        initTabBar();
        return view;
    }

    private void initViewPager() {
        if (historyTabPagerAdapter == null) {
            historyTabPagerAdapter = new HistoryTabPagerAdapter(getChildFragmentManager());
            historyTabPagerAdapter.addFragment(new HistoryFragment());
            historyTabPagerAdapter.addFragment(new FavoriteFragment());
        }
        viewPager.setAdapter(historyTabPagerAdapter);
    }

    @Override
    public ActionBar setupSupportActionBarView(MainActivity activity, @LayoutRes int viewId) {
        ActionBar actionBar = super.setupSupportActionBarView(activity, viewId);
        initTabBar();
        return actionBar;
    }

    protected void initTabBar() {
        mTabLayout = (TabLayout) getActivity().findViewById(R.id.tl_history);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(viewPager);
            mTabLayout.addOnTabSelectedListener(this);
            mTabLayout.getTabAt(0).setText("History");
            mTabLayout.getTabAt(0).setText("History");
            mTabLayout.getTabAt(1).setText("Favorite");
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
