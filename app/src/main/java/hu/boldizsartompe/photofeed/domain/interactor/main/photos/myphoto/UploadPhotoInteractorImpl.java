package hu.boldizsartompe.photofeed.domain.interactor.main.photos.myphoto;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;

import hu.boldizsartompe.photofeed.data.manager.AuthManagerImpl;
import hu.boldizsartompe.photofeed.data.repository.FirebasePhotoRepository;
import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.repository.PhotoRepository;
import hu.boldizsartompe.photofeed.domain.util.DateManager;

public class UploadPhotoInteractorImpl implements UploadPhotoInteractor {

    private PhotoRepository photoRepository;

    public UploadPhotoInteractorImpl() {
        photoRepository = FirebasePhotoRepository.getInstance();
    }

    @Override
    public void uploadPhoto(Uri uri) {
        Photo photo = new Photo(AuthManagerImpl.getInstance().getUsername(), DateManager.getCurrentDate());

        photoRepository.uploadPhoto(photo, uri);
    }

}
