package com.translator.brozzz.translator.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.fragments.PagerFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            setCurrentFragment(new PagerFragment(), false, null);
        }
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
}
