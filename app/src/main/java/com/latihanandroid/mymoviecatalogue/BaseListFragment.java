package com.latihanandroid.mymoviecatalogue;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ProgressBar;

import com.latihanandroid.mymoviecatalogue.Adapter.ItemAdapterParent;
import com.latihanandroid.mymoviecatalogue.Model.BaseListModel;
import com.latihanandroid.mymoviecatalogue.View.BaseListView;

public abstract class BaseListFragment<T,M extends BaseListModel, V extends BaseListView> extends Fragment
        implements ItemAdapterParent.OnAdapterItemClickListener<T> {
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
