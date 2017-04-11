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

    public HistoryPresenter(Context context, boolean onlyFavorite) {
        mContext = context;
        initRealm();
        initRvAdapter(onlyFavorite);
    }

    private void initRealm() {
        mRealm = Realm.getDefaultInstance();
    }

    private void initRvAdapter(boolean onlyFavorite) {
        RealmResults<TranslationInfo> translationInfos;
        if (onlyFavorite) {
            translationInfos = mRealm.where(TranslationInfo.class)
                    .equalTo("isFavourite", true)
                    .findAll();
        } else {
            translationInfos = mRealm.where(TranslationInfo.class)
                    .findAll();
        }

        mRvHistoryAdapter = new HistoryRvAdapter(mContext,
                translationInfos,
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
            for (TranslationInfo translationInfo :mRvHistoryAdapter.getData()) {
                translationInfo.getTranslation().deleteFromRealm();
                translationInfo.getDictionary().getDefinitionsList().deleteAllFromRealm();
                translationInfo.getDictionary().deleteFromRealm();
            }
            mRvHistoryAdapter.getData().deleteAllFromRealm();
            mRealm.commitTransaction();
        }
    }
}
