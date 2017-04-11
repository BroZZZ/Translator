package com.translator.brozzz.translator.interfaces;

import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.translator.brozzz.translator.activities.MainActivity;

public abstract class ActionBarFragment extends Fragment {

    public ActionBar setupSupportActionBarView(MainActivity activity, @LayoutRes int viewId) {
        if (activity != null) {
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null)
                actionBar.setCustomView(viewId);
            return actionBar;
        }
        return null;
    }

}
