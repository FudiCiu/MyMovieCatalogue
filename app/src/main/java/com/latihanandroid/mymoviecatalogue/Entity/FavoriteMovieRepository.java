package com.latihanandroid.mymoviecatalogue.Entity;

import android.arch.lifecycle.MutableLiveData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovieRepository {
    private Context mContext;
    private MutableLiveData<List<FavoriteMovie>> lsFavoriteMovie= new MutableLiveData<>();

    private static FavoriteMovieRepository INSTANCE;
    public static FavoriteMovieRepository getInstance(Context application){
        if (INSTANCE==null){
            synchronized (FavoriteRoomDatabase.class){
                if (INSTANCE==null){
                    INSTANCE=new FavoriteMovieRepository(application);
                }
            }
        }
        return INSTANCE;
    }
    private FavoriteMovieRepository(Context application) {
        mContext=application;
    }

    public MutableLiveData<List<FavoriteMovie>> getLsFavoriteMovie() {
        return lsFavoriteMovie;
    }

    public void setLsFavoriteMovie(MutableLiveData<List<FavoriteMovie>> lsFavoriteMovie) {
        this.lsFavoriteMovie = lsFavoriteMovie;
    }

    //    public void insertData(FavoriteMovie favoriteMovie){
//        new WriteFavoriteMovieAsyncTask(mFavoriteMovieDao,WriteFavoriteMovieAsyncTask.INSERT_RC).execute(favoriteMovie);
//    }
//
//    public void deleteData(FavoriteMovie favoriteMovie){
//        new WriteFavoriteMovieAsyncTask(mFavoriteMovieDao,WriteFavoriteMovieAsyncTask.DELETE_RC).execute(favoriteMovie);
//    }
//    public LiveData<List<FavoriteMovie>> getAllFavoriteMovies(){
//        LiveData<List<FavoriteMovie>> favoriteMovie=mFavoriteMovieDao.getFavoriteMovie();
//        return favoriteMovie;
//    }
    public void insertData(FavoriteMovie favoriteMovie){
        new FavoriteMovieRepository.WriteFavoriteMovieAsyncTask(WriteFavoriteMovieAsyncTask.INSERT_RC,this).execute(favoriteMovie);
    }
    public void deleteData(FavoriteMovie favoriteMovie){
        new FavoriteMovieRepository.WriteFavoriteMovieAsyncTask(WriteFavoriteMovieAsyncTask.DELETE_RC,this).execute(favoriteMovie);
    }
    public void getAllFavoriteMovies(){
        new ReadFavoriteMovieAsyncTask().execute(this);
    }
    private ArrayList<FavoriteMovie> mapCursorToArrayList(Cursor cursor){
        ArrayList<FavoriteMovie> result= new ArrayList<>();
        while (cursor.moveToNext()){
            String fmId= cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TableFavoriteMovieColumn.mMovieID));
            FavoriteMovie favoriteMovie=new FavoriteMovie(fmId);
            result.add(favoriteMovie);
        }
        return result;
    }

    private static class ReadFavoriteMovieAsyncTask extends AsyncTask<FavoriteMovieRepository,Void,Cursor>{

        @Override
        protected Cursor doInBackground(FavoriteMovieRepository... favoriteMovieRepositories) {
            Cursor cursorFavoriteMovie =favoriteMovieRepositories[0].mContext.getContentResolver().
                    query(DatabaseContract.FAVORITE_MOVIE_CONTENT_URI,null,null,null,null);
            favoriteMovieRepositories[0].lsFavoriteMovie.postValue(favoriteMovieRepositories[0].mapCursorToArrayList(cursorFavoriteMovie));
            return null;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

        }
    }

    private static class WriteFavoriteMovieAsyncTask extends AsyncTask<FavoriteMovie,Void,Void>{
        private int mRC;
        private FavoriteMovieRepository movieRepository;
        public static final int INSERT_RC=1;
        public static final int DELETE_RC=2;

        public WriteFavoriteMovieAsyncTask(int mRC, FavoriteMovieRepository movieRepository) {
            this.mRC = mRC;
            this.movieRepository = movieRepository;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }

        @Override
        protected Void doInBackground(FavoriteMovie... favoriteMovies) {
            ContentValues dataToInsert= new ContentValues();
            FavoriteMovie favoriteMovie=favoriteMovies[0];
            switch (mRC){
                case INSERT_RC:
                    dataToInsert.put(DatabaseContract.TableFavoriteMovieColumn.mMovieID,favoriteMovie.getMMovieID());
                    movieRepository.mContext.getContentResolver().insert(DatabaseContract.FAVORITE_MOVIE_CONTENT_URI,dataToInsert);
                    break;
                case DELETE_RC:
                    Uri uri= Uri.parse(DatabaseContract.FAVORITE_MOVIE_CONTENT_URI+"/"+favoriteMovie.getMMovieID());
                    movieRepository.mContext.getContentResolver().delete(uri,null,null);
                    break;
            }


            return null;
        }
    }

//    private static class WriteFavoriteMovieAsyncTask extends AsyncTask<FavoriteMovie,Void,Void>{
//        public final static String TAG=WriteFavoriteMovieAsyncTask.class.getSimpleName();
//        public static final int INSERT_RC=1;
//        public static final int DELETE_RC=2;
//        private WeakReference<FavoriteMovieDao> mDao;
//        private int mRC;
//        public WriteFavoriteMovieAsyncTask(FavoriteMovieDao mDao,int RequestCode) {
//            this.mDao = new WeakReference<>(mDao);
//            this.mRC=RequestCode;
//        }
//
//        @Override
//        protected Void doInBackground(FavoriteMovie... favoriteMovies) {
//            switch (mRC){
//                case INSERT_RC:
////                    mDao.get().insert(favoriteMovies[0]);
//                    break;
//                case DELETE_RC:
//                    mDao.get().deleteFavoriteMovieWhere(favoriteMovies[0].getMMovieID());
//                    break;
//            }
//            return null;
//        }
//    }

}
