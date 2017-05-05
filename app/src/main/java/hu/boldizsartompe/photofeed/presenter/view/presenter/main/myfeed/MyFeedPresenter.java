package hu.boldizsartompe.photofeed.presenter.view.presenter.main.myfeed;

import android.os.Bundle;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.interactor.main.photos.common.GetPhotosInteractor;
import hu.boldizsartompe.photofeed.domain.interactor.main.photos.common.LikeInteractor;
import hu.boldizsartompe.photofeed.domain.interactor.main.photos.common.LikeInteractorImpl;
import hu.boldizsartompe.photofeed.domain.interactor.main.photos.myfeed.GetMyFeedPhotosInteractorImpl;
import hu.boldizsartompe.photofeed.presenter.rx.JobExecutor;
import hu.boldizsartompe.photofeed.presenter.view.presenter.BasePresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.comment.CommentActivity;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.myfeed.MyFeedView;

public class MyFeedPresenter extends BasePresenter<MyFeedView> {

    private GetPhotosInteractor getMyFeedPhotosInteractor;
    private LikeInteractor photoInteractor;

    private List<Photo> myFeedPhotos;

    public MyFeedPresenter() {
        getMyFeedPhotosInteractor = new GetMyFeedPhotosInteractorImpl();
        photoInteractor = new LikeInteractorImpl();
    }

    @Override
    public void attachView(MyFeedView view) {
        super.attachView(view);
        getMyFeedPhotosInteractor.onResume();

    }

    @Override
    public void detachView() {
        getMyFeedPhotosInteractor.onDestroy();
        super.detachView();
    }

    public void getMyFeedPhotos(){
        JobExecutor.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                getMyFeedPhotosInteractor.getPhotos(new GetPhotosInteractor.GetPhotosCallback() {
                    @Override
                    public void onGetMyPhotos(List<Photo> photos) {
                        if(mView != null){
                            myFeedPhotos = photos;
                            mView.stopRefreshing();
                            mView.showPhotos(photos);
                        }
                    }

                    @Override
                    public void onError() {
                        mView.stopRefreshing();
                    }
                });
            }
        });
    }

    public void likePhoto(int  position){
        photoInteractor.likePhoto(myFeedPhotos.get(position));
    }

    public void showComments(int position){
        if(isViewNotNull()){
            Bundle bundle = new Bundle();
            bundle.putString(CommentActivity.EXTRA_PHOTOID, myFeedPhotos.get(position).getId());

            mView.onStartActivityWithBundle(CommentActivity.class, bundle);
        }
    }

}
