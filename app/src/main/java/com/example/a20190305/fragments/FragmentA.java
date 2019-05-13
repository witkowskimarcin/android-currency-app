package com.example.a20190305.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a20190305.R;
import com.example.a20190305.base.BaseFragment;

public class FragmentA extends BaseFragment implements View.OnClickListener {

    private AppCompatButton sendButton;
    private EditText editText;

    public static FragmentA newInstance(){
        return new FragmentA();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_a,container,false);
        findViews(rootView);
        setListeners();
        return rootView;
    }

    private void findViews(View view){
        sendButton = view.findViewById(R.id.fragment_a_send_button);
        editText = view.findViewById(R.id.fragment_a_edit_text);

    }

    private void setListeners(){
        sendButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fragment_a_send_button:
                if(checkEditText()){

                    getNavigationInteractions().changeFragment(FragmentB.newInstance(editText.getText().toString()),true);
                } else {
                    Toast.makeText(getContext(),"Wpisz text",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean checkEditText(){
        String text = editText.getText().toString();
        return !text.isEmpty();
    }
}
