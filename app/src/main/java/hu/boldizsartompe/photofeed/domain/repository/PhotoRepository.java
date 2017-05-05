package hu.boldizsartompe.photofeed.domain.repository;

import android.net.Uri;

import hu.boldizsartompe.photofeed.domain.entity.Comment;
import hu.boldizsartompe.photofeed.domain.entity.Photo;

public interface PhotoRepository {

    void uploadPhoto(Photo photo, Uri uri);

    void getPhotos();

    void likePhoto(Photo photo, String username);

    void commentPhoto(String  photoId, Comment comment);

    void getCommentsOfPhoto(String id);

    void getLikesOfPhoto(String id);

    void downloadPhoto(String id);

}
