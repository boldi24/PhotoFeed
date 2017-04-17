package hu.boldizsartompe.photofeed.presenter.view.screens.main.myfeed;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.boldizsartompe.photofeed.R;

public class MyFeedFragment extends Fragment {


    public MyFeedFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_feed, container, false);
    }

}
