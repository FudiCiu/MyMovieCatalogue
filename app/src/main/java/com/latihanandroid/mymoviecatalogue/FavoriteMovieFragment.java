package com.latihanandroid.mymoviecatalogue;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
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
import com.latihanandroid.mymoviecatalogue.Adapter.MovieItemAdapter;
import com.latihanandroid.mymoviecatalogue.Entity.DatabaseContract;
import com.latihanandroid.mymoviecatalogue.Entity.FavoriteMovie;
import com.latihanandroid.mymoviecatalogue.Entity.FavoriteMovieViewModel;
import com.latihanandroid.mymoviecatalogue.Pojo.Movie;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements MovieItemAdapter.OnAdapterItemClickListener<Movie> {
    final String TAG = FavoriteMovieFragment.class.getSimpleName();
    RecyclerView lsFavoriteMovie;
    MovieItemAdapter lsFavoriteMovieAdapter;
    ProgressBar progressBar;
    FavoriteMovieViewModel viewModel;

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lsFavoriteMovie = getView().findViewById(R.id.ls_favorite_movie);
        progressBar = getView().findViewById(R.id.prgbar_favorite_movie_list);
        lsFavoriteMovieAdapter = new MovieItemAdapter(R.layout.item_movie);
        lsFavoriteMovie.setHasFixedSize(true);
        lsFavoriteMovie.setAdapter(lsFavoriteMovieAdapter);
        lsFavoriteMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        lsFavoriteMovieAdapter.notifyDataSetChanged();
        lsFavoriteMovieAdapter.setOnAdapterItemClickListener(this);
        viewModel = ViewModelProviders.of(this)
                .get(FavoriteMovieViewModel.class);
        if (savedInstanceState == null) {
            viewModel.setmAllFavoriteMovies(progressBar);
            FavoriteMovieViewModel.needToReload = true;
        }
        HandlerThread handlerThread=new HandlerThread("FavoriteMovieHT");
        handlerThread.start();
        Handler handler=new Handler(handlerThread.getLooper());
        FavoriteMovieContentObserver favoriteMovieContentObserver=new FavoriteMovieContentObserver(handler,viewModel,progressBar);
        getContext().getContentResolver().registerContentObserver(DatabaseContract.FAVORITE_MOVIE_CONTENT_URI,true,favoriteMovieContentObserver);
        if (!viewModel.getmAllFavoriteMovies().hasActiveObservers()){
            viewModel.getmAllFavoriteMovies().observe(this, new Observer<List<FavoriteMovie>>() {
                @Override
                public void onChanged(@Nullable List<FavoriteMovie> favoriteMovies) {
                    if (FavoriteMovieViewModel.needToReload) {
                        String tempIds = "";
                        if (favoriteMovies.size() > 0) {
                            tempIds = favoriteMovies.get(0).getMMovieID();
                        }
                        for (int i = 1; i < favoriteMovies.size(); i++) {
                            tempIds = tempIds + ":" + favoriteMovies.get(i).getMMovieID();
                        }
                        String ids[] = tempIds.split(":");
                        viewModel.setmAllFavoriteMoviesDetail(progressBar, getContext(), ids);
                    }
                }
            });
        }
        if (!viewModel.getmAllFavoriteMoviesDetail().hasActiveObservers()){
            viewModel.getmAllFavoriteMoviesDetail().observe(this, new Observer<ArrayList<Movie>>() {
                @Override
                public void onChanged(@Nullable ArrayList<Movie> movies) {
                    lsFavoriteMovieAdapter.setDatas(movies);
                    lsFavoriteMovieAdapter.notifyDataSetChanged();
                }
            });
        }

        FavoriteMovieViewModel.getLang().observe(FavoriteMovieFragment.this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("Bahasa awal myin", "onChanged: " + s + " " + LanguageChangeReceiver.lg);
                if (!s.equals(LanguageChangeReceiver.lg2)) {//Apakah bahasa baru tidak sama dengan bahasa skrg
                    FavoriteMovieViewModel.needToReload=true;
                    viewModel.setmAllFavoriteMovies(progressBar);
                    viewModel.getmAllFavoriteMovies().observe(FavoriteMovieFragment.this, new Observer<List<FavoriteMovie>>() {
                        @Override
                        public void onChanged(@Nullable List<FavoriteMovie> favoriteMovies) {
                            if (FavoriteMovieViewModel.needToReload) {
                                String tempIds = "";
                                if (favoriteMovies.size() > 0) {
                                    tempIds = favoriteMovies.get(0).getMMovieID();
                                }
                                for (int i = 1; i < favoriteMovies.size(); i++) {
                                    tempIds = tempIds + ":" + favoriteMovies.get(i).getMMovieID();
                                }
                                String ids[] = tempIds.split(":");
                                viewModel.setmAllFavoriteMoviesDetail(progressBar, getContext(), ids);
                            }
                        }
                    });
                    LanguageChangeReceiver.lg2 = s;
                    Log.d("Bahasa berubah myin", "onChanged: " + s + " " + LanguageChangeReceiver.lg);
                } else {
                    Log.d("Bahasa sama myin", "onChanged: " + s + " " + LanguageChangeReceiver.lg);
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        FavoriteMovieViewModel.needToReload=false;
    }


    @Override
    public void onDataItemClick(Movie movie) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.DETAIL, movie);
        intent.putExtra(DetailActivity.OPSI, 0);
        intent.putExtra(DetailActivity.DELETE, true);
        startActivity(intent);
    }

    private static class FavoriteMovieContentObserver extends ContentObserver {
        private FavoriteMovieViewModel favoriteMovieViewModel;
        private ProgressBar fprogressBar;
        public FavoriteMovieContentObserver(Handler handler,FavoriteMovieViewModel favoriteMovieViewModel,
                                            ProgressBar progressBar) {
            super(handler);
            this.favoriteMovieViewModel=favoriteMovieViewModel;
            this.fprogressBar=progressBar;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            favoriteMovieViewModel.setmAllFavoriteMovies(fprogressBar);
            favoriteMovieViewModel.getmAllFavoriteMovies().notify();
        }
    }
}
