package com.translator.brozzz.translator.fragments.history;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.adapters.HistoryTabPagerAdapter;
import com.translator.brozzz.translator.databinding.FragmentPagerBinding;
import com.translator.brozzz.translator.databinding.HistoryActionBarBinding;

public class HistoryPagerFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private FragmentPagerBinding mPagerBinding;
    private HistoryActionBarBinding mActionBarBinding;
    private HistoryTabPagerAdapter historyTabPagerAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPagerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_pager, container, false);
        mActionBarBinding = HistoryActionBarBinding.inflate(inflater, mPagerBinding.actionBarContainer, true);
        mActionBarBinding.ibDeleteAll.setOnClickListener(v -> historyTabPagerAdapter
                .getHistoryItem(mActionBarBinding.tlHistory.getSelectedTabPosition()).clearData());

        initViewPager();
        initTabBar();
        return mPagerBinding.getRoot();
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
        mPagerBinding.viewPager.setAdapter(historyTabPagerAdapter);
    }

    protected void initTabBar() {
        mActionBarBinding.tlHistory.setupWithViewPager(mPagerBinding.viewPager);
        mActionBarBinding.tlHistory.addOnTabSelectedListener(this);
        mActionBarBinding.tlHistory.getTabAt(0).setText("History");
        mActionBarBinding.tlHistory.getTabAt(1).setText("Favorite");
        onTabSelected(mActionBarBinding.tlHistory.getTabAt(0));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        HistoryFragment selectedTab = historyTabPagerAdapter.getHistoryItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
