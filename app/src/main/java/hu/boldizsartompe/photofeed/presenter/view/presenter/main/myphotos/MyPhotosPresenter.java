package hu.boldizsartompe.photofeed.presenter.view.presenter.main.myphotos;

import android.net.Uri;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.events.main.myphoto.UploadPhotoEvent;
import hu.boldizsartompe.photofeed.domain.interactor.main.photos.common.GetPhotosInteractor;
import hu.boldizsartompe.photofeed.domain.interactor.main.photos.myphoto.GetMyPhotosInteractorImpl;
import hu.boldizsartompe.photofeed.domain.interactor.main.photos.myphoto.UploadPhotoInteractor;
import hu.boldizsartompe.photofeed.domain.interactor.main.photos.myphoto.UploadPhotoInteractorImpl;
import hu.boldizsartompe.photofeed.presenter.rx.JobExecutor;
import hu.boldizsartompe.photofeed.presenter.view.presenter.BasePresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.comment.CommentActivity;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.likes.LikesActivity;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.myphotos.MyPhotosView;

public class MyPhotosPresenter extends BasePresenter<MyPhotosView> {

    private UploadPhotoInteractor uploadPhotoInteractor;

    private GetPhotosInteractor getMyPhotos;

    private List<Photo> myPhotos;

    public MyPhotosPresenter() {
        uploadPhotoInteractor = new UploadPhotoInteractorImpl();
        getMyPhotos = new GetMyPhotosInteractorImpl();
    }

    @Override
    public void attachView(MyPhotosView view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
        getMyPhotos.onResume();
    }

    @Override
    public void detachView() {
        getMyPhotos.onDestroy();
        EventBus.getDefault().unregister(this);
        super.detachView();
    }

    public void attachViewOnPhoto(MyPhotosView view){
        super.attachView(view);
    }

    public void uploadPhoto(final Uri uri){
        if(isViewNotNull()){
            mView.showPhotoUploadProgress();

            JobExecutor.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    uploadPhotoInteractor.uploadPhoto(uri);
                }
            });
        }
    }

    public void getMyPhotos(){
        getMyPhotos.getPhotos(new GetPhotosInteractor.GetPhotosCallback() {
            @Override
            public void onGetMyPhotos(List<Photo> photos) {
                if(isViewNotNull()){
                    myPhotos = photos;
                    mView.showPhotos(photos);
                }
            }

            @Override
            public void onError() {
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPhotoUploaded(UploadPhotoEvent event){
        if(isViewNotNull()){
            mView.hideLoading();

            if(event.getPhoto() != null){
                mView.showPhotoUploadComplete();
                mView.showNewPhoto(event.getPhoto());
            } else {
                mView.showPhotoUploadError();
            }
        }
    }

    public void viewComments(int position) {
        //TODO: TO BASE CLASS!
        if(isViewNotNull() && myPhotos!= null){
            Bundle bundle = new Bundle();
            bundle.putString(CommentActivity.EXTRA_PHOTOID, myPhotos.get(position).getId());

            mView.onStartActivityWithBundle(CommentActivity.class, bundle);
        }
    }

    public void viewLikes(int position) {
        if(isViewNotNull() && myPhotos!= null){
            Bundle bundle = new Bundle();
            bundle.putString(LikesActivity.EXTRA_PHOTOID, myPhotos.get(position).getId());

            mView.onStartActivityWithBundle(LikesActivity.class, bundle);
        }
    }
}
