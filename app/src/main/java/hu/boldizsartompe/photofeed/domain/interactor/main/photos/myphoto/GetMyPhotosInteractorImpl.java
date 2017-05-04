package hu.boldizsartompe.photofeed.domain.interactor.main.photos.myphoto;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.events.main.GetPhotosEvent;
import hu.boldizsartompe.photofeed.domain.interactor.main.photos.common.GetPhotosBaseInteractor;

public class GetMyPhotosInteractorImpl extends GetPhotosBaseInteractor {

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetPhotos(GetPhotosEvent event){
        if (callback == null) return;

        String username = authManager.getUsername();

        List<Photo> photos = event.getPhotos();

        List<Photo> myPhotos = new ArrayList<>();
        if(photos != null){
            for(Photo photo : photos){
                if(photo.getSenderUserName().equals(username)) {
                    myPhotos.add(photo);
                }
            }

            callback.onGetMyPhotos(myPhotos);
        } else if(event.getThrowable() != null){
            callback.onError();
        }
    }

}
