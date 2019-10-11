package com.latihanandroid.mymoviecatalogue;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoDialogFragment extends DialogFragment {
    private int photoId;
    public static final String IMG_ID="image_id";
    public static final String IMG_ID2="tvseries_url";
    private ImageView imgPhoto;
    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
//
//    public static PhotoDialogFragment createNewInstance(int photoId){
//        PhotoDialogFragment photoDialogFragment=new PhotoDialogFragment();
//        photoDialogFragment.setPhotoId(photoId);
//        return photoDialogFragment;
//    }
    public PhotoDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgPhoto=view.findViewById(R.id.poster);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int photoId;
        if (getArguments()!=null){
            photoId=getArguments().getInt(PhotoDialogFragment.IMG_ID,-1);
            if (photoId!=-1){
                Glide.with(getContext()).load(photoId).into(imgPhoto);
            }else {
                String photoUrl=getArguments().getString(IMG_ID2,null);
                if (photoUrl!=null){
                    Glide.with(getContext()).load(photoUrl).into(imgPhoto);
                }
            }
        }
    }
}
