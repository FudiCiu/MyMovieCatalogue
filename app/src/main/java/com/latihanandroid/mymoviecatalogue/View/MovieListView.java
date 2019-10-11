package com.latihanandroid.mymoviecatalogue.View;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.latihanandroid.mymoviecatalogue.Adapter.ItemAdapterParent;
import com.latihanandroid.mymoviecatalogue.Adapter.MovieItemAdapter;
import com.latihanandroid.mymoviecatalogue.Pojo.Movie;
import com.latihanandroid.mymoviecatalogue.R;

import java.util.ArrayList;

public class MovieListView extends BaseListView<Movie> {
    private Fragment fragmentActivity;
    private RecyclerView lsMovie;
    private MovieItemAdapter movieItemAdapter;
    private ProgressBar progressBar;

    public Fragment getFragmentActivity() {
        return fragmentActivity;
    }

    public void setFragmentActivity(Fragment fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    public RecyclerView getLsMovie() {
        return lsMovie;
    }

    public void setLsMovie(RecyclerView lsMovie) {
        this.lsMovie = lsMovie;
    }

    public MovieItemAdapter getMovieItemAdapter() {
        return movieItemAdapter;
    }

    public void setMovieItemAdapter(MovieItemAdapter movieItemAdapter) {
        this.movieItemAdapter = movieItemAdapter;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public MovieListView(Fragment fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
        persiapanListView();
    }

    public void siapkanKomponenAdapter(){
        movieItemAdapter = new MovieItemAdapter(R.layout.item_movie);
        movieItemAdapter.notifyDataSetChanged();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(fragmentActivity.getActivity(),
                LinearLayoutManager.VERTICAL,
                false);
        lsMovie.setLayoutManager(linearLayoutManager);
        lsMovie.setAdapter(movieItemAdapter);
    }
    public void definisikanWidget() {
        lsMovie = getFragmentActivity().getActivity().findViewById(R.id.ls_movie);
        progressBar = getFragmentActivity().getActivity().findViewById(R.id.prgbar_movie_list);
    }
    public void pasangListenerPadaWidget() {
        movieItemAdapter.setOnAdapterItemClickListener((ItemAdapterParent.OnAdapterItemClickListener<Movie>) fragmentActivity);
    }

    public void ubahDataPadaListView(ArrayList<Movie> movies) {
        if (movies != null) {
            movieItemAdapter.setDatas(movies);
            movieItemAdapter.notifyDataSetChanged();

        } else {
            Log.d(getClass().getSimpleName(), "movies is null");
            Toast.makeText(fragmentActivity.getContext(), "Data gagal dimuat", Toast.LENGTH_SHORT).show();
        }
    }
}
