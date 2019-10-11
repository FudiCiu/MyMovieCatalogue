package com.latihanandroid.mymoviecatalogue.Activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.latihanandroid.mymoviecatalogue.MovieDetailFragment;
import com.latihanandroid.mymoviecatalogue.R;
import com.latihanandroid.mymoviecatalogue.TVShowDetailFragment;


public class DetailActivity extends AppCompatActivity {
    public static final String DETAIL="detail";
    public static final String DELETE="tvshow_detail";
    public static final String OPSI="opsi";
    private static final String TAG = DetailActivity.class.getSimpleName() ;
    private Bundle bundle;
    private Parcelable parcelable;
    private int pilihan;
    private boolean isDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        pilihan=getIntent().getIntExtra(DetailActivity.OPSI,0);;
        parcelable=getIntent()
                .getParcelableExtra(DETAIL);
        isDelete=getIntent().getBooleanExtra(DetailActivity.DELETE,false);
        if (savedInstanceState==null) {
            tampilkanFragmentSesuai(pilihan);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public  void tampilkanFragmentSesuai(int opsi) {
        bundle=new Bundle();
        bundle.putParcelable(DetailActivity.DETAIL,parcelable);
        bundle.putBoolean(DetailActivity.DELETE,isDelete);
        Fragment fragment=null;
        switch (opsi){
            case 0:
                fragment=new MovieDetailFragment();
                break;
            case 1:
                fragment= new TVShowDetailFragment();
                break;
        }
        if (fragment==null){
            Toast.makeText(this,"Fragment is null",Toast.LENGTH_SHORT).show();
        return;
    }
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_layout,fragment)
                .commit();
        Log.e(TAG, "tampilkanFragmentSesuai: tes");
    }
}
