package com.latihanandroid.mymoviecatalogue.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "tbFavoriteTVShow")
public class FavoriteTVShow {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "mTVShowID")
    private String mTVShowID;

    public String getMTVShowID() {
        return mTVShowID;
    }

    public void setMTVShowID(String mTVShowID) {
        this.mTVShowID = mTVShowID;
    }
}
