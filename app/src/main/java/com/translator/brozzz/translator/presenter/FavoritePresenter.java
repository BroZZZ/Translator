package com.translator.brozzz.translator.presenter;

import android.content.Context;

import com.translator.brozzz.translator.adapters.HistoryRvAdapter;
import com.translator.brozzz.translator.entity.TranslationInfo;
import com.translator.brozzz.translator.fragments.FavoriteFragment;

import io.realm.Realm;

public class FavoritePresenter {
    private HistoryRvAdapter mRvHistoryAdapter;
    private Context mContext;
    private FavoriteFragment mView;
    private Realm mRealm;

    public FavoritePresenter(Context context, FavoriteFragment view) {
        mView = view;
        mContext = context;
        initRealm();
        initRvAdapter();
    }

    private void initRealm() {
        mRealm = Realm.getDefaultInstance();
    }

    private void initRvAdapter() {
        mRvHistoryAdapter = new HistoryRvAdapter(mContext,
                mRealm.where(TranslationInfo.class).equalTo("isFavourite", true).findAll(),
                originalText ->  {
                    mRealm.beginTransaction();
                    TranslationInfo realmTranslationInfo = mRealm.where(TranslationInfo.class)
                            .equalTo("originalText", originalText)
                            .findFirst();
                    realmTranslationInfo.changeIsFavorite();
                    mRealm.commitTransaction();

                });
    }

    public HistoryRvAdapter getRvHistoryAdapter() {
        return mRvHistoryAdapter;
    }

}
