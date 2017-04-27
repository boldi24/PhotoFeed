package hu.boldizsartompe.photofeed.domain.interactor.main.myphoto;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import hu.boldizsartompe.photofeed.data.manager.AuthManager;
import hu.boldizsartompe.photofeed.data.manager.AuthManagerImpl;
import hu.boldizsartompe.photofeed.data.repository.FirebasePhotoRepository;
import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.events.main.GetPhotosEvent;
import hu.boldizsartompe.photofeed.domain.repository.PhotoRepository;

public class GetMyPhotosInteractorImpl implements GetMyPhotosInteractor {

    private PhotoRepository photoRepository;
    private AuthManager authManager;

    private GetMyPhotosCallback callback;

    public GetMyPhotosInteractorImpl() {
        photoRepository = FirebasePhotoRepository.getInstance();
        authManager = AuthManagerImpl.getInstance();
    }

    @Override
    public void getMyPhotos(GetMyPhotosCallback callback) {
        this.callback = callback;
        photoRepository.getPhotos();
    }

    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetPhotos(GetPhotosEvent event){
        String username = authManager.getUsername();

        List<Photo> photos = event.getPhotos();
        if(photos != null){
            for(Photo photo : photos){
                if(!photo.getSenderUserName().equals(username)) photos.remove(photo);
            }

            callback.onGetMyPhotos(photos);
        } else if(event.getThrowable() != null){
            callback.onError();
        }

    }

    @Override
    public void onDestroy(){
        EventBus.getDefault().unregister(this);
    }
}
