package com.translator.brozzz.translator.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.interfaces.TabHistoryFragment;
import com.translator.brozzz.translator.presenter.HistoryPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryFragment extends TabHistoryFragment {

    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;

    private boolean mOnlyFavourite;

    HistoryPresenter mPresenter;

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
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        ButterKnife.bind(this, view);
        initRv();
        return view;
    }

    public void initRv() {
        mRvHistory.setAdapter(mPresenter.getRvHistoryAdapter());
        mRvHistory.setItemAnimator(new DefaultItemAnimator());
        mRvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void setDeleteImageButton(ImageButton imageButton) {
        super.setDeleteImageButton(imageButton);
        ib_delete.setOnClickListener(view -> mPresenter.clearRealmCollection());
    }
}
