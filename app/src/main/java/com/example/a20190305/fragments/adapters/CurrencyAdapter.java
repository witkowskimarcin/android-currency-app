package com.example.a20190305.fragments.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a20190305.R;
import com.example.a20190305.models.Currency;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.PostViewHolder> {

    private List<Currency> dataList;

    public CurrencyAdapter(List<Currency> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_currency,viewGroup,false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        Currency item = dataList.get(i);

        postViewHolder.currency.setText(String.valueOf(item.getCurrency()));
        postViewHolder.code.setText(String.valueOf(item.getCode()));
    }

    @Override
    public int getItemCount(){
        if(dataList !=null){
            return dataList.size();
        } else {
            return 0;
        }
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
        public TextView currency, code;
        public RecyclerView comments;

        PostViewHolder(View view){
            super(view);
            currency = view. findViewById(R.id.currency_currency);
            code = view. findViewById(R.id.currency_code);
        }
    }
}
