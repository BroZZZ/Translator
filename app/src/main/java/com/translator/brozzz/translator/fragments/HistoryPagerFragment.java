package com.translator.brozzz.translator.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.activities.MainActivity;
import com.translator.brozzz.translator.adapters.HistoryTabPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryPagerFragment extends Fragment {
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
        initSupportActionBarView();
        initViewPager();
        return view;
    }

    private void initSupportActionBarView() {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null)
            actionBar.setCustomView(R.layout.translate_action_bar);
    }

    private void initViewPager() {
        if (historyTabPagerAdapter == null) {
            historyTabPagerAdapter = new HistoryTabPagerAdapter(getChildFragmentManager());
            historyTabPagerAdapter.addFragment(new HistoryFragment());
            historyTabPagerAdapter.addFragment(new FavoriteFragment());
        }
        viewPager.setAdapter(historyTabPagerAdapter);
    }

    private void initTabBar() {
//        mTabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
//        mTabLayout.setupWithViewPager(viewPager);
//        mTabLayout.addOnTabSelectedListener(this);
//
//        viewPager.setCurrentItem(1);
    }

}
