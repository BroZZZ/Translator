package com.translator.brozzz.translator.fragments.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.presenter.HistoryPresenter;
import com.translator.brozzz.translator.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryFragment extends Fragment {

    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;

    private boolean mOnlyFavourite;

    private HistoryPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mOnlyFavourite = bundle.getBoolean(Utils.IntentExtras.FAVORITE_ONLY, false);
        }
        mPresenter = new HistoryPresenter(getContext(), mOnlyFavourite);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        initRv();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.init();
    }

    public void initRv() {
        mRvHistory.setAdapter(mPresenter.getRvHistoryAdapter());
        mRvHistory.setItemAnimator(new DefaultItemAnimator());
        mRvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void clearData(){
        mPresenter.clearRealmCollection();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.dismiss();
    }
}
