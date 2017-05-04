package hu.boldizsartompe.photofeed.domain.interactor.main.photos.common;

import hu.boldizsartompe.photofeed.domain.entity.Comment;
import hu.boldizsartompe.photofeed.domain.entity.Photo;

public interface PhotoInteractor {

    void likePhoto(Photo photo);

}
