package com.latihanandroid.mymoviecatalogue.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
public class Movie implements Parcelable {
    private String mMovieId;
    private String mJudul;
    private int mDurasi;
    private String mStatus;
    private Date mTanggalRilis;
    private ArrayList<Pemain> mPemeran;
    private String[] mGenre;
    private String mBahasaUtama;
    private String mDeskripsi;
    private int mBudget;
    private int mPendapatan;
    private String mPhoto;
    private String mBackdroppathurl;
    public Movie(String mJudul, int mDurasi, String mStatus, Date mTanggalRilis, ArrayList<Pemain> mPemeran, String[] mGenre, String mBahasaUtama, String mDeskripsi, int mBudget, int mPendapatan, String mPhoto, String mBackdroppathurl) {
        this.mJudul = mJudul;
        this.mDurasi = mDurasi;
        this.mStatus = mStatus;
        this.mTanggalRilis = mTanggalRilis;
        this.mPemeran = mPemeran;
        this.mGenre = mGenre;
        this.mBahasaUtama = mBahasaUtama;
        this.mDeskripsi = mDeskripsi;
        this.mBudget = mBudget;
        this.mPendapatan = mPendapatan;
        this.mPhoto = mPhoto;
        this.mBackdroppathurl=mBackdroppathurl;
    }

    public Movie(String mMovieId,String mJudul, int mDurasi, String mStatus, Date mTanggalRilis, ArrayList<Pemain> mPemeran, String[] mGenre, String mBahasaUtama, String mDeskripsi, int mBudget, int mPendapatan, String mPhoto, String mBackdroppathurl) {
        this.mMovieId = mMovieId;
        this.mJudul = mJudul;
        this.mDurasi = mDurasi;
        this.mStatus = mStatus;
        this.mTanggalRilis = mTanggalRilis;
        this.mPemeran = mPemeran;
        this.mGenre = mGenre;
        this.mBahasaUtama = mBahasaUtama;
        this.mDeskripsi = mDeskripsi;
        this.mBudget = mBudget;
        this.mPendapatan = mPendapatan;
        this.mPhoto = mPhoto;
        this.mBackdroppathurl = mBackdroppathurl;
    }

    protected Movie(Parcel in) {
        mMovieId=in.readString();
        mJudul = in.readString();
        mDurasi = in.readInt();
        mStatus = in.readString();
        mTanggalRilis= new Date(in.readLong());
        mPemeran=in.createTypedArrayList(Pemain.CREATOR);
        mGenre = in.createStringArray();
        mBahasaUtama = in.readString();
        mDeskripsi = in.readString();
        mBudget = in.readInt();
        mPendapatan = in.readInt();
        mPhoto=in.readString();
        mBackdroppathurl=in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getMJudul() {
        return mJudul;
    }

    public void setMJudul(String mJudul) {
        this.mJudul = mJudul;
    }

    public int getMDurasi() {
        return mDurasi;
    }

    public void setMDurasi(int mDurasi) {
        this.mDurasi = mDurasi;
    }

    public String getMStatus() {
        return mStatus;
    }

    public void setMStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public Date getMTanggalRilis() {
        return mTanggalRilis;
    }

    public String getMTanggalRilisAsString(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMMM YYYY", Locale.getDefault());
        return simpleDateFormat.format(mTanggalRilis);
    }
    public void setMTanggalRilis(Date mTanggalRilis) {
        this.mTanggalRilis = mTanggalRilis;
    }

    public ArrayList<Pemain> getmPemeran() {
        return mPemeran;
    }

    public void setmPemeran(ArrayList<Pemain> mPemeran) {
        this.mPemeran = mPemeran;
    }

    public String[] getmGenre() {
        return mGenre;
    }

    public void setmGenre(String[] mGenre) {
        this.mGenre = mGenre;
    }

    public String getmBahasaUtama() {
        return mBahasaUtama;
    }

    public void setmBahasaUtama(String mBahasaUtama) {
        this.mBahasaUtama = mBahasaUtama;
    }

    public String getmDeskripsi() {
        return mDeskripsi;
    }

    public void setmDeskripsi(String mDeskripsi) {
        this.mDeskripsi = mDeskripsi;
    }

    public int getmBudget() {
        return mBudget;
    }

    public void setmBudget(int mBudget) {
        this.mBudget = mBudget;
    }

    public int getmPendapatan() {
        return mPendapatan;
    }

    public void setmPendapatan(int mPendapatan) {
        this.mPendapatan = mPendapatan;
    }

    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getmBackdroppathurl() {
        return mBackdroppathurl;
    }

    public String getFullStringmBahasaUtama(){
        Locale locale=new Locale(mBahasaUtama);
        return locale.getDisplayLanguage();
    }
    public void setmBackdroppathurl(String mBackdroppathurl) {
        this.mBackdroppathurl = mBackdroppathurl;
    }

    public String getmMovieId() {
        return mMovieId;
    }

    public void setmMovieId(String mMovieId) {
        this.mMovieId = mMovieId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mMovieId);
        parcel.writeString(mJudul);
        parcel.writeInt(mDurasi);
        parcel.writeString(mStatus);
        parcel.writeLong(mTanggalRilis.getTime());
        parcel.writeTypedList(mPemeran);
        parcel.writeStringArray(mGenre);
        parcel.writeString(mBahasaUtama);
        parcel.writeString(mDeskripsi);
        parcel.writeInt(mBudget);
        parcel.writeInt(mPendapatan);
        parcel.writeString(mPhoto);
        parcel.writeString(mBackdroppathurl);
    }
}
