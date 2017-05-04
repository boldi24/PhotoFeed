package hu.boldizsartompe.photofeed.presenter.view.screens.main.photos;


import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.presenter.view.IView;

public interface PhotosView extends IView {

    void showPhotos(List<Photo> photos);

}
