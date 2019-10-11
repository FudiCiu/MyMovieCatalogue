package com.latihanandroid.mymoviecatalogue.Adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.latihanandroid.mymoviecatalogue.Pojo.TVShow;
import com.latihanandroid.mymoviecatalogue.R;

public class TVShowAdapter extends ItemAdapterParent<TVShow, TVShowAdapter.TVShowViewHolder> {
    public TVShowAdapter(int viewGroupId) {
        super(viewGroupId);
    }

    @Override
    public void onBindItemViewHolder(@NonNull TVShowViewHolder tvShowViewHolder, int position) {
        final TVShow tvShow=getDatas().get(position);
        tvShowViewHolder.tvJudul.setText(tvShow.getmJudul());
        tvShowViewHolder.tvTglRilis.setText(tvShow.getmTanggalRilisAsString());
        String season=tvShowViewHolder.itemView.getContext()
                .getResources()
                .getQuantityString(R.plurals.season,tvShow.getmJumlahSeason(),tvShow.getmJumlahSeason());
        String episodes=tvShowViewHolder.itemView.getContext()
                .getResources()
                .getQuantityString(R.plurals.episode,tvShow.getmJumlahEpisode(),tvShow.getmJumlahEpisode());
        tvShowViewHolder.tvJumlahSeason.setText(season);
        tvShowViewHolder.tvJumlahEpisode.setText(episodes);
        tvShowViewHolder.tvDeskripsi.setText(tvShow.getmDeskripsi());
        Glide.with(tvShowViewHolder.itemView.getContext()).load(tvShow.getmPhotoUrl()).
                into(tvShowViewHolder.imgPhoto);
    }

    @Override
    public TVShowViewHolder onCreateItemViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tvshow
                ,viewGroup,false);
        return new TVShowViewHolder(view);
    }

    public class TVShowViewHolder extends ItemAdapterParent.ItemAdapterViewHolder {
        TextView tvJudul,tvTglRilis,tvJumlahSeason,tvJumlahEpisode,tvDeskripsi;
        ImageView imgPhoto;
        public TVShowViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void definisikanWidget() {
            tvJudul= itemView.findViewById(R.id.tv_judul);
            tvTglRilis=itemView.findViewById(R.id.tv_tgl_rilis);
            tvDeskripsi=itemView.findViewById(R.id.tv_deskripsi);
            tvJumlahSeason=itemView.findViewById(R.id.tv_jumlah_season);
            tvJumlahEpisode=itemView.findViewById(R.id.tv_jumlah_episode);
            imgPhoto=itemView.findViewById(R.id.img_photo);
        }
    }
}
