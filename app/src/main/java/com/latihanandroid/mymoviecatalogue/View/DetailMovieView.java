package com.latihanandroid.mymoviecatalogue.View;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.latihanandroid.mymoviecatalogue.Adapter.PemainAdapter;
import com.latihanandroid.mymoviecatalogue.Pojo.Movie;
import com.latihanandroid.mymoviecatalogue.Pojo.Pemain;
import com.latihanandroid.mymoviecatalogue.R;

import java.util.ArrayList;

public class DetailMovieView extends BaseDetailView<Movie> {
    private TextView tvJudul,tvDurasi,tvStatus,tvTanggalRilis,
            tvPemainTermahal,tvGenre,tvBahasaUtama,tvDeskripsi,
            tvBudget,tvPendapatan;
    private RecyclerView rvPemain;
    private ImageView imgPoster,imgBackdrop;
    private ProgressBar progressBar;
    private PemainAdapter pemainAdapter;
    private GridLayoutManager gridLayoutManager;
    private Button btnAddToFavorite;
    private boolean delete;
    public DetailMovieView(Fragment activity) {
        super(activity);
    }

    @Override
    public void definisikanWidget() {
        tvJudul=getActivity().getActivity().findViewById(R.id.tv_judul);
        tvDurasi=getActivity().getActivity().findViewById(R.id.tv_durasi);
        tvStatus=getActivity().getActivity().findViewById(R.id.tv_status);
        tvTanggalRilis=getActivity().getActivity().findViewById(R.id.tv_tgl_rilis);
        rvPemain=getActivity().getActivity().findViewById(R.id.rv_daftar_pemain);
        tvGenre=getActivity().getActivity().findViewById(R.id.tv_genre);
        tvBahasaUtama=getActivity().getActivity().findViewById(R.id.tv_bahasa_utama);
        tvDeskripsi=getActivity().getActivity().findViewById(R.id.tv_deskripsi);
        tvBudget=getActivity().getActivity().findViewById(R.id.tv_budget);
        tvPendapatan=getActivity().getActivity().findViewById(R.id.tv_pendapatan);
        imgPoster=getActivity().getActivity().findViewById(R.id.img_poster);
        imgBackdrop=getActivity().getActivity().findViewById(R.id.img_backdrop);
        progressBar=getActivity().getActivity().findViewById(R.id.prbar_movie_detail);
        btnAddToFavorite=getActivity().getActivity().findViewById(R.id.btnTambahKeFavorit);
        btnAddToFavorite.setOnClickListener((View.OnClickListener) getActivity());
    }

    @Override
    public void tampilkanDetailData(final Parcelable mrvState, final Movie movie) {
        tvJudul.setText(movie.getMJudul());
        String mnt= getActivity().getResources().getQuantityString(R.plurals.menit,
                movie.getMDurasi(),movie.getMDurasi());
        tvDurasi.setText(mnt);
        tvStatus.setText(movie.getMStatus());
        tvTanggalRilis.setText(movie.getMTanggalRilisAsString());
        pemainAdapter=new PemainAdapter();
        gridLayoutManager= new GridLayoutManager(
                getActivity().getContext(),1,GridLayoutManager.HORIZONTAL,false);
        rvPemain.setLayoutManager(gridLayoutManager);
        rvPemain.setHasFixedSize(true);
        rvPemain.setAdapter(pemainAdapter);
        rvPemain.getLayoutManager().onRestoreInstanceState(mrvState);
        pemainAdapter.notifyDataSetChanged();
        pemainAdapter.setOnPemainItemClickListener((PemainAdapter.OnPemainItemClickListener) getActivity());
        String genre= movie.getmGenre()[0];
        for (int i = 1; i< movie.getmGenre().length; i++){
            genre=genre+","+ movie.getmGenre()[i];
        }
        tvGenre.setText(genre);
        tvBahasaUtama.setText(movie.getFullStringmBahasaUtama());
        tvDeskripsi.setText(movie.getmDeskripsi());
        tvBudget.setText("$ "+String.format("%,d", movie.getmBudget()));
        tvPendapatan.setText("$ "+String.format("%,d", movie.getmPendapatan()));
        Glide.with(getActivity()).load(movie.getmPhoto()).into(imgPoster);
        Glide.with(getActivity()).load(movie.getmBackdroppathurl()).into(imgBackdrop);
        imgPoster.setOnClickListener((View.OnClickListener) getActivity());
        if (isDelete()){
            btnAddToFavorite.setText(getActivity().getResources().getString(R.string.delete_favorite));
        }else {
            btnAddToFavorite.setText(getActivity().getResources().getString(R.string.add_favorite));
        }
    }

    @Override
    public void ubahDaftarPemain(ArrayList<Pemain> pemains) {
        pemainAdapter.setPemains(pemains);
        pemainAdapter.notifyDataSetChanged();
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public PemainAdapter getPemainAdapter() {
        return pemainAdapter;
    }

    public void setPemainAdapter(PemainAdapter pemainAdapter) {
        this.pemainAdapter = pemainAdapter;
    }

    public GridLayoutManager getGridLayoutManager() {
        return gridLayoutManager;
    }

    public void setGridLayoutManager(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
