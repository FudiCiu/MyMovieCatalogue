package com.latihanandroid.mymoviecatalogue.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.latihanandroid.mymoviecatalogue.Pojo.Pemain;
import com.latihanandroid.mymoviecatalogue.R;

import java.util.ArrayList;

public class PemainAdapter extends RecyclerView.Adapter<PemainAdapter.PemainViewHolder>
{
    private ArrayList<Pemain> pemains=new ArrayList<>();
    private OnPemainItemClickListener onPemainItemClickListener;
    public PemainAdapter(ArrayList<Pemain> pemains) {
        this.pemains = pemains;
    }

    public PemainAdapter() {

    }

    public ArrayList<Pemain> getPemains() {
        return pemains;
    }

    public void setPemains(ArrayList<Pemain> pemains) {
        this.pemains = pemains;
    }

    public OnPemainItemClickListener getOnPemainItemClickListener() {
        return onPemainItemClickListener;
    }

    public void setOnPemainItemClickListener(OnPemainItemClickListener onPemainItemClickListener) {
        this.onPemainItemClickListener = onPemainItemClickListener;
    }

    @NonNull
    @Override
    public PemainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pemain,viewGroup,false);
        return new PemainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PemainViewHolder pemainViewHolder, int i) {
        final Pemain pemain= pemains.get(i);
        pemainViewHolder.tvNama.setText(pemain.getmNama());
        pemainViewHolder.tvKarakter.setText(pemain.getmKarakter());
        Glide.with(pemainViewHolder.itemView.getContext()).load(pemain.getmFoto()).
                into(pemainViewHolder.imgFoto);
        pemainViewHolder.imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onPemainItemClickListener!=null){
                    onPemainItemClickListener.onPemainItemClick(pemain);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pemains.size();
    }

    public class PemainViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama,tvKarakter;
        private ImageView imgFoto;
        public PemainViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama=itemView.findViewById(R.id.tvNama);
            tvKarakter=itemView.findViewById(R.id.tvChar);
            imgFoto=itemView.findViewById(R.id.imgPhoto);
        }
    }

    public interface OnPemainItemClickListener{
        public void onPemainItemClick(Pemain pemain);
    }
}
