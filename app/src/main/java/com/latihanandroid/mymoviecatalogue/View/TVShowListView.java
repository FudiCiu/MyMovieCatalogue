package com.latihanandroid.mymoviecatalogue.View;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.latihanandroid.mymoviecatalogue.Adapter.ItemAdapterParent;
import com.latihanandroid.mymoviecatalogue.Adapter.TVShowAdapter;
import com.latihanandroid.mymoviecatalogue.Pojo.TVShow;
import com.latihanandroid.mymoviecatalogue.R;
import com.latihanandroid.mymoviecatalogue.TVShowListFragment;

import java.util.ArrayList;

public class TVShowListView extends BaseListView<TVShow> {
    private Fragment fragmentActivity;
    private RecyclerView lsTVShow;
    private TVShowAdapter tvShowItemAdapter;
    private ProgressBar progressBar;

    public Fragment getFragmentActivity() {
        return fragmentActivity;
    }

    public void setFragmentActivity(Fragment fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    public RecyclerView getLsTVShow() {
        return lsTVShow;
    }

    public void setLsTVShow(RecyclerView lsTVShow) {
        this.lsTVShow = lsTVShow;
    }

    public TVShowAdapter getTvShowItemAdapter() {
        return tvShowItemAdapter;
    }

    public void setTvShowItemAdapter(TVShowAdapter tvShowItemAdapter) {
        this.tvShowItemAdapter = tvShowItemAdapter;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public TVShowListView(Fragment fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
        persiapanListView();
    }

    public void definisikanWidget(){
        lsTVShow= fragmentActivity.getActivity().findViewById(R.id.ls_tvshow);
        progressBar= fragmentActivity.getActivity().findViewById(R.id.prbar_tvshow_list);
    };
    public void pasangListenerPadaWidget(){
        tvShowItemAdapter.setOnAdapterItemClickListener((ItemAdapterParent.OnAdapterItemClickListener<TVShow>) fragmentActivity);
    };

    @Override
    public void siapkanKomponenAdapter() {
        tvShowItemAdapter=new TVShowAdapter(R.layout.item_tvshow);
        tvShowItemAdapter.notifyDataSetChanged();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(fragmentActivity.getContext(),LinearLayoutManager.VERTICAL,
                false);
        lsTVShow.setLayoutManager(linearLayoutManager);
        lsTVShow.setAdapter(tvShowItemAdapter);

    }

    @Override
    public void ubahDataPadaListView(ArrayList<TVShow> tvShows) {
        if (tvShows!=null){
            tvShowItemAdapter.setDatas(tvShows);
            tvShowItemAdapter.notifyDataSetChanged();
        }else {
            TVShowListFragment.showToast(fragmentActivity.getContext(),"Data is null");
        }
    }

}
