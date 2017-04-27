package hu.boldizsartompe.photofeed.presenter.view.presenter.main.myphotos;

import android.net.Uri;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.events.main.myphoto.UploadPhotoEvent;
import hu.boldizsartompe.photofeed.domain.interactor.main.myphoto.GetMyPhotosInteractor;
import hu.boldizsartompe.photofeed.domain.interactor.main.myphoto.GetMyPhotosInteractorImpl;
import hu.boldizsartompe.photofeed.domain.interactor.main.myphoto.UploadPhotoInteractor;
import hu.boldizsartompe.photofeed.domain.interactor.main.myphoto.UploadPhotoInteractorImpl;
import hu.boldizsartompe.photofeed.presenter.rx.JobExecutor;
import hu.boldizsartompe.photofeed.presenter.view.presenter.BasePresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.myphotos.MyPhotosView;

public class MyPhotosPresenter extends BasePresenter<MyPhotosView> {

    private UploadPhotoInteractor uploadPhotoInteractor;

    private GetMyPhotosInteractor getMyPhotos;

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
        if(isViewNotNull()) mView.showLoading("Képek letöltése");
        getMyPhotos.getMyPhotos(new GetMyPhotosInteractor.GetMyPhotosCallback() {
            @Override
            public void onGetMyPhotos(List<Photo> photos) {
                if(isViewNotNull()){
                    mView.hideLoading();
                    mView.showNewPhotos(photos);
                }
            }

            @Override
            public void onError() {
                if(isViewNotNull()){
                    mView.hideLoading();
                }
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

}
