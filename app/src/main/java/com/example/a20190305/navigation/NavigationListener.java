package com.example.a20190305.navigation;

import android.support.v4.app.Fragment;

public interface NavigationListener {
    void changeFragment(Fragment fragment,Boolean addToBackStack);
}
