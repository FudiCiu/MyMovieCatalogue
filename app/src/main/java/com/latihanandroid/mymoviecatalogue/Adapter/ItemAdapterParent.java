package com.latihanandroid.mymoviecatalogue.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class ItemAdapterParent<T,Q extends ItemAdapterParent.ItemAdapterViewHolder> extends RecyclerView.Adapter<Q> {
    private int viewGroupId;
    private ArrayList<T> datas=new ArrayList<>();
    private OnAdapterItemClickListener<T> onAdapterItemClickListener;



    public ItemAdapterParent(int viewGroupId) {
        this.viewGroupId = viewGroupId;
    }

    public ItemAdapterParent(int viewGroupId, ArrayList<T> datas, OnAdapterItemClickListener<T> adapterItemClickListener) {
        this.viewGroupId = viewGroupId;
        this.datas = datas;
        this.onAdapterItemClickListener=adapterItemClickListener;
    }
    public abstract void onBindItemViewHolder(@NonNull Q holder, int position);
    public abstract Q onCreateItemViewHolder(@NonNull ViewGroup viewGroup, int position);
    @Override
    public void onBindViewHolder(@NonNull final Q holder, final int position) {
        if (onAdapterItemClickListener!=null)
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onAdapterItemClickListener.onDataItemClick(datas.get(holder.getAdapterPosition()));
                }
            });
        onBindItemViewHolder(holder,position);
    }

    @NonNull
    @Override
    public Q onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(viewGroupId,viewGroup,false);
        return onCreateItemViewHolder(viewGroup,i);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public int getViewGroupId() {
        return viewGroupId;
    }

    public void setViewGroupId(int viewGroupId) {
        this.viewGroupId = viewGroupId;
    }

    public ArrayList<T> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
    public OnAdapterItemClickListener<T> getOnAdapterItemClickListener() {
        return onAdapterItemClickListener;
    }

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener<T> onAdapterItemClickListener) {
        this.onAdapterItemClickListener = onAdapterItemClickListener;
    }

    public abstract class ItemAdapterViewHolder extends RecyclerView.ViewHolder {
        public abstract void definisikanWidget();
        public ItemAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            definisikanWidget();
        }
    }
    public interface OnAdapterItemClickListener<T>{
        public void onDataItemClick(T data);
    }
}
