package com.latihanandroid.mymoviecatalogue.Entity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.latihanandroid.mymoviecatalogue.Helper.MapCursorToArrayList;
import com.latihanandroid.mymoviecatalogue.Pojo.Movie;
import com.latihanandroid.mymoviecatalogue.Pojo.Pemain;
import com.latihanandroid.mymoviecatalogue.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FavoriteMovieViewModel extends AndroidViewModel {
    private static final String TAG = FavoriteMovieViewModel.class.getSimpleName();
//    private FavoriteMovieRepository mFavoriteMovieRepository;
    private MutableLiveData<List<FavoriteMovie>> mAllFavoriteMovies=new MutableLiveData<List<FavoriteMovie>>();
    private MutableLiveData<ArrayList<Movie>> mAllFavoriteMoviesDetail=new MutableLiveData<ArrayList<Movie>>();
    public static boolean needToReload;
    public static MutableLiveData<String> lang=new MutableLiveData<>();
    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);
//        mFavoriteMovieRepository=FavoriteMovieRepository.getInstance(application);
    }
    public void setmAllFavoriteMovies(final ProgressBar progressBar){
        Log.e(TAG, "onChanged: Favorite Movie dimuat ulang");
        ReadAsyncTask readAsyncTask=new ReadAsyncTask(getApplication(),this);
        readAsyncTask.setAsyncCallbackWeakReference(new ReadAsyncTask.AsyncCallback() {
            @Override
            public void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPostExecute() {
                progressBar.setVisibility(View.GONE);
            }
        });
        readAsyncTask.execute();
//        mFavoriteMovieRepository.getAllFavoriteMovies();
    }
    public LiveData<List<FavoriteMovie>> getmAllFavoriteMovies() {
        return mAllFavoriteMovies;
    }

    public void insertFavoriteMovie(FavoriteMovie favoriteMovie){
//        mFavoriteMovieRepository.insertData(favoriteMovie);
        WriteAsyncTask writeAsyncTask=new WriteAsyncTask(getApplication(),this,WriteAsyncTask.INSERT_RC);
        writeAsyncTask.execute(favoriteMovie);
    }

    public void deleteFavoriteMovie(FavoriteMovie favoriteMovie){
//        mFavoriteMovieRepository.deleteData(favoriteMovie);
        WriteAsyncTask writeAsyncTask=new WriteAsyncTask(getApplication(),this,WriteAsyncTask.DELETE_RC);
        writeAsyncTask.execute(favoriteMovie);

    }

    public void setmAllFavoriteMoviesDetail(final ProgressBar progressBar, Context context, String [] ids){
        ArrayList<Movie> movies=new ArrayList<>();
        for (int i=0;i<ids.length;i++){
            String id=ids[i];
            String movieDetailUrl=context.getString(R.string.movie_detail_url);
            String API_KEY="47078f3db47d2cd23dff56874096837c";
            String baseimage_url="https://image.tmdb.org/t/p/w500";
            ANRequest movieDetailRequest= AndroidNetworking.get(movieDetailUrl)
                    .addPathParameter("API_KEY",API_KEY)
                    .addPathParameter("MOVIE_ID",id)
                    .build();
            ANResponse<JSONObject> responMovieDetail=movieDetailRequest.executeForJSONObject();
            if (responMovieDetail.isSuccess()){
                JSONObject movieDetailJSON=responMovieDetail.getResult();
                Log.d(FavoriteMovie.class.getSimpleName(), "onResponse: "+movieDetailJSON);
                String pemainurl=context.getResources().getString(R.string.pemain_url);
                AndroidNetworking.initialize(context);
                ANRequest pemainRequest=AndroidNetworking.get(pemainurl)
                        .addPathParameter("API_KEY",API_KEY)
                        .addPathParameter("MOVIE_ID",id)
                        .build();
                ANResponse<JSONObject> responsePemain=pemainRequest.executeForJSONObject();
                if (responsePemain.isSuccess()){
                    JSONObject responsePemainJSON=responsePemain.getResult();
                    try {
                        JSONArray pemainsJSONArray = responsePemainJSON.getJSONArray("cast");
                        ArrayList<Pemain> pemains=parsingPemains(pemainsJSONArray);
                        String mJudul=movieDetailJSON.getString("title");
                        int mDurasi=movieDetailJSON.getInt("runtime");
                        String mStatus=movieDetailJSON.getString("status");
                        Date mTanggalRilis= new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(movieDetailJSON.getString("release_date"));
                        JSONArray jsonGenreArray=movieDetailJSON.getJSONArray("genres");
                        String[] mGenres= new String[jsonGenreArray.length()];
                        for (int j=0;j<mGenres.length;j++){
                            JSONObject jsonGenre= jsonGenreArray.getJSONObject(j);
                            mGenres[j]=jsonGenre.getString("name");
                        }
                        String mBahasaUtama= movieDetailJSON.getString("original_language");
                        String mDeskripsi=movieDetailJSON.getString("overview");
                        int mBudget=movieDetailJSON.getInt("budget");
                        int mPendapatan=movieDetailJSON.getInt("revenue");
                        String mPhoto=baseimage_url+movieDetailJSON.getString("poster_path");
                        String backdroppath=baseimage_url+movieDetailJSON.getString("backdrop_path");
                        Movie movie =new Movie(id,mJudul,mDurasi,mStatus,mTanggalRilis,pemains,mGenres,mBahasaUtama,mDeskripsi,
                                mBudget,mPendapatan,mPhoto,backdroppath);
                        movies.add(movie);
                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
            mAllFavoriteMoviesDetail.postValue(movies);
            progressBar.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            },300);
            Log.e(TAG, "onChanged: Favorite Movie Detail dimuat ulang");

        }
    }
    protected ArrayList<Pemain> parsingPemains(JSONArray pemainJSON){
        ArrayList<Pemain> pemains=new ArrayList<>();
        for (int i=0;i<pemainJSON.length();i++){
            try {
                JSONObject jsonPemeran=pemainJSON.getJSONObject(i);
                pemains.add(parsingPemain(jsonPemeran));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return pemains;
    }

    protected Pemain parsingPemain(JSONObject jsonPemeran){
        try {
            String baseimage_url="https://image.tmdb.org/t/p/w500";
            String mNamaPemeran= jsonPemeran.getString("name");
            String mKarakter=jsonPemeran.getString("character");
            String mFoto=baseimage_url+jsonPemeran.getString("profile_path");
            Pemain pemain = new Pemain(mNamaPemeran,mKarakter,mFoto);
            return pemain;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public LiveData<ArrayList<Movie>> getmAllFavoriteMoviesDetail() {
        return this.mAllFavoriteMoviesDetail;
    }
    public static void setLang(String langa){
        lang.postValue(langa);
    }
    public static LiveData<String> getLang(){
        return lang;
    }

    private static class ReadAsyncTask extends AsyncTask<Void,Void,Void>{
        private WeakReference<Context> context;
        private WeakReference<FavoriteMovieViewModel> favoriteMovieViewModelWeakReference;
        private WeakReference<AsyncCallback> asyncCallbackWeakReference;

        public ReadAsyncTask(Context context, FavoriteMovieViewModel favoriteMovieViewModel) {
            this.context = new WeakReference<>(context);
            this.favoriteMovieViewModelWeakReference= new WeakReference<>(favoriteMovieViewModel);
        }
        public WeakReference<AsyncCallback> getAsyncCallbackWeakReference() {
            return asyncCallbackWeakReference;
        }

        public void setAsyncCallbackWeakReference(AsyncCallback asyncCallbackWeakReference) {
            this.asyncCallbackWeakReference = new WeakReference<>(asyncCallbackWeakReference);
        }
        @Override
        protected Void doInBackground(Void... voids) {
            Cursor cursor= context.get().getContentResolver().query(DatabaseContract.FAVORITE_MOVIE_CONTENT_URI,null,null,null,null);
            if (cursor!=null) favoriteMovieViewModelWeakReference.get().mAllFavoriteMovies.postValue(MapCursorToArrayList.mapCursorFavoriteMovieToMovieList(cursor));
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (asyncCallbackWeakReference!=null){
                if (asyncCallbackWeakReference.get()!=null)
                    asyncCallbackWeakReference.get().onPreExecute();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (asyncCallbackWeakReference!=null){
                if (asyncCallbackWeakReference.get()!=null)
                asyncCallbackWeakReference.get().onPostExecute();
            }
        }

        public static interface AsyncCallback{
            public void onPreExecute();
            public void onPostExecute();
        }
    }

    private static class WriteAsyncTask extends AsyncTask<FavoriteMovie,Void,Void>{
        private WeakReference<Context> context;
        private WeakReference<FavoriteMovieViewModel> favoriteMovieViewModelWeakReference;
        private WeakReference<Integer> mRC;
        public static final int INSERT_RC=1;
        public static final int DELETE_RC=2;
        public WriteAsyncTask(Context context, FavoriteMovieViewModel favoriteMovieViewModel,int mRC) {
            this.context = new WeakReference<>(context);
            this.favoriteMovieViewModelWeakReference= new WeakReference<>(favoriteMovieViewModel);
            this.mRC=new WeakReference<>(mRC);
        }


        @Override
        protected Void doInBackground(FavoriteMovie... favoriteMovies) {
            ContentValues contentValues=new ContentValues();
            FavoriteMovie favoriteMovie=favoriteMovies[0];
            contentValues.put(DatabaseContract.TableFavoriteMovieColumn.mMovieID,favoriteMovie.getMMovieID());
            switch (mRC.get()){
                case INSERT_RC:
                    context.get().getContentResolver().insert(DatabaseContract.FAVORITE_MOVIE_CONTENT_URI,contentValues);
                    break;
                case DELETE_RC:
                    Uri uri= Uri.parse(DatabaseContract.FAVORITE_MOVIE_CONTENT_URI+"/"+favoriteMovie.getMMovieID());
                    context.get().getContentResolver().delete(uri,null,null);
                    break;
            }
            return null;
        }




    }
}
