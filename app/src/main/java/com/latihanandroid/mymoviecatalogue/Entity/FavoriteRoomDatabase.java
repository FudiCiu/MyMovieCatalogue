package com.latihanandroid.mymoviecatalogue.Entity;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {FavoriteMovie.class,FavoriteTVShow.class},version = 2)
public abstract class FavoriteRoomDatabase extends RoomDatabase {
    public abstract FavoriteMovieDao favoriteMovieDao();
    public abstract FavoriteTVShowDao favoriteTVShowDao();

    private static FavoriteRoomDatabase INSTANCE;

    public static FavoriteRoomDatabase getDatabase(Context context) {
        if (INSTANCE==null){
            synchronized (FavoriteRoomDatabase.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            FavoriteRoomDatabase.class,"favorite_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
