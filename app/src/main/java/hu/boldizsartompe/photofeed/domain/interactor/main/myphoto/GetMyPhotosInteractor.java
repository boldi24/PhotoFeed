package hu.boldizsartompe.photofeed.domain.interactor.main.myphoto;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Photo;

public interface GetMyPhotosInteractor {

    interface GetMyPhotosCallback{

        void onGetMyPhotos(List<Photo> photos);

        void onError();

    }

    void getMyPhotos(GetMyPhotosCallback callback);

    void onResume();

    void onDestroy();
}
