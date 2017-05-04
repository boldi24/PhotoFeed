package hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.myphotos;

import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.PhotosView;

public interface MyPhotosView extends PhotosView {

    void showPhotoUploadProgress();

    void showPhotoUploadComplete();

    void showPhotoUploadError();

    void showNewPhoto(Photo photo);

}
