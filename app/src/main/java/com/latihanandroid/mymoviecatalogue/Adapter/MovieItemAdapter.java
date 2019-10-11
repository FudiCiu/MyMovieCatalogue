package com.latihanandroid.mymoviecatalogue.Adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.latihanandroid.mymoviecatalogue.Pojo.Movie;
import com.latihanandroid.mymoviecatalogue.R;

public class MovieItemAdapter extends ItemAdapterParent<Movie, MovieItemAdapter.MovieItemViewHolder> {


    public MovieItemAdapter(int viewGroupId) {
        super(viewGroupId);
    }

    @Override
    public void onBindItemViewHolder(@NonNull final MovieItemViewHolder movieViewHolder, int position) {
        final Movie currentMovie=getDatas().get(position);
        if (currentMovie!=null){
            movieViewHolder.tvJudul.setText(currentMovie.getMJudul());
            movieViewHolder.tvTglRilis.setText(currentMovie.getMTanggalRilisAsString());
            movieViewHolder.tvDeskripsi.setText(currentMovie.getmDeskripsi());
            Glide.with(movieViewHolder.itemView.getContext()).load(currentMovie.getmPhoto())
                    .into(movieViewHolder.imgPhoto);
        }else {
            Log.e(MovieItemAdapter.class.getSimpleName(), "Data at index "+position+" is null");
        }
    }

    @Override
    public MovieItemViewHolder onCreateItemViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie,viewGroup,false);
        return new MovieItemViewHolder(view);
    }

    public class MovieItemViewHolder extends ItemAdapterParent.ItemAdapterViewHolder {
        TextView tvJudul,tvTglRilis,tvDeskripsi;
        ImageView imgPhoto;
        public MovieItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void definisikanWidget() {
            tvJudul=itemView.findViewById(R.id.tv_judul);
            tvTglRilis=itemView.findViewById(R.id.tv_tgl_rilis);
            tvDeskripsi=itemView.findViewById(R.id.tv_deskripsi);
            imgPhoto=itemView.findViewById(R.id.img_photo);
        }
    }
}
