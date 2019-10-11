package com.latihanandroid.mymoviecatalogue.Entity;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.latihanandroid.mymoviecatalogue.Pojo.Pemain;
import com.latihanandroid.mymoviecatalogue.Pojo.TVShow;
import com.latihanandroid.mymoviecatalogue.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FavoriteTVShowViewModel extends AndroidViewModel {
    private static final String TAG = FavoriteTVShowViewModel.class.getSimpleName();
    private FavoriteTVShowRepository mFavoriteTVShowRepository;
    private LiveData<List<FavoriteTVShow>> mAllFavoriteTVShow;
    private MutableLiveData<ArrayList<TVShow>> mAllFavoriteTVShowDetail=new MutableLiveData<ArrayList<TVShow>>();
    public static boolean needToReload;
    public static MutableLiveData<String> lang=new MutableLiveData<>();

    public FavoriteTVShowViewModel(@NonNull Application application) {
        super(application);
        mFavoriteTVShowRepository=new FavoriteTVShowRepository(application);
    }
    public void setmAllFavoriteTVShow(final ProgressBar progressBar){
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        Log.e(TAG, "onChanged: Favorite TVShow dimuat ulang");
        mAllFavoriteTVShow=mFavoriteTVShowRepository.getAllFavoriteTVShow();
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public LiveData<List<FavoriteTVShow>> getmAllFavoriteTVShow() {
        return mAllFavoriteTVShow;
    }

    public void insertFavoriteTVShow(FavoriteTVShow favoriteTVShow){
        mFavoriteTVShowRepository.insertData(favoriteTVShow);
    }
    public void deleteFavoriteTVShow(FavoriteTVShow favoriteTVShow){
        mFavoriteTVShowRepository.deleteData(favoriteTVShow);
    }


    public void setmAllFavoriteTVShowDetail(final ProgressBar progressBar, Context context, String[] ids){
        final ArrayList<TVShow> listTVShows=new ArrayList<>();
        String tvshowdetailUrl=context.getResources().getString(R.string.tvseries_detail_url);
        String API_KEY="47078f3db47d2cd23dff56874096837c";
        String baseimage_url="https://image.tmdb.org/t/p/w500";
        for (int i=0;i<ids.length;i++){
            String id=ids[i];
            ANRequest tvSeriesDetailRequest= AndroidNetworking.get(tvshowdetailUrl)
                    .addPathParameter("TV_ID",id)
                    .addPathParameter("API_KEY",API_KEY)
                    .build();
            ANResponse<JSONObject> tvSeriesDetailResponse=tvSeriesDetailRequest.executeForJSONObject();
            if (tvSeriesDetailResponse.isSuccess()){
                JSONObject tvSeriesDetailJSON=tvSeriesDetailResponse.getResult();
                String pemainURL=context.getResources().getString(R.string.pemain_tvseries_url);
                ANRequest tvSeriesPemainRequest=AndroidNetworking.get(pemainURL)
                        .addPathParameter("TV_ID",id)
                        .addPathParameter("API_KEY",API_KEY)
                        .build();
                ANResponse<JSONObject> tvSeriesPemainResponse=tvSeriesPemainRequest.executeForJSONObject();
                if (tvSeriesPemainResponse.isSuccess()){
                    JSONObject tvSeriesPemainJSON=tvSeriesPemainResponse.getResult();
                    Log.d(TAG, "onResponse JSON:"+" "+Thread.currentThread().getId()+" "+tvSeriesPemainJSON);
                    try {
                        JSONArray pemainsJSONArray = tvSeriesPemainJSON.getJSONArray("cast");
                        ArrayList<Pemain>pemains=parsingPemains(pemainsJSONArray);
                        String mJudul = tvSeriesDetailJSON.getString("name");
                        JSONArray mDurasiTemp = tvSeriesDetailJSON.getJSONArray("episode_run_time");
                        int mDurasi = mDurasiTemp.getInt(0);
                        String mStatus = tvSeriesDetailJSON.getString("status");
                        Date mTanggalRilis = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(tvSeriesDetailJSON.getString("first_air_date"));
                        JSONArray jsonGenreArray = tvSeriesDetailJSON.getJSONArray("genres");
                        String[] mGenres = new String[jsonGenreArray.length()];
                        for (int j = 0; j < mGenres.length; j++) {
                            JSONObject jsonGenre = jsonGenreArray.getJSONObject(j);
                            mGenres[j] = jsonGenre.getString("name");
                        }
                        String mBahasaUtama = tvSeriesDetailJSON.getString("original_language");
                        String mDeskripsi = tvSeriesDetailJSON.getString("overview");
                        int mEpisode = tvSeriesDetailJSON.getInt("number_of_episodes");
                        int mSeason = tvSeriesDetailJSON.getInt("number_of_seasons");
                        String mPhoto = baseimage_url + tvSeriesDetailJSON.getString("poster_path");
                        String backdroppath = baseimage_url + tvSeriesDetailJSON.getString("backdrop_path");
                        TVShow tvShow=new TVShow(id,mJudul, mEpisode, mStatus, mTanggalRilis, pemains, mGenres, mBahasaUtama, mDeskripsi,
                                mSeason, mDurasi, mPhoto, backdroppath);
                        listTVShows.add(tvShow);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(TAG, "onResponse: "+e);
                    }
                    progressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });

                }
            }
        }
        mAllFavoriteTVShowDetail.postValue(listTVShows);
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
        Log.e(TAG, "onChanged: Favorite TVShow Detail dimuat ulang");
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

    public LiveData<ArrayList<TVShow>> getmAllFavoriteTVShowDetail() {
        return mAllFavoriteTVShowDetail;
    }

    public static void setLang(String langa){
        lang.postValue(langa);
    }
    public static LiveData<String> getLang(){
        return lang;
    }
}
