package com.latihanandroid.mymoviecatalogue.View;

import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.latihanandroid.mymoviecatalogue.Pojo.Pemain;

import java.util.ArrayList;

public abstract class BaseDetailView<T> {
    public BaseDetailView(Fragment activity) {
        this.activity = activity;
    }
    private Fragment activity;

    public Fragment getActivity() {
        return activity;
    }

    public void setActivity(Fragment activity) {
        this.activity = activity;
    }

    public abstract void definisikanWidget();
    public abstract void tampilkanDetailData(Parcelable mrvState,T data);
    public void tampilkanHasil(Parcelable mrvState,T data){
        definisikanWidget();
        tampilkanDetailData(mrvState,data);
    }
    public abstract void ubahDaftarPemain(ArrayList<Pemain> pemains);
}
