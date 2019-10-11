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
import com.latihanandroid.mymoviecatalogue.Entity.FavoriteTVShow;
import com.latihanandroid.mymoviecatalogue.Entity.FavoriteTVShowViewModel;
import com.latihanandroid.mymoviecatalogue.Model.DetailTVShowModel;
import com.latihanandroid.mymoviecatalogue.Pojo.Pemain;
import com.latihanandroid.mymoviecatalogue.Pojo.TVShow;
import com.latihanandroid.mymoviecatalogue.View.DetailTVShowView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowDetailFragment extends BaseDetailFragment<TVShow, DetailTVShowModel, DetailTVShowView>
        implements View.OnClickListener, PemainAdapter.OnPemainItemClickListener{
    private static final String TAG = TVShowDetailFragment.class.getSimpleName();
    private Parcelable listState;
    public static String EXTRA_RELOAD="extrareload";
    public TVShowDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            listState=savedInstanceState.getParcelable("ListState");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        boolean reload = true;
        persiapanSemua();
        if (savedInstanceState == null) {
            isiData(cListView.getProgressBar(),this.getActivity());
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(EXTRA_RELOAD,false);
        outState.putParcelable("ListState", cListView.getRvPemain().getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onStart() {
        super.onStart();

    }



    public static void showToast(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }


    @Override
    void persiapkanModel() {
        cListModel= ViewModelProviders.of(this)
                .get(DetailTVShowModel.class);
    }

    @Override
    void persiapkanView() {
        cListView=new DetailTVShowView(this);
        cListView.definisikanWidget();
    }

    @Override
    void persiapkanmodelObserver() {
        cListModel.getDetailData().observe(this, new Observer<TVShow>() {
            @Override
            public void onChanged(@Nullable TVShow tvShow) {
                if (tvShow!=null){
                    cListView.setDelete(getArguments().getBoolean(DetailActivity.DELETE,false));
                    cListView.tampilkanDetailData(listState,tvShow);
                    cListModel.setDaftarPemain(tvShow.getmPemeran());
                }
            }
        });
        cListModel.getDaftarPemain().observe(this,new Observer<ArrayList<Pemain>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Pemain> pemains) {
                cListView.ubahDaftarPemain(pemains);
            }
        });

    }

    @Override
    void isiData(ProgressBar progressBar, FragmentActivity activity) {
        TVShow tvShow = null;
        if (getArguments() != null) {
            tvShow = getArguments().getParcelable(DetailActivity.DETAIL);
        }
        cListModel.setDetailData(progressBar,tvShow);
        String s=activity.getResources().getString(R.string.muat_ulang);
        MovieDetailFragment.tampilkanToast(activity,s);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTambahKeFavorit:
                FavoriteTVShow favoriteTVShow=new FavoriteTVShow();
                favoriteTVShow.setMTVShowID(cListModel.getDetailData().getValue().getmTVShowId());
                FavoriteTVShowViewModel favoriteTVShowViewModel=ViewModelProviders.of(this)
                        .get(FavoriteTVShowViewModel.class);
                if (cListView.isDelete()){
                    favoriteTVShowViewModel.deleteFavoriteTVShow(favoriteTVShow);
                    FavoriteTVShowViewModel.needToReload=true;
                    TVShowDetailFragment.showToast(getContext(),getActivity().getResources()
                            .getString(R.string.delete_success));
                }else {
                    favoriteTVShowViewModel.insertFavoriteTVShow(favoriteTVShow);
                    TVShowDetailFragment.showToast(getContext(),getActivity().getResources()
                            .getString(R.string.add_success));
                }
                getActivity().finish();
                break;
            case R.id.img_poster:
                PhotoDialogFragment photoDialogFragment=new PhotoDialogFragment();
                Bundle bundle=new Bundle();
                bundle.putString(PhotoDialogFragment.IMG_ID2, cListModel.getDetailData().getValue().getmPhotoUrl());
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
