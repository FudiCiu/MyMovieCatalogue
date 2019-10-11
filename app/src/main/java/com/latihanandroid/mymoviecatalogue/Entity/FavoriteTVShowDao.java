package com.latihanandroid.mymoviecatalogue.Entity;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;
@Dao
public interface FavoriteTVShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertFavoriteTVShow(FavoriteTVShow tvShow);

    @Update
    public void updateFavoriteTVShow(FavoriteTVShow... tvShows);

    @Query("DELETE FROM tbFavoriteTVShow WHERE mTVShowID like :tvShowId")
    public void deleteWhereTVShow(String tvShowId);

    @Query("SELECT * FROM tbFavoriteTVShow")
    public LiveData<List<FavoriteTVShow>> getAllFavoriteTVShow();
}
