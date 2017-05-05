package hu.boldizsartompe.photofeed.presenter.view.presenter.likes;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import hu.boldizsartompe.photofeed.domain.events.main.likes.GetLikesEvent;
import hu.boldizsartompe.photofeed.domain.interactor.main.photos.common.LikeInteractor;
import hu.boldizsartompe.photofeed.domain.interactor.main.photos.common.LikeInteractorImpl;
import hu.boldizsartompe.photofeed.presenter.view.presenter.BasePresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.likes.LikesView;

public class LikesPresenter extends BasePresenter<LikesView> {

    private LikeInteractor likeInteractor;

    private String photoId;

    public LikesPresenter(String photoId) {
        this.photoId = photoId;
        likeInteractor = new LikeInteractorImpl();
    }

    @Override
    public void attachView(LikesView view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView() {
        EventBus.getDefault().unregister(this);
        super.detachView();
    }

    public void getLikes() {
        if(isViewNotNull()){
            mView.showDownloadingLikes();

            likeInteractor.getLikesOfPhoto(photoId);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetLikes(GetLikesEvent event){
        if(isViewNotNull()){
            mView.hideLoading();

            if(event.getUsernames() != null){
                mView.showLikes(event.getUsernames());
            }
        }
    }
}
