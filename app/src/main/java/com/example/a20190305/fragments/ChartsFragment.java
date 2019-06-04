package com.example.a20190305.fragments;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a20190305.R;
import com.example.a20190305.base.BaseFragment;
import com.example.a20190305.fragments.adapters.CurrencyAdapter;
import com.example.a20190305.models.Currency;
import com.example.a20190305.models.TableModel;
import com.example.a20190305.models.TableModelHistory;
import com.example.a20190305.retrofit.Rest;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChartsFragment extends BaseFragment implements View.OnClickListener, OnChartGestureListener, OnChartValueSelectedListener {

    private EditText et_currency;
    private EditText et_currency2;
    private EditText et_d1;
    private EditText et_d2;
    private TableModelHistory tmh;
    private LineChart mChart;
    private Button button_1w;
    private Button button_1m;
    private Button button_3m;
    private Button button_6m;
    private Button button_1y;

    public static ChartsFragment newInstance(){
        return new ChartsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_charts,container,false);

        findViews(rootView);
        setListeners();

        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        return rootView;
    }

    private void drawChart() {
        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // no description text
        mChart.setDescription("");
//        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setScaleXEnabled(true);
        mChart.setScaleYEnabled(true);

//        LimitLine upper_limit = new LimitLine(130f, "Upper Limit");
//        upper_limit.setLineWidth(4f);
//        upper_limit.enableDashedLine(10f, 10f, 0f);
//        upper_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        upper_limit.setTextSize(10f);
//
//        LimitLine lower_limit = new LimitLine(-30f, "Lower Limit");
//        lower_limit.setLineWidth(4f);
//        lower_limit.enableDashedLine(10f, 10f, 0f);
//        lower_limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        lower_limit.setTextSize(10f);

//        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
////        leftAxis.addLimitLine(upper_limit);
////        leftAxis.addLimitLine(lower_limit);
//        leftAxis.setAxisMaxValue(220f);
//        leftAxis.setAxisMinValue(-50f);
//        //leftAxis.setYOffset(20f);
//        leftAxis.enableGridDashedLine(10f, 10f, 0f);
//        leftAxis.setDrawZeroLine(false);
//
//        // limit lines are drawn behind data (and not on top)
//        leftAxis.setDrawLimitLinesBehindData(true);
//
//        mChart.getAxisRight().setEnabled(false);
//
//        //mChart.getViewPortHandler().setMaximumScaleY(2f);
//        //mChart.getViewPortHandler().setMaximumScaleX(2f);
//
//        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

        //  dont forget to refresh the drawing
        mChart.invalidate();
    }

    private void fetchData(String d1, String d2) {

       //s Toast.makeText(getContext(),"Pobieranie danych z API",Toast.LENGTH_SHORT).show();

        Log.i("log", "TUTAJ");

//        currencyList.clear();
//

        String currency_str = et_currency.getText().toString();
        if(currency_str==null || currency_str.equals("") || currency_str.equals("Currency")){
            currency_str = "PLN";
        }

        String currency_str2 = et_currency2.getText().toString();
        if(currency_str2==null || currency_str2.equals("") || currency_str2.equals("Currency")){
            currency_str2 = "PLN";
        }

        Log.i("log",currency_str);
        Log.i("log",currency_str2);

        Rest.getRest().getTables5(d1,d2,currency_str,currency_str2).enqueue(new Callback<TableModelHistory>() {
            @Override
            public void onResponse(Call<TableModelHistory> call, Response<TableModelHistory> response) {
                Log.i("log",call.request().url().toString());
                if(response.isSuccessful()&&response.body()!=null){

                    //TableModelHistory tableModel = response.body();
                    tmh = response.body();

                    Log.i("log","ZALADOWALO BODY");
                    Log.i("log",response.body().toString());

                    ArrayList<String> xVals = new ArrayList<String>();
                    ArrayList<Entry> yVals = new ArrayList<Entry>();

                    //TreeMap<String,Map<String,Double>> map = tmh.getHistory();

                    int i = 0;
                    for (Map.Entry<String,Map<String,Double>> entry : tmh.getHistory().entrySet()){

                        //Log.i("log","x = "+x+" | KEY = "+entry.getKey()+ " | VALUE = "+entry.getValue().values().iterator().next());

                        xVals.add(entry.getKey());
                        float y = (entry.getValue().values().iterator().next()).floatValue();
                        yVals.add(new Entry(y, i));
                        ++i;
                    }

                    setData(xVals,yVals);
                    drawChart();
                }
                else {
                    Log.i("log", "Nie pobrano body");
                }
            }

            @Override
            public void onFailure(Call<TableModelHistory> call, Throwable t) {
                Log.i("log", "onFailure");
                t.printStackTrace();
            }
        });
    }

    private void findViews(View view) {
        et_currency = view.findViewById(R.id.et_currency);
        et_currency2 = view.findViewById(R.id.et_currency2);
        mChart = view.findViewById(R.id.lineChart);
        button_1w = view.findViewById(R.id.button_1w);
        button_1m = view.findViewById(R.id.button_1m);
        button_3m = view.findViewById(R.id.button_3m);
        button_6m = view.findViewById(R.id.button_6m);
        button_1y = view.findViewById(R.id.button_1y);
    }

    private void setListeners(){
        button_1w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dt = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(dt);

                int month2 = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
                int year2  = c.get(Calendar.YEAR);
                int day2  = c.get(Calendar.DAY_OF_MONTH);

                c.add(Calendar.DAY_OF_MONTH, -7);

                int month = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
                int year  = c.get(Calendar.YEAR);
                int day  = c.get(Calendar.DAY_OF_MONTH);

                fetchData(year+"-"+month+"-"+day,year2+"-"+month2+"-"+day2);
            }
        });
        button_1m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dt = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(dt);

                int month2 = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
                int year2  = c.get(Calendar.YEAR);
                int day2  = c.get(Calendar.DAY_OF_MONTH);

                c.add(Calendar.MONTH, -1);

                int month = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
                int year  = c.get(Calendar.YEAR);
                int day  = c.get(Calendar.DAY_OF_MONTH);

                fetchData(year+"-"+month+"-"+day,year2+"-"+month2+"-"+day2);
            }
        });
        button_3m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dt = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(dt);

                int month2 = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
                int year2  = c.get(Calendar.YEAR);
                int day2  = c.get(Calendar.DAY_OF_MONTH);

                c.add(Calendar.MONTH, -3);

                int month = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
                int year  = c.get(Calendar.YEAR);
                int day  = c.get(Calendar.DAY_OF_MONTH);

                fetchData(year+"-"+month+"-"+day,year2+"-"+month2+"-"+day2);
            }
        });
        button_6m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dt = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(dt);

                int month2 = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
                int year2  = c.get(Calendar.YEAR);
                int day2  = c.get(Calendar.DAY_OF_MONTH);

                c.add(Calendar.MONTH, -6);

                int month = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
                int year  = c.get(Calendar.YEAR);
                int day  = c.get(Calendar.DAY_OF_MONTH);

                fetchData(year+"-"+month+"-"+day,year2+"-"+month2+"-"+day2);
            }
        });
        button_1y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dt = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(dt);

                int month2 = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
                int year2  = c.get(Calendar.YEAR);
                int day2  = c.get(Calendar.DAY_OF_MONTH);

                c.add(Calendar.YEAR, -1);

                int month = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
                int year  = c.get(Calendar.YEAR);
                int day  = c.get(Calendar.DAY_OF_MONTH);

                fetchData(year+"-"+month+"-"+day,year2+"-"+month2+"-"+day2);
            }
        });
    }

    private void getDataFromDatabase() {

    }

    // CHARTS

    @Override
    public void onChartGestureStart(MotionEvent me,
                                    ChartTouchListener.ChartGesture
                                            lastPerformedGesture) {

        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me,
                                  ChartTouchListener.ChartGesture
                                          lastPerformedGesture) {

        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if(lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            // or highlightTouch(null) for callback to onNothingSelected(...)
            mChart.highlightValues(null);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2,
                             float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: "
                + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleXIndex()
                + ", high: " + mChart.getHighestVisibleXIndex());

        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin()
                + ", xmax: " + mChart.getXChartMax()
                + ", ymin: " + mChart.getYChartMin()
                + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }

    private void setData(ArrayList<String> xVals, ArrayList<Entry> yVals) {
        LineDataSet set1;

        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "Wykres");
        set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        // set1.enableDashedLine(10f, 5f, 0f);
        // set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);
    }

    @Override
    public void onClick(View v) {

    }
}
