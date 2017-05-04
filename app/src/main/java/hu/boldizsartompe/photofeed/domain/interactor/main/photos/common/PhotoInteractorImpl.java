package hu.boldizsartompe.photofeed.domain.interactor.main.photos.common;

import hu.boldizsartompe.photofeed.data.manager.AuthManager;
import hu.boldizsartompe.photofeed.data.manager.AuthManagerImpl;
import hu.boldizsartompe.photofeed.data.repository.FirebasePhotoRepository;
import hu.boldizsartompe.photofeed.domain.entity.Comment;
import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.repository.PhotoRepository;
import hu.boldizsartompe.photofeed.domain.util.DateManager;

public class PhotoInteractorImpl implements PhotoInteractor {

    private PhotoRepository photoRepository;
    private AuthManager authManager;

    public PhotoInteractorImpl() {
        photoRepository = FirebasePhotoRepository.getInstance();
        authManager = AuthManagerImpl.getInstance();
    }

    @Override
    public void likePhoto(Photo photo) {
        photoRepository.likePhoto(photo, authManager.getUsername());
    }
}
