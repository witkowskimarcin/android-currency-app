package com.example.a20190305.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.a20190305.navigation.NavigationListener;

public class BaseFragment extends Fragment {

    NavigationListener navigationListener;

    public NavigationListener getNavigationInteractions(){
        return navigationListener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigationListener = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof NavigationListener){
            navigationListener = (NavigationListener) context;
        }
    }
}
