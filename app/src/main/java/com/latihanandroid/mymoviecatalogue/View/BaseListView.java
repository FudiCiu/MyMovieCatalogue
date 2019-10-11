package com.latihanandroid.mymoviecatalogue.View;

import java.util.ArrayList;

public abstract class BaseListView<T> {
    public abstract void definisikanWidget();

    public abstract void pasangListenerPadaWidget();

    public abstract void siapkanKomponenAdapter();

    public abstract void ubahDataPadaListView(ArrayList<T> datas);

    public void persiapanListView() {
        definisikanWidget();
        siapkanKomponenAdapter();
        pasangListenerPadaWidget();
    }
}