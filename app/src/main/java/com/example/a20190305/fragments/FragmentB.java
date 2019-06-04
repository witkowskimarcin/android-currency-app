package com.example.a20190305.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a20190305.R;
import com.example.a20190305.base.BaseFragment;

public class FragmentB  extends BaseFragment {

    private static final String ARG = "ARG";
    private TextView textView;
    private String textFromFragmentA = "";

    public static FragmentB newInstance(String text){
        Bundle bundle = new Bundle();
        bundle.putString(ARG,text);
        FragmentB fragment = new FragmentB();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            textFromFragmentA = getArguments().getString(ARG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_b,container,false);
        findViews(rootView);
        fillView();
        return rootView;
    }

    private void findViews(View view){
        textView = view.findViewById(R.id.fragment_b_text_view);
    }

    private void fillView(){
        textView.setText(textFromFragmentA);
    }


}
