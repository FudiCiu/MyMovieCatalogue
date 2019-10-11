package com.latihanandroid.mymoviecatalogue.View;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import com.latihanandroid.mymoviecatalogue.R;

public class MainActivityView {
    private AppCompatActivity mainActivity;
    private BottomNavigationView navView;

    public MainActivityView(AppCompatActivity mainActivity) {
        this.mainActivity = mainActivity;
        definisikanWidget();
        tambahkanListenerKeWidget();
    }

    public AppCompatActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(AppCompatActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public BottomNavigationView getNavView() {
        return navView;
    }

    public void setNavView(BottomNavigationView navView) {
        this.navView = navView;
    }

    public void definisikanWidget(){
        navView = mainActivity.findViewById(R.id.nav_view);

    };
    public void tambahkanListenerKeWidget(){
        navView.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) mainActivity);
    };
}
