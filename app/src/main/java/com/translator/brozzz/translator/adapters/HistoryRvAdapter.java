package com.translator.brozzz.translator.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.entity.TranslationInfo;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class HistoryRvAdapter extends RealmRecyclerViewAdapter<TranslationInfo, RecyclerView.ViewHolder> {

    public HistoryRvAdapter(@Nullable OrderedRealmCollection<TranslationInfo> data) {
        super(data, true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.translation_history_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TranslationInfo translationInfo = getItem(position);
        ((ViewHolder) holder).tv_originalText.setText(translationInfo.getOriginalText());
        ((ViewHolder) holder).tv_translatedText.setText(translationInfo.getTranslation().getTranslatedText());
        ((ViewHolder) holder).tv_lang.setText(translationInfo.getTranslation().getLang());
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_originalText;
        TextView tv_translatedText;
        TextView tv_lang;
        ImageView iv_favorite;

        ViewHolder(View view) {
            super(view);
            tv_originalText = (TextView) view.findViewById(R.id.tv_original_text);
            tv_translatedText = (TextView) view.findViewById(R.id.tv_translated_text);
            tv_lang = (TextView) view.findViewById(R.id.tv_lang);
            iv_favorite = (ImageView) view.findViewById(R.id.iv_favorite);
        }
    }
}
