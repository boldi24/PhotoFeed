package hu.boldizsartompe.photofeed.domain.interactor.main.myphoto;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;

import hu.boldizsartompe.photofeed.data.manager.AuthManagerImpl;
import hu.boldizsartompe.photofeed.data.repository.FirebasePhotoRepository;
import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.repository.PhotoRepository;

public class UploadPhotoInteractorImpl implements UploadPhotoInteractor {

    private PhotoRepository photoRepository;

    public UploadPhotoInteractorImpl() {
        photoRepository = FirebasePhotoRepository.getInstance();
    }

    @Override
    public void uploadPhoto(Uri uri) {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd 'at' hh:mm aa");
        Photo photo = new Photo(AuthManagerImpl.getInstance().getUsername(), ft.format(date));

        photoRepository.uploadPhoto(photo, uri);
    }

}
