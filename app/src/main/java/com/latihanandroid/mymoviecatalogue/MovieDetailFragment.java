package com.latihanandroid.mymoviecatalogue;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.latihanandroid.mymoviecatalogue.Activity.DetailActivity;
import com.latihanandroid.mymoviecatalogue.Adapter.PemainAdapter;
import com.latihanandroid.mymoviecatalogue.Entity.FavoriteMovie;
import com.latihanandroid.mymoviecatalogue.Entity.FavoriteMovieViewModel;
import com.latihanandroid.mymoviecatalogue.Model.DetailMovieModel;
import com.latihanandroid.mymoviecatalogue.Pojo.Movie;
import com.latihanandroid.mymoviecatalogue.Pojo.Pemain;
import com.latihanandroid.mymoviecatalogue.View.DetailMovieView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends BaseDetailFragment<Movie, DetailMovieModel, DetailMovieView>
    implements View.OnClickListener, PemainAdapter.OnPemainItemClickListener {

    private Parcelable mrvState;
    public static String EXTRA_STATE_RV="extrastaterv";
    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            mrvState=savedInstanceState.getParcelable(EXTRA_STATE_RV);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        persiapanSemua();
        if (savedInstanceState == null) {
            isiData(cListView.getProgressBar(),getActivity());
        }


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mrvState=cListView.getGridLayoutManager().onSaveInstanceState();
        outState.putParcelable(EXTRA_STATE_RV,mrvState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

    }



    public static void tampilkanToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    void persiapkanModel() {
        cListModel= ViewModelProviders.of(this)
                .get(DetailMovieModel.class);
    }

    @Override
    void persiapkanView() {
        cListView=new DetailMovieView(this);
        cListView.definisikanWidget();

    }

    @Override
    void persiapkanmodelObserver() {
        cListModel.getDetailData().observe(MovieDetailFragment.this,new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                    if (movie!=null) {
                        cListView.setDelete(getArguments().getBoolean(DetailActivity.DELETE,false));
                        cListView.tampilkanDetailData(mrvState, movie);
                        cListView.ubahDaftarPemain(movie.getmPemeran());
                    }
            }
        });
        cListModel.getDaftarPemain().observe(MovieDetailFragment.this,new Observer<ArrayList<Pemain>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Pemain> pemains) {
                cListView.ubahDaftarPemain(pemains);
            }
        });

    }

    @Override
    void isiData(ProgressBar progressBar, FragmentActivity activity) {
        Movie movie = null;
        if (getArguments() != null) {
            movie = getArguments().getParcelable(DetailActivity.DETAIL);
        }
        cListModel.setDetailData(progressBar,movie);
        String s=activity.getResources().getString(R.string.muat_ulang);
        MovieDetailFragment.tampilkanToast(activity,s);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTambahKeFavorit:
                FavoriteMovie favoriteMovie=new FavoriteMovie();
                favoriteMovie.setMMovieID(cListModel.getDetailData().getValue().getmMovieId());
                FavoriteMovieViewModel favoriteMovieViewModel=ViewModelProviders.of(this)
                        .get(FavoriteMovieViewModel.class);
                if (cListView.isDelete()){
                    favoriteMovieViewModel.deleteFavoriteMovie(favoriteMovie);
                    FavoriteMovieViewModel.needToReload=true;
                    MovieDetailFragment.tampilkanToast(getContext(),getActivity().getResources()
                    .getString(R.string.delete_success));
                }else {
                    favoriteMovieViewModel.insertFavoriteMovie(favoriteMovie);
//                    try {
//                        Thread.sleep(20000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    MovieDetailFragment.tampilkanToast(getContext(),getActivity().getResources()
                            .getString(R.string.add_success));
                }
                getActivity().finish();
                break;
            case R.id.img_poster:
                PhotoDialogFragment photoDialogFragment=new PhotoDialogFragment();
                Bundle bundle=new Bundle();
                bundle.putString(PhotoDialogFragment.IMG_ID2, cListModel.getDetailData().getValue().getmPhoto());
                photoDialogFragment.setArguments(bundle);
                FragmentManager fragmentManager=getChildFragmentManager();
                photoDialogFragment.show(fragmentManager,PhotoDialogFragment.class.getSimpleName());
                break;
        }
    }

    @Override
    public void onPemainItemClick(Pemain pemain) {
        PhotoDialogFragment photoDialogFragment=new PhotoDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putString(PhotoDialogFragment.IMG_ID2, pemain.getmFoto());
        photoDialogFragment.setArguments(bundle);
        FragmentManager fragmentManager=getChildFragmentManager();
        photoDialogFragment.show(fragmentManager,PhotoDialogFragment.class.getSimpleName());
    }
}
