package com.latihanandroid.mymoviecatalogue.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.view.View;
import android.widget.ProgressBar;

import com.latihanandroid.mymoviecatalogue.Pojo.Pemain;

import java.util.ArrayList;

public class BaseDetailModel<T> extends ViewModel {
    protected MutableLiveData<T> detailData=new MutableLiveData<>();
    protected MutableLiveData<ArrayList<Pemain>> daftarPemain=new MutableLiveData<>();
    public void setDetailData(final ProgressBar progressBar, T data){
        progressBar.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        detailData.postValue(data);
        progressBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        },800);
    }

    public LiveData<T> getDetailData() {
        return detailData;
    }
    public void setDaftarPemain(ArrayList<Pemain> pemains){
        daftarPemain.postValue(pemains);
    }

    public LiveData<ArrayList<Pemain>> getDaftarPemain() {
        return daftarPemain;
    }

}
