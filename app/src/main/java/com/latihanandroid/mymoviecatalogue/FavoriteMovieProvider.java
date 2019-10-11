package com.latihanandroid.mymoviecatalogue;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.latihanandroid.mymoviecatalogue.Entity.DatabaseContract;
import com.latihanandroid.mymoviecatalogue.Entity.FavoriteMovie;
import com.latihanandroid.mymoviecatalogue.Entity.FavoriteMovieDao;
import com.latihanandroid.mymoviecatalogue.Entity.FavoriteRoomDatabase;

public class FavoriteMovieProvider extends ContentProvider {
    private static final int ALLDATA=1;
    private static final int ONEDATA=2;
    static final UriMatcher fmUriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
    private FavoriteMovieDao favoriteMovieDao;
    static {
        fmUriMatcher.addURI(DatabaseContract.AUTHORITY,DatabaseContract.TABLE_FAVORITE_MOVIE,ALLDATA);
        fmUriMatcher.addURI(DatabaseContract.AUTHORITY,DatabaseContract.TABLE_FAVORITE_MOVIE+"/#",ONEDATA);
    }

    public FavoriteMovieProvider() {
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public boolean onCreate() {
        favoriteMovieDao=FavoriteRoomDatabase.getDatabase(getContext()).favoriteMovieDao();
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (fmUriMatcher.match(uri)){
            case ALLDATA:
                FavoriteMovie favoriteMovie=new FavoriteMovie();
                favoriteMovie.setMMovieID(values.getAsString(DatabaseContract.TableFavoriteMovieColumn.mMovieID));
                Log.d("Tes cp", "insert: "+favoriteMovie.getMMovieID());
                favoriteMovieDao.insert(favoriteMovie);
                break;
        }
        return null;
    }
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (fmUriMatcher.match(uri)){
            case ALLDATA:
                return favoriteMovieDao.getFavoriteMovieAsCursor();
            case ONEDATA:
                return favoriteMovieDao.getFavoriteMovieWhereAsCursor(uri.getLastPathSegment());
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return -1;
    }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (fmUriMatcher.match(uri)){
            case ONEDATA:
                favoriteMovieDao.deleteFavoriteMovieWhere(uri.getLastPathSegment());
                return 1;
        }
        return -1;
    }
}
