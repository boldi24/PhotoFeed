package hu.boldizsartompe.photofeed.presenter.view.presenter.main.myfeed;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.interactor.main.photos.GetPhotosInteractor;
import hu.boldizsartompe.photofeed.domain.interactor.main.photos.myfeed.GetMyFeedPhotosInteractorImpl;
import hu.boldizsartompe.photofeed.presenter.rx.JobExecutor;
import hu.boldizsartompe.photofeed.presenter.view.presenter.BasePresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.myfeed.MyFeedView;

public class MyFeedPresenter extends BasePresenter<MyFeedView> {

    private GetPhotosInteractor getMyFeedPhotosInteractor;

    public MyFeedPresenter() {
        getMyFeedPhotosInteractor = new GetMyFeedPhotosInteractorImpl();
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
                            mView.showPhotos(photos);
                        }
                    }

                    @Override
                    public void onError() {
                        //TODO
                    }
                });
            }
        });


    }

}
