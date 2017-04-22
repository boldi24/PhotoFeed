package hu.boldizsartompe.photofeed.domain.repository;

import android.net.Uri;

import hu.boldizsartompe.photofeed.domain.entity.Photo;

public interface PhotoRepository {

    void uploadPhoto(Photo photo, Uri uri);

    void getPhotos();

}
