package com.latihanandroid.mymoviecatalogue;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.latihanandroid.mymoviecatalogue.Activity.DetailActivity;
import com.latihanandroid.mymoviecatalogue.Adapter.ItemAdapterParent;
import com.latihanandroid.mymoviecatalogue.Adapter.TVShowAdapter;
import com.latihanandroid.mymoviecatalogue.Entity.FavoriteTVShow;
import com.latihanandroid.mymoviecatalogue.Entity.FavoriteTVShowViewModel;
import com.latihanandroid.mymoviecatalogue.Pojo.TVShow;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTVShowFragment extends Fragment implements ItemAdapterParent.OnAdapterItemClickListener<TVShow> {
    private final String TAG = FavoriteTVShowFragment.class.getSimpleName();
    RecyclerView lvFavoriteTVShow;
    TVShowAdapter tvShowAdapter;
    ProgressBar progressBar;
    FavoriteTVShowViewModel viewModel;

    public FavoriteTVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_tvshow, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvFavoriteTVShow = getView().findViewById(R.id.ls_favorite_tvshow);
        progressBar = getView().findViewById(R.id.prbar_favorite_tvshow_list);
        tvShowAdapter = new TVShowAdapter(R.layout.item_tvshow);
        lvFavoriteTVShow.setHasFixedSize(true);
        lvFavoriteTVShow.setAdapter(tvShowAdapter);
        lvFavoriteTVShow.setLayoutManager(new LinearLayoutManager(getContext()));
        tvShowAdapter.notifyDataSetChanged();
        tvShowAdapter.setOnAdapterItemClickListener(this);
        viewModel = ViewModelProviders.of(this)
                .get(FavoriteTVShowViewModel.class);
        final boolean state;
        if (savedInstanceState == null) {
            FavoriteTVShowViewModel.needToReload=true;
            viewModel.setmAllFavoriteTVShow(progressBar);
        }
        viewModel.getmAllFavoriteTVShow().observe(this, new Observer<List<FavoriteTVShow>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteTVShow> favoriteTVShows) {
                if (FavoriteTVShowViewModel.needToReload) {//Reload hanya jika diper
                    String tempIds = "";
                    if (favoriteTVShows.size() > 0) {
                        tempIds = favoriteTVShows.get(0).getMTVShowID();
                    }
                    for (int i = 1; i < favoriteTVShows.size(); i++) {
                        tempIds = tempIds + ":" + favoriteTVShows.get(i).getMTVShowID();
                    }
                    String ids[] = tempIds.split(":");
                    viewModel.setmAllFavoriteTVShowDetail(progressBar, getContext(), ids);
                }
            }
        });

        viewModel.getmAllFavoriteTVShowDetail().observe(this, new Observer<ArrayList<TVShow>>() {
            @Override
            public void onChanged(@Nullable ArrayList<TVShow> tvShows) {
                tvShowAdapter.setDatas(tvShows);
                tvShowAdapter.notifyDataSetChanged();
            }
        });
        FavoriteTVShowViewModel.getLang().observe(FavoriteTVShowFragment.this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("Bahasa awal ftv", "onChanged: " + s + " " + LanguageChangeReceiver.lg);
                if (!s.equals(LanguageChangeReceiver.lg)) {//Apakah bahasa baru tidak sama dengan bahasa skrg
                    FavoriteTVShowViewModel.needToReload=true;
                    viewModel.setmAllFavoriteTVShow(progressBar);
                    viewModel.getmAllFavoriteTVShow().observe(FavoriteTVShowFragment.this, new Observer<List<FavoriteTVShow>>() {
                        @Override
                        public void onChanged(@Nullable List<FavoriteTVShow> favoriteTVShows) {
                            if (FavoriteTVShowViewModel.needToReload) {//Reload hanya jika diper
                                String tempIds = "";
                                if (favoriteTVShows.size() > 0) {
                                    tempIds = favoriteTVShows.get(0).getMTVShowID();
                                }
                                for (int i = 1; i < favoriteTVShows.size(); i++) {
                                    tempIds = tempIds + ":" + favoriteTVShows.get(i).getMTVShowID();
                                }
                                String ids[] = tempIds.split(":");
                                viewModel.setmAllFavoriteTVShowDetail(progressBar, getContext(), ids);
                            }
                        }
                    });
                    LanguageChangeReceiver.lg = s;
                    Log.d("Bahasa berubah ftv", "onChanged: " + s + " " + LanguageChangeReceiver.lg);
                } else {
                    Log.d("Bahasa sama ftv", "onChanged: " + s + " " + LanguageChangeReceiver.lg);
                }
            }
        });
    }

    @Override
    public void onDataItemClick(TVShow tvShow) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.DETAIL, tvShow);
        intent.putExtra(DetailActivity.OPSI, 1);
        intent.putExtra(DetailActivity.DELETE, true);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        FavoriteTVShowViewModel.needToReload=false;
    }
}
