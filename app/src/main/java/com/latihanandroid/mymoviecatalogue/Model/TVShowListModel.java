package com.latihanandroid.mymoviecatalogue.Model;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.latihanandroid.mymoviecatalogue.Pojo.Pemain;
import com.latihanandroid.mymoviecatalogue.Pojo.TVShow;
import com.latihanandroid.mymoviecatalogue.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class TVShowListModel extends BaseListModel<TVShow> {
    private final String TAG=getClass().getSimpleName();
    public TVShowListModel() {
    }
    public void setListData(final ProgressBar progressBar, final Context context) {
        String url=context.getResources().getString(R.string.tvseries_discover_url);
        final ArrayList<TVShow> listTVShows=new ArrayList<>();
        listData.postValue(listTVShows);

        AndroidNetworking.initialize(context);
        ANRequest discoverTVShowRequest=AndroidNetworking.get(url)
            .addPathParameter("API_KEY",API_KEY)
            .setPriority(Priority.IMMEDIATE)
            .setExecutor(Executors.newSingleThreadExecutor())
            .setTag("Tag2")
            .build();
        discoverTVShowRequest.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });
                    JSONArray results= response.getJSONArray("results");
                    JSONObject tempForResult0=results.getJSONObject(0);
                    String idForTemp=tempForResult0.getString("id");
                    for (int i=1;i<results.length();i++){
                        JSONObject tempForResult=results.getJSONObject(i);
                        idForTemp=idForTemp+":"+tempForResult.getString("id");
                    }
                    Log.d(TAG, "onResponse: "+idForTemp);
                    String[] ids=idForTemp.split(":");
                    String tvshowdetailUrl=context.getResources().getString(R.string.tvseries_detail_url);
                    for (int i=0;i<ids.length;i++){
                        String id=ids[i];
                        ANRequest tvSeriesDetailRequest=AndroidNetworking.get(tvshowdetailUrl)
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
                    listData.postValue(listTVShows);

                    progressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "onResponse: errj");
                }

                Log.d(TAG, "onResponse: erro");
            }

            @Override
            public void onError(ANError anError) {
                Log.d(getClass().getSimpleName(), "onError: ");
            }
        });
        Log.d(getClass().getSimpleName(), "jalan: ");
    }



}
