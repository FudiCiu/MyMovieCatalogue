package com.latihanandroid.mymoviecatalogue;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ProgressBar;

import com.latihanandroid.mymoviecatalogue.Model.BaseDetailModel;
import com.latihanandroid.mymoviecatalogue.View.BaseDetailView;

public abstract class BaseDetailFragment<T,M extends BaseDetailModel,V extends BaseDetailView> extends Fragment {
    protected M cListModel;
    protected V cListView;
    abstract void persiapkanModel();
    abstract void persiapkanView();
    abstract void persiapkanmodelObserver();
    public void persiapanSemua(){
        persiapkanModel();
        persiapkanView();
        persiapkanmodelObserver();
    }
    abstract void isiData(ProgressBar progressBar, FragmentActivity activity);
}
