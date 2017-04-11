package com.translator.brozzz.translator.presenter;

import android.content.Context;

import com.translator.brozzz.translator.adapters.HistoryRvAdapter;
import com.translator.brozzz.translator.entity.TranslationInfo;
import com.translator.brozzz.translator.fragments.HistoryFragment;

import io.realm.Realm;

public class HistoryPresenter {
    private HistoryRvAdapter mRvHistoryAdapter;
    private Context mContext;
    private HistoryFragment mView;
    private Realm mRealm;

    public HistoryPresenter(Context context, HistoryFragment view) {
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
                mRealm.where(TranslationInfo.class).findAll(),
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
