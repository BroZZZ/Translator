package com.translator.brozzz.translator.fragments;

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
import com.translator.brozzz.translator.adapters.HistoryRvAdapter;
import com.translator.brozzz.translator.entity.TranslationInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class HistoryFragment extends Fragment {
    @BindView(R.id.rv_history)
    RecyclerView mRvHistory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment,container,false);
        ButterKnife.bind(this, view);
        initRv();
        return view;
    }

    private void initRv() {
        mRvHistory.setAdapter(new HistoryRvAdapter(Realm.getDefaultInstance().where(TranslationInfo.class).findAll()));
        mRvHistory.setItemAnimator(new DefaultItemAnimator());
        mRvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
