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
import com.latihanandroid.mymoviecatalogue.Pojo.Movie;
import com.latihanandroid.mymoviecatalogue.Pojo.Pemain;
import com.latihanandroid.mymoviecatalogue.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;

public class MovieListModel extends BaseListModel<Movie> {
    private final String TAG=getClass().getSimpleName();
    public void setListData(final ProgressBar progressBar, final Context context) {
        Log.d(TAG, "setListData: jalanjuga");
        String url= context.getResources().getString(R.string.json_url);
        final ArrayList<Movie> movies=new ArrayList<>();
        listData.postValue(movies);
        AndroidNetworking.initialize(context);
        ANRequest discoverMoviesRequest=AndroidNetworking.get(url)
                .addPathParameter("API_KEY",API_KEY)
                .setPriority(Priority.LOW)
                .setExecutor(Executors.newSingleThreadExecutor())
                .build();
        discoverMoviesRequest.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });
                    JSONArray jsonResult=response.getJSONArray("results");
                    JSONObject tempForResulti0=jsonResult.getJSONObject(0);
                    String idtemp=tempForResulti0.getString("id");
                    for (int i=1;i<jsonResult.length();i++){
                        JSONObject tempForResult=jsonResult.getJSONObject(i);
                        idtemp=idtemp+":"+tempForResult.getString("id");
                    }
                    String[] ids=idtemp.split(":");
                    for (int i=0;i<ids.length;i++){
                        String id=ids[i];
                        String movieDetailUrl=context.getString(R.string.movie_detail_url);
                        ANRequest movieDetailRequest=AndroidNetworking.get(movieDetailUrl)
                                .addPathParameter("API_KEY",API_KEY)
                                .addPathParameter("MOVIE_ID",id)
                                .build();
                        ANResponse<JSONObject> responMovieDetail=movieDetailRequest.executeForJSONObject();
                        if (responMovieDetail.isSuccess()){
                            JSONObject movieDetailJSON=responMovieDetail.getResult();
                            Log.d(TAG, "onResponse: "+movieDetailJSON);
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
                                    ArrayList<Pemain>pemains=parsingPemains(pemainsJSONArray);
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
                        listData.postValue(movies);
                        progressBar.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                            }
                        },300);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.d(TAG, "onError: ");
            }
        });

    }



}
