package com.latihanandroid.mymoviecatalogue.Entity;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

public class FavoriteTVShowRepository {
    private FavoriteTVShowDao mFavoriteTVShowDao;
    private FavoriteRoomDatabase mFavoriteRoomDatabase;

    public FavoriteTVShowRepository(Application application) {
        this.mFavoriteRoomDatabase = FavoriteRoomDatabase.getDatabase(application);
        this.mFavoriteTVShowDao=mFavoriteRoomDatabase.favoriteTVShowDao();
    }

    public void insertData(FavoriteTVShow tvShow){
        new WriteFavoriteTVShowAsyncTask(mFavoriteTVShowDao,WriteFavoriteTVShowAsyncTask.INSERT_RC).execute(tvShow);
    }

    public void deleteData(FavoriteTVShow tvShow){
        new WriteFavoriteTVShowAsyncTask(mFavoriteTVShowDao,WriteFavoriteTVShowAsyncTask.DELETE_RC).execute(tvShow);
    }

    public LiveData<List<FavoriteTVShow>> getAllFavoriteTVShow(){
        return mFavoriteTVShowDao.getAllFavoriteTVShow();
    }

    private static class WriteFavoriteTVShowAsyncTask extends AsyncTask<FavoriteTVShow,Void,Void>{
        public static final int INSERT_RC=1;
        public static final int DELETE_RC=2;
        private WeakReference<FavoriteTVShowDao> mDao;
        private int RC;
        public WriteFavoriteTVShowAsyncTask(FavoriteTVShowDao mDao,int RC) {
            this.mDao = new WeakReference<>(mDao);
            this.RC=RC;
        }

        @Override
        protected Void doInBackground(FavoriteTVShow... favoriteTVShows) {
            switch (RC){
                case INSERT_RC:
                    mDao.get().insertFavoriteTVShow(favoriteTVShows[0]);
                    break;
                case DELETE_RC:
                    mDao.get().deleteWhereTVShow(favoriteTVShows[0].getMTVShowID());
                    break;
            }
            return null;
        }
    }
}
