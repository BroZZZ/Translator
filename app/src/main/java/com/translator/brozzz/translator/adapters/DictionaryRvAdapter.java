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

    public DictionaryRvAdapter(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.dictionary_list_item, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).synonyms.setText(dictionary.getSynonyms(0, position));
        ((ViewHolder) holder).mean.setText(dictionary.getDef().get(0).getTr().
                get(position).getMeansString());
        if ( ((ViewHolder) holder).mean.getText().equals("")){
            ((ViewHolder) holder).mean.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (dictionary == null
                || dictionary.getDef().size() == 0
                || dictionary.getDef().get(0).getTr().size() == 0) {
            return 0;
        }

        return dictionary.getDef().get(0).getTr().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView synonyms;
        TextView mean;

        ViewHolder(View view) {
            super(view);
            synonyms = (TextView) view.findViewById(R.id.synonyms);
            mean = (TextView) view.findViewById(R.id.mean);
        }
    }
}
