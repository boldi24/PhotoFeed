package hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.myfeed;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.presenter.view.BaseActivity;
import hu.boldizsartompe.photofeed.presenter.view.presenter.main.myfeed.MyFeedPresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.BasePhotosFragment;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.myphotos.MyPhotosAdapter;

public class MyFeedFragment extends BasePhotosFragment implements MyFeedView, MyFeedAdapter.IPhoto {


    @BindView(R.id.rv_myfeed)
    RecyclerView recyclerView;
    @BindView(R.id.srf_myfeed)
    SwipeRefreshLayout swipeRefreshLayout;

    private MyFeedAdapter adapter;

    private MyFeedPresenter myFeedPresenter;

    public MyFeedFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myFeedPresenter = new MyFeedPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new MyFeedAdapter(getContext(), new ArrayList<Photo>(), this);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myFeedPresenter.getMyFeedPhotos();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        myFeedPresenter.attachView(this);
        myFeedPresenter.getMyFeedPhotos();
    }

    @Override
    public void onStop() {
        myFeedPresenter.detachView();
        super.onStop();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_my_feed;
    }

    @Override
    public void showPhotos(List<Photo> photos) {
        adapter.addPhotos(photos);
    }

    @Override
    public void likePhoto(int position) {
        myFeedPresenter.likePhoto(position);
    }

    @Override
    public void commentClicked(int position) {
        myFeedPresenter.showComments(position);
    }


    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
