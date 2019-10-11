package com.latihanandroid.mymoviecatalogue.Entity;

import android.database.Cursor;
import android.net.Uri;

public class DatabaseContract {
    public static final String TABLE_FAVORITE_MOVIE="tbFavoriteMovie";
    public static final String TABLE_FAVORITE_TVSHOW="tbFavoriteTVShow";

    public static final String AUTHORITY="com.latihanandroid.mymoviecatalogue";
    public static final String SCHEME="content";

    public static final Uri FAVORITE_MOVIE_CONTENT_URI= new Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE_MOVIE)
            .build();
    public static final Uri FAVORITE_TVSHOW_CONTENT_URI= new Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE_TVSHOW)
            .build();

    public static String getColumnString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor,String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
    public static class TableFavoriteMovieColumn{
        public static final String mMovieID="mMovieID";
    }

    public static class TableFavoriteTVShowColumn{
        public static final String mMovieID="mTVShowID";
    }
}
