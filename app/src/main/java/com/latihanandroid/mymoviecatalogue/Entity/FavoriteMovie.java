package com.latihanandroid.mymoviecatalogue.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.database.Cursor;
import android.support.annotation.NonNull;

@Entity(tableName = "tbFavoriteMovie")
public class FavoriteMovie {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "mMovieID")
    private String mMovieID;

    public FavoriteMovie(String mId) {
        setMMovieID(mId);
    }

    public String getMMovieID() {
        return mMovieID;
    }

    public void setMMovieID(String mMovieID) {
        this.mMovieID = mMovieID;
    }

    public FavoriteMovie(Cursor cursor) {
        setMMovieID(DatabaseContract.getColumnString(cursor,DatabaseContract.TableFavoriteMovieColumn.mMovieID));
    }

    public FavoriteMovie() {
    }
}
