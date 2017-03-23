package com.translator.brozzz.translator.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.fragments.PagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            setCurrentFragment(new PagerFragment(), false, null);
        }
        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    public void setCurrentFragment(Fragment fragment, boolean addToBackStack, String name) {
        if (!isFinishing()) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.replace(R.id.fragment_container, fragment);
            if (addToBackStack) {
                ft.addToBackStack(name);
            }
            ft.commit();
        }
    }

    public TabLayout getTabLayout() {
        return tabLayout;
    }
}
