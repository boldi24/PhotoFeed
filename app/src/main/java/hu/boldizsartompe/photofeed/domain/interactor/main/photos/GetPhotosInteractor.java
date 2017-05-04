package hu.boldizsartompe.photofeed.domain.interactor.main.photos;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Photo;

public interface GetPhotosInteractor {

    interface GetPhotosCallback {

        void onGetMyPhotos(List<Photo> photos);

        void onError();

    }

    void getPhotos(GetPhotosCallback callback);

    void onResume();

    void onDestroy();
}
