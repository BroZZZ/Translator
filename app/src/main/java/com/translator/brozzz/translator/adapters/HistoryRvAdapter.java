package com.translator.brozzz.translator.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.entity.TranslationInfo;
import com.translator.brozzz.translator.interfaces.IOnFavoriteClickListener;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class HistoryRvAdapter extends RealmRecyclerViewAdapter<TranslationInfo, RecyclerView.ViewHolder> {

    private IOnFavoriteClickListener mListener;
    private Context mContext;

    public HistoryRvAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<TranslationInfo> data, IOnFavoriteClickListener listener) {
        super(data, true);
        mContext = context;
        mListener = listener;
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
        ((ViewHolder) holder).tv_lang.setText(translationInfo.getTranslation().getLang().toUpperCase());
        if (translationInfo.isFavourite()){
            ((ViewHolder) holder).iv_favorite.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));
        } else {
            ((ViewHolder) holder).iv_favorite.setColorFilter(ContextCompat.getColor(mContext, R.color.colorUnselectedTab));
        }
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
            iv_favorite.setOnClickListener(v ->
                    mListener.OnClick(tv_originalText.getText().toString()));
        }
    }
}
