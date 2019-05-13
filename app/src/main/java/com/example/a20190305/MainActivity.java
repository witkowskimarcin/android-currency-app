package com.example.a20190305;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.a20190305.fragments.FragmentA;
import com.example.a20190305.fragments.PostFragment;
import com.example.a20190305.navigation.NavigationListener;
import com.example.a20190305.retrofit.Rest;

public class MainActivity extends AppCompatActivity implements NavigationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Rest.init();

        changeFragment(PostFragment.newInstance(), false);
    }


    @Override
    public void changeFragment(Fragment fragment, Boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.root, fragment);

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.toString());
        }

        fragmentTransaction.commit();
    }
}
