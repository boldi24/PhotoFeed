package hu.boldizsartompe.photofeed.presenter.view.screens.main.myphotos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.boldizsartompe.photofeed.R;


public class MyPhotosFragment extends Fragment {


    public MyPhotosFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_photos, container, false);
    }

}
