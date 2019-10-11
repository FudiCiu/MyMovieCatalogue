package com.latihanandroid.mymoviecatalogue.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.latihanandroid.mymoviecatalogue.FavoriteFragment;
import com.latihanandroid.mymoviecatalogue.LanguageChangeReceiver;
import com.latihanandroid.mymoviecatalogue.MovieListFragment;
import com.latihanandroid.mymoviecatalogue.R;
import com.latihanandroid.mymoviecatalogue.TVShowListFragment;
import com.latihanandroid.mymoviecatalogue.View.MainActivityView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Fragment fragment=null;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        definisikanWidget();
//        tambahkanListenerKeWidget();
//        if (savedInstanceState==null){
//            navView.setSelectedItemId(R.id.navigation_movie);
//        }

        MainActivityView mainActivityView=new MainActivityView(this);
        if (savedInstanceState==null){
            mainActivityView.getNavView().setSelectedItemId(R.id.navigation_movie);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings_menu:
                tampilkanMenuSetting();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        fragment=null;
        switch (item.getItemId()) {
            case R.id.navigation_movie:
                fragment= MovieListFragment.newInstance();
                break;
            case R.id.navigation_tvshow:
                fragment=new TVShowListFragment();
                break;
            case R.id.navigation_favorite:
                fragment=new FavoriteFragment();
                break;
        }
        if (fragment==null)
            Toast.makeText(MainActivity.this, "Silahkan pilih menu dibawah", Toast.LENGTH_SHORT).show();
        else
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout,fragment)
                    .commit();
        return true;
    }
    public void tampilkanMenuSetting() {
        Intent intent=new Intent(Settings.ACTION_LOCALE_SETTINGS);
        intent.putExtra(LanguageChangeReceiver.EXTRA_LANGUAGE, Locale.getDefault().getDisplayLanguage());
        LanguageChangeReceiver.lg= Locale.getDefault().getDisplayLanguage();
        startActivity(intent);
    }
//    public void definisikanWidget() {
//        navView = findViewById(R.id.nav_view);
//    }
//
//    public void tambahkanListenerKeWidget() {
//        navView.setOnNavigationItemSelectedListener(this);
//    }

}
