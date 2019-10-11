package com.latihanandroid.mymoviecatalogue;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.latihanandroid.mymoviecatalogue.Entity.FavoriteMovieViewModel;
import com.latihanandroid.mymoviecatalogue.Entity.FavoriteTVShowViewModel;
import com.latihanandroid.mymoviecatalogue.Model.MovieListModel;
import com.latihanandroid.mymoviecatalogue.Model.TVShowListModel;

import java.util.Locale;

public class LanguageChangeReceiver extends BroadcastReceiver {
    public static String lg=Locale.getDefault().getDisplayLanguage();
    public static String lg2=Locale.getDefault().getDisplayLanguage();
    public static final String EXTRA_LANGUAGE="extrabahasa";
    @Override
    public void onReceive(Context context, Intent intent) {

        MovieListModel.setLang(Locale.getDefault().getDisplayLanguage());
        TVShowListModel.setLang(Locale.getDefault().getDisplayLanguage());
        FavoriteTVShowViewModel.setLang(Locale.getDefault().getDisplayLanguage());
        FavoriteMovieViewModel.setLang(Locale.getDefault().getDisplayLanguage());
    }
}
