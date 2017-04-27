package hu.boldizsartompe.photofeed.data.repository;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.events.main.GetPhotosEvent;
import hu.boldizsartompe.photofeed.domain.events.main.myphoto.UploadPhotoEvent;
import hu.boldizsartompe.photofeed.domain.repository.DatabaseNames;
import hu.boldizsartompe.photofeed.domain.repository.PhotoRepository;


public class FirebasePhotoRepository implements PhotoRepository {

    private final String TAG = "FirebasePhotoRepository";

    private static PhotoRepository instance;

    private StorageReference storageRefToPhotos;
    private DatabaseReference dbReferenceToPhotos;

    public static PhotoRepository getInstance(){
        if(instance == null) instance = new FirebasePhotoRepository();
        return instance;
    }

    private FirebasePhotoRepository() {
        storageRefToPhotos = FirebaseStorage.getInstance().getReference().child(DatabaseNames.PHOTOS);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        dbReferenceToPhotos = FirebaseDatabase.getInstance().getReference().child(DatabaseNames.PHOTOS);
    }

    @Override
    public void uploadPhoto(final Photo photo, Uri uri) {
        Log.d(TAG, "Uploading photo");

        storageRefToPhotos.child(photo.getSenderUserName() + photo.getDate())
                .putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                DatabaseReference newPhotosRef = dbReferenceToPhotos.push();
                photo.setDownloadRef(downloadUrl);
                newPhotosRef.setValue(photo);

                EventBus.getDefault().post(new UploadPhotoEvent(photo));
            }
        });
    }

    @Override
    public void getPhotos() {

        dbReferenceToPhotos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Photo> photos = new ArrayList<>();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    Photo photo = snap.getValue(Photo.class);
                    photos.add(photo);
                }

                EventBus.getDefault().post(new GetPhotosEvent(photos));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                EventBus.getDefault().post(new GetPhotosEvent(databaseError.toException()));
            }
        });

    }
}
