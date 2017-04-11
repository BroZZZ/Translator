package com.translator.brozzz.translator.interfaces;

import android.support.v4.app.Fragment;
import android.widget.ImageButton;

public abstract class TabHistoryFragment extends Fragment {
    protected ImageButton ib_delete;
    public void setDeleteImageButton(ImageButton imageButton){
        ib_delete = imageButton;
    }
}
