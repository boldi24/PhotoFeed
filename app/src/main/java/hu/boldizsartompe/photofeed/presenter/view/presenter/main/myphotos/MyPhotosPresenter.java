package hu.boldizsartompe.photofeed.presenter.view.presenter.main.myphotos;

import android.net.Uri;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import hu.boldizsartompe.photofeed.domain.events.main.myphoto.UploadPhotoEvent;
import hu.boldizsartompe.photofeed.domain.interactor.main.myphoto.UploadPhotoInteractor;
import hu.boldizsartompe.photofeed.domain.interactor.main.myphoto.UploadPhotoInteractorImpl;
import hu.boldizsartompe.photofeed.presenter.rx.JobExecutor;
import hu.boldizsartompe.photofeed.presenter.view.presenter.BasePresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.myphotos.MyPhotosView;

public class MyPhotosPresenter extends BasePresenter<MyPhotosView> {

    private UploadPhotoInteractor uploadPhotoInteractor;

    public MyPhotosPresenter() {
        uploadPhotoInteractor = new UploadPhotoInteractorImpl();
    }

    @Override
    public void attachView(MyPhotosView view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView() {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPhotoUploaded(UploadPhotoEvent event){
        if(isViewNotNull()){
            mView.hideLoading();


        }
    }

}
