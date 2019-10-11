package com.latihanandroid.mymoviecatalogue;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.latihanandroid.mymoviecatalogue.Model.TVShowListModel;
import com.latihanandroid.mymoviecatalogue.Pojo.TVShow;
import com.latihanandroid.mymoviecatalogue.View.TVShowListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowListFragment extends BaseListFragment<TVShow, TVShowListModel, TVShowListView> {
    private static final String TAG = TVShowListFragment.class.getSimpleName() ;

    public TVShowListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getClass().getSimpleName(), "onCreate TVShowListFragment: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public static void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    void persiapkanModel() {
        cListModel= ViewModelProviders.of(this).get(TVShowListModel.class);
    }

    @Override
    void persiapkanView() {
        cListView=new TVShowListView(this);
    }

    @Override
    void persiapkanmodelObserver() {
        cListModel.getListData().observe(TVShowListFragment.this, new Observer<ArrayList<TVShow>>() {
            @Override
            public void onChanged(@Nullable ArrayList<TVShow> tvShows) {
                cListView.ubahDataPadaListView(tvShows);
            }
        });
        TVShowListModel.getLang().observe(TVShowListFragment.this, new Observer<String>() {
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

    @Override
    void isiData(ProgressBar progressBar, FragmentActivity activity) {
        if (NetworkCheck.checkNetwork(activity)){
            cListModel.setListData(progressBar,activity);
            String s=activity.getResources().getString(R.string.muat_ulang);
            TVShowListFragment.showToast(activity,s);
        }else {
            String s= activity.getResources().getString(R.string.network_failed);
            TVShowListFragment.showToast(activity,s);

        }
    }

    @Override
    public void onDataItemClick(TVShow tvShow) {
        Log.d(MovieListFragment.class.getSimpleName(),tvShow.getmPemeran().get(0).getmNama());
        Intent intent= new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.DETAIL,tvShow);
        intent.putExtra(DetailActivity.OPSI,1);
        startActivity(intent);
    }
}
