package com.latihanandroid.mymoviecatalogue.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Pemain implements Parcelable {
    private String mNama;
    private String mKarakter;
    private String mFoto;
    public Pemain(String mNama, String mKarakter) {
        this.mNama = mNama;
        this.mKarakter = mKarakter;
    }

    public Pemain(String mNama, String mKarakter, String mFoto) {
        this.mNama = mNama;
        this.mKarakter = mKarakter;
        this.mFoto = mFoto;
    }

    protected Pemain(Parcel in) {
        mNama = in.readString();
        mKarakter = in.readString();
        mFoto=in.readString();
    }

    public static final Creator<Pemain> CREATOR = new Creator<Pemain>() {
        @Override
        public Pemain createFromParcel(Parcel in) {
            return new Pemain(in);
        }

        @Override
        public Pemain[] newArray(int size) {
            return new Pemain[size];
        }
    };

    public String getmFoto() {
        return mFoto;
    }

    public void setmFoto(String mFoto) {
        this.mFoto = mFoto;
    }

    public String getmNama() {
        return mNama;
    }

    public void setmNama(String mNama) {
        this.mNama = mNama;
    }

    public String getmKarakter() {
        return mKarakter;
    }

    public void setmKarakter(String mKarakter) {
        this.mKarakter = mKarakter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mNama);
        parcel.writeString(mKarakter);
        parcel.writeString(mFoto);
    }
}
