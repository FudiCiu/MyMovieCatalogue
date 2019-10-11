package com.latihanandroid.mymoviecatalogue;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.latihanandroid.mymoviecatalogue.Activity.DetailActivity;
import com.latihanandroid.mymoviecatalogue.Model.MovieListModel;
import com.latihanandroid.mymoviecatalogue.Pojo.Movie;
import com.latihanandroid.mymoviecatalogue.View.MovieListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends BaseListFragment<Movie,MovieListModel,MovieListView> {

    public MovieListFragment() {
        // Required empty public constructor
    }
    public static MovieListFragment newInstance(){
        return new MovieListFragment();
    }
    public MovieListFragment getInstance(){
        return this;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        persiapanSemua();
        int datasize=cListModel.getListData().getValue()!=null?cListModel.getListData().getValue().size():0;
        if (savedInstanceState==null||datasize==0){
            isiData(cListView.getProgressBar(),
                    getActivity());
        }
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

    }

    @Override
    void persiapkanModel() {
        cListModel = ViewModelProviders.of(this).get(MovieListModel.class);

    }

    @Override
    void persiapkanView() {
        cListView =new MovieListView(this);

    }

    @Override
    void persiapkanmodelObserver() {
        cListModel.getListData().observe(this,new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Movie> movies) {
                cListView.ubahDataPadaListView(movies);
            }
        });
        MovieListModel.getLang().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("Bahasa awal", "onChanged: "+s+" "+LanguageChangeReceiver.lg);
                if (!s.equals(LanguageChangeReceiver.lg)){//Apakah bahasa baru tidak sama dengan bahasa skrg
                    isiData(cListView.getProgressBar(),getActivity());
                    LanguageChangeReceiver.lg=s;
                    Log.d("Bahasa berubah", "onChanged: "+s+" "+LanguageChangeReceiver.lg);
                }else {
                    Log.d("Bahasa sama", "onChanged: "+s+" "+LanguageChangeReceiver.lg);
                }
            }
        });
    }

    public void isiData(final ProgressBar progressBar, final FragmentActivity activity){
            if (NetworkCheck.checkNetwork(activity)){
                cListModel.setListData(progressBar,activity);
                String s=activity.getResources().getString(R.string.muat_ulang);
                MovieListFragment.showToast(activity,s);
            }else {
                String s= activity.getResources().getString(R.string.network_failed);
                MovieListFragment.showToast(activity,s);

            }
    };


    @Override
    public void onDataItemClick(Movie movie) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.DETAIL, movie);
        intent.putExtra(DetailActivity.OPSI, 0);
        startActivity(intent);
    }
}
