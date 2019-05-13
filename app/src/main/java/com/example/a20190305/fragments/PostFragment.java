package com.example.a20190305.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.a20190305.R;
import com.example.a20190305.base.BaseFragment;
import com.example.a20190305.database.AppDatabase;
import com.example.a20190305.fragments.adapters.CurrencyAdapter;
import com.example.a20190305.models.Currency;
import com.example.a20190305.models.TableModel;
import com.example.a20190305.retrofit.Rest;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFragment extends BaseFragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private List<Currency> currencyList = new ArrayList<>();
    private Map<String,Currency> currencyMap = new HashMap<>();
    private CurrencyAdapter adapter;
    private Button refreshData;

    public static PostFragment newInstance(){
        return new PostFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post,container,false);

        findViews(rootView);
        setListeners();
        setAdapter();
        //SQLite.delete().from(Currency.class).execute();
        fetchData();
        //getDataFromDatabase();

        //fetchData();
//        if(postList.size()==0){
//            Toast.makeText(getContext(),"Pobieranie danych z api, poniewaz lista jest pusta",Toast.LENGTH_SHORT).show();
//            fetchData();
//        } else {
//            Toast.makeText(getContext(),"Pobieranie danych z bazy",Toast.LENGTH_SHORT).show();
//        }

        return rootView;
    }

    private void fetchData() {

        Toast.makeText(getContext(),"Pobieranie danych z API",Toast.LENGTH_SHORT).show();

        Log.i("log", "TUTAJ");

        Rest.getRest().getTables("PLN").enqueue(new Callback<TableModel>() {
            @Override
            public void onResponse(Call<TableModel> call, Response<TableModel> response) {
                Log.i("log", response.message());
                Log.i("log", response.toString());
                Log.i("log", response.raw().toString());

                if(response.isSuccessful()&&response.body()!=null){
                    Log.i("log", "TUTAJ2");

                    TableModel tableModel = response.body();
                        Log.i("log", "TUTAJ3");


                        Log.i("log", tableModel.getBase());

                        TableModel tm = new TableModel();
                        tm.setBase(tableModel.getBase());
                        tm.setDate(tableModel.getDate());
                        tm.setCurrencyList(tableModel.getCurrencyList());

                        for (Map.Entry<String,Double> entry : tm.getCurrencyList().entrySet()){

                            Currency c = new Currency();
                            c.setCode(entry.getKey());
                            c.setCurrency(entry.getValue());

                            c.save();
                            currencyList.add(c);
                            currencyMap.put("PLN",c);
                        }

                    adapter.notifyDataSetChanged();
                } else {
                    Log.i("log", "Nie pobrano body");
                }
            }

            @Override
            public void onFailure(Call<TableModel> call, Throwable t) {
                Log.i("log", "onFailure");
                Log.i("log", call.toString());

                t.printStackTrace();
            }
        });
    }

    private void findViews(View view) {
        recyclerView = view.findViewById(R.id.currency_recycler_view);
        refreshData = view.findViewById(R.id.refresh_buttton);
    }

    private void setListeners(){
        refreshData.setOnClickListener(this);
    }

    private void setAdapter(){
        //currencyList = (List<Currency>) currencyMap.values();

        for (Currency currency : currencyMap.values()) {
            System.out.println(currency);
        }

        adapter = new CurrencyAdapter(currencyList);
        //adapter = new CurrencyAdapter((List<Currency>) currencyMap.values());
        //((List<Currency>) currencyMap.values())
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void getDataFromDatabase() {
        //currencyList.addAll(SQLite.select().from(Currency.class).queryList());
        for (Currency currency : SQLite.select().from(Currency.class).queryList()) {
            currencyMap.put("PLN",currency);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.refresh_buttton:
                fetchData();
                break;
        }
    }
}
