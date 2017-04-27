package hu.boldizsartompe.photofeed.domain.events.main;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.events.BaseEvent;

public class GetPhotosEvent extends BaseEvent{

    private List<Photo> photos;

    public GetPhotosEvent(Throwable throwable) {
        super(throwable);
    }

    public GetPhotosEvent(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Photo> getPhotos() {
        return photos;
    }
}
