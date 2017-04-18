package com.translator.brozzz.translator.fragments.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.adapters.HistoryTabPagerAdapter;
import com.translator.brozzz.translator.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryPagerFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tl_history)
    TabLayout mTabLayout;
    @BindView(R.id.ib_deleteAll)
    ImageView ibDeleteAll;

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

        ibDeleteAll.setOnClickListener(v -> historyTabPagerAdapter
                .getHistoryItem(mTabLayout.getSelectedTabPosition()).clearData());

        initViewPager();
        initTabBar();
        return view;
    }

    private void initViewPager() {
        if (historyTabPagerAdapter == null) {

            historyTabPagerAdapter = new HistoryTabPagerAdapter(getChildFragmentManager());
            historyTabPagerAdapter.addFragment(new HistoryFragment());

            Bundle favoriteBundle = new Bundle();
            favoriteBundle.putBoolean(Utils.IntentExtras.FAVORITE_ONLY, true);
            HistoryFragment favourite = new HistoryFragment();
            favourite.setArguments(favoriteBundle);
            historyTabPagerAdapter.addFragment(favourite);
        }
        viewPager.setAdapter(historyTabPagerAdapter);
    }

    protected void initTabBar() {
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.addOnTabSelectedListener(this);
        mTabLayout.getTabAt(0).setText(getString(R.string.history_title));
        mTabLayout.getTabAt(1).setText(getString(R.string.favorite_title));
        onTabSelected(mTabLayout.getTabAt(0));
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
