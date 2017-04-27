package hu.boldizsartompe.photofeed.presenter.view.screens.main.myphotos;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.presenter.view.IView;

public interface MyPhotosView extends IView {

    void showPhotoUploadProgress();

    void showPhotoUploadComplete();

    void showPhotoUploadError();

    void showNewPhoto(Photo photo);

    void showNewPhotos(List<Photo> photos);

}
