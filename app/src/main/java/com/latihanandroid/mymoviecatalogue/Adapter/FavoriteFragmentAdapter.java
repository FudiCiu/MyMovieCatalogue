package com.latihanandroid.mymoviecatalogue.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.latihanandroid.mymoviecatalogue.FavoriteMovieFragment;
import com.latihanandroid.mymoviecatalogue.FavoriteTVShowFragment;

public class FavoriteFragmentAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;

    public FavoriteFragmentAdapter(Context context,FragmentManager fm,int totalTabs) {
        super(fm);
        this.context=context;
        this.totalTabs=totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FavoriteMovieFragment();
            case 1:
                return new FavoriteTVShowFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
