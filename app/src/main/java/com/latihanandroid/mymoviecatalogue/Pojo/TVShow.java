package com.latihanandroid.mymoviecatalogue.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TVShow implements Parcelable {
    private String mTVShowId;
    private String mJudul;
    private int mJumlahEpisode;
    private String mStatus;
    private Date mTanggalRilis;
    private ArrayList<Pemain> mPemeran;
    private String[] mGenre;
    private String mBahasaUtama;
    private String mDeskripsi;
    private int mJumlahSeason;
    private int mDurasiPerEpisode;
    private String mPhotoUrl;
    private String mBackdroppathurl;

    public TVShow(String mJudul, int mJumlahEpisode, String mStatus, Date mTanggalRilis, ArrayList<Pemain> mPemeran, String[] mGenre, String mBahasaUtama, String mDeskripsi, int mJumlahSeason, int mDurasiPerEpisode, String mPhotoUrl, String mBackdroppathurl) {
        this.mJudul = mJudul;
        this.mJumlahEpisode = mJumlahEpisode;
        this.mStatus = mStatus;
        this.mTanggalRilis = mTanggalRilis;
        this.mPemeran = mPemeran;
        this.mGenre = mGenre;
        this.mBahasaUtama = mBahasaUtama;
        this.mDeskripsi = mDeskripsi;
        this.mJumlahSeason = mJumlahSeason;
        this.mDurasiPerEpisode = mDurasiPerEpisode;
        this.mPhotoUrl = mPhotoUrl;
        this.mBackdroppathurl = mBackdroppathurl;
    }

    public TVShow(String mTVShowId, String mJudul, int mJumlahEpisode, String mStatus, Date mTanggalRilis, ArrayList<Pemain> mPemeran, String[] mGenre, String mBahasaUtama, String mDeskripsi, int mJumlahSeason, int mDurasiPerEpisode, String mPhotoUrl, String mBackdroppathurl) {
        this.mTVShowId = mTVShowId;
        this.mJudul = mJudul;
        this.mJumlahEpisode = mJumlahEpisode;
        this.mStatus = mStatus;
        this.mTanggalRilis = mTanggalRilis;
        this.mPemeran = mPemeran;
        this.mGenre = mGenre;
        this.mBahasaUtama = mBahasaUtama;
        this.mDeskripsi = mDeskripsi;
        this.mJumlahSeason = mJumlahSeason;
        this.mDurasiPerEpisode = mDurasiPerEpisode;
        this.mPhotoUrl = mPhotoUrl;
        this.mBackdroppathurl = mBackdroppathurl;
    }

    public String getmJudul() {
        return mJudul;
    }

    public void setmJudul(String mJudul) {
        this.mJudul = mJudul;
    }

    public int getmJumlahEpisode() {
        return mJumlahEpisode;
    }

    public void setmJumlahEpisode(int mJumlahEpisode) {
        this.mJumlahEpisode = mJumlahEpisode;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public Date getmTanggalRilis() {
        return mTanggalRilis;
    }

    public void setmTanggalRilis(Date mTanggalRilis) {
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

    public int getmJumlahSeason() {
        return mJumlahSeason;
    }

    public void setmJumlahSeason(int mJumlahSeason) {
        this.mJumlahSeason = mJumlahSeason;
    }

    public int getmDurasiPerEpisode() {
        return mDurasiPerEpisode;
    }

    public void setmDurasiPerEpisode(int mDurasiPerEpisode) {
        this.mDurasiPerEpisode = mDurasiPerEpisode;
    }

    public String getmPhotoUrl() {
        return mPhotoUrl;
    }

    public void setmPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }

    public String getmBackdroppathurl() {
        return mBackdroppathurl;
    }

    public void setmBackdroppathurl(String mBackdroppathurl) {
        this.mBackdroppathurl = mBackdroppathurl;
    }

    public String getmTanggalRilisAsString(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd MMMM YYYY", Locale.getDefault());
        return simpleDateFormat.format(mTanggalRilis);
    }

    public String getFullStringmBahasaUtama(){
        Locale locale=new Locale(mBahasaUtama);
        return locale.getDisplayLanguage();
    }

    public String getmTVShowId() {
        return mTVShowId;
    }

    public void setmTVShowId(String mTVShowId) {
        this.mTVShowId = mTVShowId;
    }

    protected TVShow(Parcel in) {
        mTVShowId=in.readString();
        mJudul = in.readString();
        mJumlahEpisode = in.readInt();
        mStatus = in.readString();
        mTanggalRilis= new Date(in.readLong());
        mPemeran = in.createTypedArrayList(Pemain.CREATOR);
        mGenre = in.createStringArray();
        mBahasaUtama = in.readString();
        mDeskripsi = in.readString();
        mJumlahSeason = in.readInt();
        mDurasiPerEpisode = in.readInt();
        mPhotoUrl = in.readString();
        mBackdroppathurl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTVShowId);
        dest.writeString(mJudul);
        dest.writeInt(mJumlahEpisode);
        dest.writeString(mStatus);
        dest.writeLong(mTanggalRilis.getTime());
        dest.writeTypedList(mPemeran);
        dest.writeStringArray(mGenre);
        dest.writeString(mBahasaUtama);
        dest.writeString(mDeskripsi);
        dest.writeInt(mJumlahSeason);
        dest.writeInt(mDurasiPerEpisode);
        dest.writeString(mPhotoUrl);
        dest.writeString(mBackdroppathurl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };
}
