package hu.boldizsartompe.photofeed.domain.events.main.myphoto;


import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.events.BaseEvent;

public class UploadPhotoEvent extends BaseEvent {

    private Photo photo;

    public UploadPhotoEvent(Throwable throwable, Photo photo) {
        super(throwable);
        this.photo = photo;
    }

    public UploadPhotoEvent(Photo photo) {
        this.photo = photo;
    }

    public Photo getPhoto() {
        return photo;
    }
}
