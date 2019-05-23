package com.example.a20190305.models;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Map;
import java.util.TreeMap;

public class TableModelHistory extends BaseModel {

    @SerializedName("base")
    private String base;

    @SerializedName("date")
    private String date;

    @SerializedName("rates")
    private TreeMap<String,Map<String,Double>> history;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TreeMap<String, Map<String, Double>> getHistory() {
        return history;
    }

    public void setHistory(TreeMap<String, Map<String, Double>> history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "TableModelHistory{" +
                "base='" + base + '\'' +
                ", date='" + date + '\'' +
                ", history=" + history +
                '}';
    }
}
