package com.translator.brozzz.translator.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.adapters.MainPagerAdapter;
import com.translator.brozzz.translator.fragments.history.HistoryPagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagerFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    private static final int HISTORY_FRAGMENT_POSITION = 0;
    private static final int TRANSLATE_FRAGMENT_POSITION = 1;
    private static final int SETTINGS_FRAGMENT_POSITION = 2;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    MainPagerAdapter mainPagerAdapter;

    private TabLayout mTabLayout;


    private void initViewPager() {
        if (mainPagerAdapter == null) {
            mainPagerAdapter = new MainPagerAdapter(getChildFragmentManager());
            mainPagerAdapter.addFragment(new HistoryPagerFragment());
            mainPagerAdapter.addFragment(new TranslateFragment());
            mainPagerAdapter.addFragment(new SettingFragment());
        }
        viewPager.setAdapter(mainPagerAdapter);
    }

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

    private void initTabBar() {
        mTabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.addOnTabSelectedListener(this);

        setTabIconParams(mTabLayout, HISTORY_FRAGMENT_POSITION, R.drawable.ic_bookmark_white_24dp);
        setTabIconParams(mTabLayout, TRANSLATE_FRAGMENT_POSITION, R.drawable.ic_translate_white_24dp);
        setTabIconParams(mTabLayout, SETTINGS_FRAGMENT_POSITION, R.drawable.ic_settings_white_24dp);

        viewPager.setCurrentItem(1);
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    private void setTabIconParams(@NonNull TabLayout tabLayout, int tabIndex, @DrawableRes int iconId) {
        tabLayout.getTabAt(tabIndex).setIcon(iconId);

        tabLayout.getTabAt(tabIndex).getIcon().mutate()
                .setColorFilter(ResourcesCompat.getColor(getResources(),
                        R.color.colorUnselectedTab, getContext().getTheme()),
                        PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (isAdded()) {
            if (tab != null) {
                if (tab.getIcon() != null) {
                    tab.getIcon().mutate()
                            .setColorFilter(ResourcesCompat.getColor(getResources(),
                                    R.color.colorSelectedTab, getContext().getTheme()),
                                    PorterDuff.Mode.MULTIPLY);
                }
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (tab != null && tab.getIcon() != null) {
            tab.getIcon().mutate()
                    .setColorFilter(ResourcesCompat.getColor(getResources(),
                            R.color.colorUnselectedTab, getContext().getTheme()),
                            PorterDuff.Mode.MULTIPLY);
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
