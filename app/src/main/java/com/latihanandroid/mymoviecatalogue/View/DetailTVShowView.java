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
import com.latihanandroid.mymoviecatalogue.Pojo.Pemain;
import com.latihanandroid.mymoviecatalogue.Pojo.TVShow;
import com.latihanandroid.mymoviecatalogue.R;

import java.util.ArrayList;

public class DetailTVShowView extends BaseDetailView<TVShow> {
    private TextView tvJudul,tvDurasi,tvStatus,tvTanggalRilis,
            tvPemainTermahal,tvGenre,tvBahasaUtama,tvDeskripsi,
            tvEpisode, tvSeason;
    private RecyclerView rvPemain;
    private ImageView imgPoster,imgBackdrop;
    private PemainAdapter pemainAdapter;
    private ProgressBar progressBar;
    private GridLayoutManager linearLayoutManager;
    private Button btnAddToFavorite;
    public DetailTVShowView(Fragment activity) {
        super(activity);
    }
    private boolean delete;


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
        tvEpisode=getActivity().getActivity().findViewById(R.id.tv_jumlah_episode);
        tvSeason=getActivity().getActivity().findViewById(R.id.tv_jumlah_season);
        imgPoster=getActivity().getActivity().findViewById(R.id.img_poster);
        imgBackdrop=getActivity().getActivity().findViewById(R.id.img_backdrop);
        progressBar=getActivity().getActivity().findViewById(R.id.prbar_tvshow_detail);
        btnAddToFavorite=getActivity().getActivity().findViewById(R.id.btnTambahKeFavorit);
        btnAddToFavorite.setOnClickListener((View.OnClickListener) getActivity());
    }

    @Override
    public void tampilkanDetailData(Parcelable mrvState, final TVShow tvShow) {
        tvJudul.setText(String.format(getActivity().getResources().getString(R.string.judul_value),
                tvShow.getmJudul()));
        String mnt= String.format(getActivity().getResources().getQuantityString(R.plurals.menit,
                tvShow.getmDurasiPerEpisode(),
                tvShow.getmDurasiPerEpisode()));
        tvDurasi.setText(mnt);
        tvStatus.setText(tvShow.getmStatus());
        tvTanggalRilis.setText(tvShow.getmTanggalRilisAsString());
        pemainAdapter=new PemainAdapter(tvShow.getmPemeran());
        linearLayoutManager= new GridLayoutManager(getActivity().getContext(),1,GridLayoutManager.HORIZONTAL,false);
        rvPemain.setLayoutManager(linearLayoutManager);
        rvPemain.setHasFixedSize(true);
        rvPemain.setAdapter(pemainAdapter);
        rvPemain.setSaveEnabled(true);
        rvPemain.getLayoutManager().onRestoreInstanceState(mrvState);
        pemainAdapter.notifyDataSetChanged();
        pemainAdapter.setOnPemainItemClickListener((PemainAdapter.OnPemainItemClickListener) getActivity());
        String genre= tvShow.getmGenre()[0];
        for (int i = 1; i< tvShow.getmGenre().length; i++){
            genre=genre+","+ tvShow.getmGenre()[i];
        }
        tvGenre.setText(genre);
        tvBahasaUtama.setText(tvShow.getFullStringmBahasaUtama());
        tvDeskripsi.setText(tvShow.getmDeskripsi());
        String season=String.format(getActivity()
                .getResources()
                .getQuantityString(R.plurals.season,tvShow.getmJumlahSeason(),
                        tvShow.getmJumlahSeason()));
        String episodes=String.format(getActivity()
                .getResources()
                .getQuantityString(R.plurals.episode,tvShow.getmJumlahEpisode(),
                        tvShow.getmJumlahEpisode()));
        tvEpisode.setText(episodes);
        tvSeason.setText(season);
        Glide.with(getActivity()).load(tvShow.getmPhotoUrl()).into(imgPoster);
        Glide.with(getActivity()).load(tvShow.getmBackdroppathurl()).into(imgBackdrop);
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

    public TextView getTvJudul() {
        return tvJudul;
    }

    public void setTvJudul(TextView tvJudul) {
        this.tvJudul = tvJudul;
    }

    public TextView getTvDurasi() {
        return tvDurasi;
    }

    public void setTvDurasi(TextView tvDurasi) {
        this.tvDurasi = tvDurasi;
    }

    public TextView getTvStatus() {
        return tvStatus;
    }

    public void setTvStatus(TextView tvStatus) {
        this.tvStatus = tvStatus;
    }

    public TextView getTvTanggalRilis() {
        return tvTanggalRilis;
    }

    public void setTvTanggalRilis(TextView tvTanggalRilis) {
        this.tvTanggalRilis = tvTanggalRilis;
    }

    public TextView getTvPemainTermahal() {
        return tvPemainTermahal;
    }

    public void setTvPemainTermahal(TextView tvPemainTermahal) {
        this.tvPemainTermahal = tvPemainTermahal;
    }

    public TextView getTvGenre() {
        return tvGenre;
    }

    public void setTvGenre(TextView tvGenre) {
        this.tvGenre = tvGenre;
    }

    public TextView getTvBahasaUtama() {
        return tvBahasaUtama;
    }

    public void setTvBahasaUtama(TextView tvBahasaUtama) {
        this.tvBahasaUtama = tvBahasaUtama;
    }

    public TextView getTvDeskripsi() {
        return tvDeskripsi;
    }

    public void setTvDeskripsi(TextView tvDeskripsi) {
        this.tvDeskripsi = tvDeskripsi;
    }

    public TextView getTvEpisode() {
        return tvEpisode;
    }

    public void setTvEpisode(TextView tvEpisode) {
        this.tvEpisode = tvEpisode;
    }

    public TextView getTvSeason() {
        return tvSeason;
    }

    public void setTvSeason(TextView tvSeason) {
        this.tvSeason = tvSeason;
    }

    public RecyclerView getRvPemain() {
        return rvPemain;
    }

    public void setRvPemain(RecyclerView rvPemain) {
        this.rvPemain = rvPemain;
    }

    public ImageView getImgPoster() {
        return imgPoster;
    }

    public void setImgPoster(ImageView imgPoster) {
        this.imgPoster = imgPoster;
    }

    public ImageView getImgBackdrop() {
        return imgBackdrop;
    }

    public void setImgBackdrop(ImageView imgBackdrop) {
        this.imgBackdrop = imgBackdrop;
    }

    public PemainAdapter getPemainAdapter() {
        return pemainAdapter;
    }

    public void setPemainAdapter(PemainAdapter pemainAdapter) {
        this.pemainAdapter = pemainAdapter;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public GridLayoutManager getLinearLayoutManager() {
        return linearLayoutManager;
    }

    public void setLinearLayoutManager(GridLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
