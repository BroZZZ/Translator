package com.translator.brozzz.translator.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.activities.MainActivity;
import com.translator.brozzz.translator.adapters.HistoryTabPagerAdapter;
import com.translator.brozzz.translator.interfaces.TabHistoryFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryPagerFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tl_history)
    TabLayout mTabLayout;

    private HistoryTabPagerAdapter historyTabPagerAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);

        ViewGroup insertPoint = (ViewGroup) view.findViewById(R.id.action_bar_container);
        inflater.inflate(R.layout.history_action_bar, insertPoint, true);
        ButterKnife.bind(this, view);
        initViewPager();
        initTabBar();
        return view;
    }

    private void initViewPager() {
        if (historyTabPagerAdapter == null) {

            historyTabPagerAdapter = new HistoryTabPagerAdapter(getChildFragmentManager());
            historyTabPagerAdapter.addFragment(new HistoryFragment());

            Bundle favoriteBundle = new Bundle();
            favoriteBundle.putBoolean("onlyFavorite", true);
            HistoryFragment favourite = new HistoryFragment();
            favourite.setArguments(favoriteBundle);
            historyTabPagerAdapter.addFragment(favourite);
        }
        viewPager.setAdapter(historyTabPagerAdapter);
    }

    protected void initTabBar() {
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.addOnTabSelectedListener(this);
        mTabLayout.getTabAt(0).setText("History");
        mTabLayout.getTabAt(1).setText("Favorite");
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            TabHistoryFragment selectedTab = (TabHistoryFragment) historyTabPagerAdapter.getItem(tab.getPosition());
            selectedTab.setDeleteImageButton((ImageButton) actionBar.getCustomView().findViewById(R.id.ib_deleteAll));
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
