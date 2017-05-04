package hu.boldizsartompe.photofeed.domain.interactor.main.photos;

import org.greenrobot.eventbus.EventBus;

import hu.boldizsartompe.photofeed.data.manager.AuthManager;
import hu.boldizsartompe.photofeed.data.manager.AuthManagerImpl;
import hu.boldizsartompe.photofeed.data.repository.FirebasePhotoRepository;
import hu.boldizsartompe.photofeed.domain.repository.PhotoRepository;

public abstract class GetPhotosBaseInteractor implements GetPhotosInteractor {

    protected PhotoRepository photoRepository;
    protected AuthManager authManager;

    protected GetPhotosCallback callback;

    public GetPhotosBaseInteractor() {
        photoRepository = FirebasePhotoRepository.getInstance();
        authManager = AuthManagerImpl.getInstance();
    }

    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void getPhotos(GetPhotosCallback callback) {
        this.callback = callback;
        photoRepository.getPhotos();
    }
}
