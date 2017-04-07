package com.translator.brozzz.translator.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.translator.brozzz.translator.R;
import com.translator.brozzz.translator.entity.dictionary.Dictionary;


public class DictionaryRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Dictionary dictionary;

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
        notifyDataSetChanged();
    }

    public void clear() {
        setDictionary(null);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.dictionary_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).synonyms.setText(dictionary.getDefinition(position).getSynonyms());
        ((ViewHolder) holder).position.setText(Integer.toString(position + 1));
        ((ViewHolder) holder).mean.setText(dictionary.getDefinition(position).getMeans());
        if (((ViewHolder) holder).mean.getText().equals("")) {
            ((ViewHolder) holder).mean.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (dictionary == null) {
            return 0;
        }

        return dictionary.getDefinitionSize();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView synonyms;
        TextView mean;
        TextView position;

        ViewHolder(View view) {
            super(view);
            synonyms = (TextView) view.findViewById(R.id.synonyms);
            mean = (TextView) view.findViewById(R.id.mean);
            position = (TextView) view.findViewById(R.id.position);
        }
    }
}
