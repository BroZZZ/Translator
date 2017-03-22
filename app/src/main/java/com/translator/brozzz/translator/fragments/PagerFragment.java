package com.translator.brozzz.translator.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
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

    @BindView(R.id.view_pager)  ViewPager viewPager;


    PagerAdapter pagerAdapter;

    @Override
    public void onResume() {
        super.onResume();
        int selectedTabIndex = ((MainActivity) getActivity()).getTabLayout()
                .getSelectedTabPosition();

//        switch (selectedTabIndex) {
//            case 0:
//                ((MainActivity) getActivity()).getToolBar().setTitle(getString(R.string.genres_title));
//                break;
//            case 1:
//                ((MainActivity) getActivity()).getToolBar().setTitle(getString(R.string.popular_title));
//                break;
//            case 2:
//                ((MainActivity) getActivity()).getToolBar().setTitle(getString(R.string.latest_title));
//                break;
//            case 3:
//                ((MainActivity) getActivity()).getToolBar().setTitle(getString(R.string.my_music_title));
//                break;
//            default:
//                break;
//        }
    }

    private void initViewPager() {
        if (pagerAdapter == null) {
            pagerAdapter = new PagerAdapter(getChildFragmentManager());
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

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    private void initTabBar(@NotNull TabLayout tabLayout) {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_translate_white_24dp);
        tabLayout.getTabAt(0).getIcon().mutate()
                .setColorFilter(ResourcesCompat.getColor(getResources(),
                        R.color.colorSelectedTab, getContext().getTheme()),
                        PorterDuff.Mode.MULTIPLY);

        tabLayout.getTabAt(1).setIcon(R.drawable.ic_settings_white_24dp);
        tabLayout.getTabAt(1).getIcon().mutate()
                .setColorFilter(getResources().getColor(R.color.colorUnselectedTab), PorterDuff.Mode.MULTIPLY);

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (isAdded()) {
            if (tab != null && tab.getIcon() != null) {
                tab.getIcon().mutate()
                        .setColorFilter(ResourcesCompat.getColor(getResources(),
                                R.color.colorSelectedTab, getContext().getTheme()),
                                PorterDuff.Mode.MULTIPLY);

//                String tabName = getTabTitle(tab);
//                ((MainActivity) getActivity()).getToolBar().setTitle(tabName);
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (tab != null && tab.getIcon() != null) {
            tab.getIcon().mutate()
                    .setColorFilter(getResources().getColor(R.color.colorUnselectedTab), PorterDuff.Mode.MULTIPLY);
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
