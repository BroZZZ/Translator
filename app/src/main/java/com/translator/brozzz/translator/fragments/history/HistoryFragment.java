package com.translator.brozzz.translator.fragments.history;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.databinding.HistoryFragmentBinding;
import com.translator.brozzz.translator.presenter.HistoryPresenter;

public class HistoryFragment extends Fragment {

    HistoryFragmentBinding mHistoryBinding;
    private boolean mOnlyFavourite;

    private HistoryPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mOnlyFavourite = bundle.getBoolean("onlyFavorite", false);
        }
        mPresenter = new HistoryPresenter(getContext(), mOnlyFavourite);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHistoryBinding = DataBindingUtil.inflate(inflater, R.layout.history_fragment, container, false);
        initRv();
        return mHistoryBinding.getRoot();
    }

    public void initRv() {
        mHistoryBinding.rvHistory.setAdapter(mPresenter.getRvHistoryAdapter());
        mHistoryBinding.rvHistory.setItemAnimator(new DefaultItemAnimator());
        mHistoryBinding.rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void clearData() {
        mPresenter.clearRealmCollection();
    }
}
