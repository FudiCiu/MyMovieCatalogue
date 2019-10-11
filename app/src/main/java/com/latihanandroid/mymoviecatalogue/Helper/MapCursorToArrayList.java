package com.latihanandroid.mymoviecatalogue.Helper;

import android.database.Cursor;

import com.latihanandroid.mymoviecatalogue.Entity.DatabaseContract;
import com.latihanandroid.mymoviecatalogue.Entity.FavoriteMovie;

import java.util.ArrayList;

public class MapCursorToArrayList {
    public static ArrayList<FavoriteMovie> mapCursorFavoriteMovieToMovieList(Cursor cursor){
        ArrayList<FavoriteMovie> result= new ArrayList<>();
        while (cursor.moveToNext()){
            String fmId= cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableFavoriteMovieColumn.mMovieID));
            FavoriteMovie favoriteMovie=new FavoriteMovie(fmId);
            result.add(favoriteMovie);
        }
        return result;
    }
}
