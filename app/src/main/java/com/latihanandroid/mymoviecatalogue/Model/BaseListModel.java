package com.latihanandroid.mymoviecatalogue.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.ProgressBar;

import com.latihanandroid.mymoviecatalogue.Pojo.Pemain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class BaseListModel<T> extends ViewModel {
    private final String TAG=getClass().getSimpleName();
    protected MutableLiveData<ArrayList<T>> listData=new MutableLiveData<>();
    static final String baseimage_url="https://image.tmdb.org/t/p/w500";
    static final String API_KEY="47078f3db47d2cd23dff56874096837c";
    protected static MutableLiveData<String> lang=new MutableLiveData<>();
    public static void setLang(String langa){
        lang.setValue(langa);
    }
    public static LiveData<String> getLang(){
        return lang;
    }

    public LiveData<ArrayList<T>> getListData() {
        return this.listData;
    }

    public abstract void setListData(ProgressBar progressBar, Context context);

    protected ArrayList<Pemain> parsingPemains(JSONArray pemainJSON){
        ArrayList<Pemain> pemains=new ArrayList<>();
        for (int i=0;i<pemainJSON.length();i++){
            try {
                JSONObject jsonPemeran=pemainJSON.getJSONObject(i);
                pemains.add(parsingPemain(jsonPemeran));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return pemains;
    }

    protected Pemain parsingPemain(JSONObject jsonPemeran){
        try {
            String mNamaPemeran= jsonPemeran.getString("name");
            String mKarakter=jsonPemeran.getString("character");
            String mFoto=baseimage_url+jsonPemeran.getString("profile_path");
            Pemain pemain = new Pemain(mNamaPemeran,mKarakter,mFoto);
            return pemain;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
