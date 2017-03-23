package com.translator.brozzz.translator.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.activities.MainActivity;
import com.translator.brozzz.translator.adapters.PagerAdapter;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagerFragment extends Fragment implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    private void initViewPager() {
        if (pagerAdapter == null) {
            pagerAdapter = new PagerAdapter(getChildFragmentManager());
            pagerAdapter.addFragment(new HistoryFragment());
            pagerAdapter.addFragment(new TranslateFragment());
            pagerAdapter.addFragment(new SettingFragment());
        }
        viewPager.setAdapter(pagerAdapter);
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
        initTabBar(((MainActivity) getActivity()).getTabLayout());
        return view;
    }

    private void initTabBar(@NotNull TabLayout tabLayout) {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(this);

        setTabIconParams(tabLayout, 0, R.drawable.ic_bookmark_white_24dp);
        setTabIconParams(tabLayout, 1, R.drawable.ic_translate_white_24dp);
        setTabIconParams(tabLayout, 2, R.drawable.ic_settings_white_24dp);

        viewPager.setCurrentItem(1);
    }

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    private void setTabIconParams(@NotNull TabLayout tabLayout, int tabIndex, @DrawableRes int iconId) {
        tabLayout.getTabAt(tabIndex).setIcon(iconId);

        tabLayout.getTabAt(tabIndex).getIcon().mutate()
                .setColorFilter(ResourcesCompat.getColor(getResources(),
                        R.color.colorUnselectedTab, getContext().getTheme()),
                        PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (isAdded()) {
            if (tab != null && tab.getIcon() != null) {
                tab.getIcon().mutate()
                        .setColorFilter(ResourcesCompat.getColor(getResources(),
                                R.color.colorSelectedTab, getContext().getTheme()),
                                PorterDuff.Mode.MULTIPLY);
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
