package com.latihanandroid.mymoviecatalogue;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.latihanandroid.mymoviecatalogue.Adapter.FavoriteFragmentAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FavoriteFragmentAdapter fragmentAdapter;
    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabLayout=getView().findViewById(R.id.tabLayout);
        viewPager=getView().findViewById(R.id.viewPager);
        String fm=getResources().getString(R.string.favorite_movie);
        String ftv=getResources().getString(R.string.favorite_tv);
        tabLayout.addTab(tabLayout.newTab().setText(fm));
        tabLayout.addTab(tabLayout.newTab().setText(ftv));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fragmentAdapter=new FavoriteFragmentAdapter(getContext(),getChildFragmentManager(),2);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
