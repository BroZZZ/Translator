package com.translator.brozzz.translator.presenter;

import android.content.Context;

import com.translator.brozzz.translator.adapters.HistoryRvAdapter;
import com.translator.brozzz.translator.entity.TranslationInfo;
import com.translator.brozzz.translator.interfaces.IHistoryActionClickListener;

import io.realm.Realm;
import io.realm.RealmResults;

public class HistoryPresenter implements IHistoryActionClickListener {
    private HistoryRvAdapter mRvHistoryAdapter;
    private Context mContext;
    private Realm mRealm;
    private boolean isOnlyFavorite;

    public HistoryPresenter(Context context, boolean onlyFavorite) {
        mContext = context;
        isOnlyFavorite = onlyFavorite;
        initRealm();
        initRvAdapter();
    }

    private void initRealm() {
        mRealm = Realm.getDefaultInstance();
    }

    private void initRvAdapter() {
        mRvHistoryAdapter = new HistoryRvAdapter(mContext,
                null,
                this);

    }

    public HistoryRvAdapter getRvHistoryAdapter() {
        return mRvHistoryAdapter;
    }

    @Override
    public void OnFavoriteClick(String originalText) {
        mRealm.beginTransaction();
        TranslationInfo realmTranslationInfo = mRealm.where(TranslationInfo.class)
                .equalTo("originalText", originalText)
                .findFirst();
        realmTranslationInfo.changeIsFavorite();
        mRealm.commitTransaction();
    }

    public void clearRealmCollection() {
        if (mRvHistoryAdapter.getData() != null) {
            mRealm.beginTransaction();
            for (TranslationInfo translationInfo : mRvHistoryAdapter.getData()) {
                translationInfo.getTranslation().deleteFromRealm();
                translationInfo.getDictionary().getDefinitionsList().deleteAllFromRealm();
                translationInfo.getDictionary().deleteFromRealm();
            }
            mRvHistoryAdapter.getData().deleteAllFromRealm();
            mRealm.commitTransaction();
        }
    }

    public void dismiss() {
        mRealm.close();
        mRvHistoryAdapter.updateData(null);
    }

    public void init() {
        if (mRealm.isClosed()) {
            mRealm = Realm.getDefaultInstance();
        }
        RealmResults<TranslationInfo> translationInfos;

        if (isOnlyFavorite) {
            translationInfos = mRealm.where(TranslationInfo.class)
                    .equalTo("isFavourite", isOnlyFavorite)
                    .findAll();
        } else {
            translationInfos = mRealm.where(TranslationInfo.class)
                    .findAll();
        }
        mRvHistoryAdapter.updateData(translationInfos);

    }
}
