package com.latihanandroid.mymoviecatalogue.Entity;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

@Dao
public interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(FavoriteMovie favoriteMovie);

    @Update
    public void updateFavoriteMovies(FavoriteMovie... favoriteMovie);

    @Query("DELETE FROM tbFavoriteMovie WHERE mMovieID like:movieID")
    public void deleteFavoriteMovieWhere(String movieID);

    @Query("SELECT * FROM tbFavoriteMovie")
    public LiveData<List<FavoriteMovie>> getFavoriteMovie();

    @Query("SELECT * FROM tbFavoriteMovie WHERE mMovieID like:movieID ")
    public LiveData<List<FavoriteMovie>> getFavoriteMovieWhere(String movieID);

    @Query("SELECT * FROM tbFavoriteMovie")
    public Cursor getFavoriteMovieAsCursor();

    @Query("SELECT * FROM tbFavoriteMovie WHERE mMovieID like:movieID ")
    public Cursor getFavoriteMovieWhereAsCursor(String movieID);

}
