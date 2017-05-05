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

import hu.boldizsartompe.photofeed.domain.entity.Comment;
import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.events.main.GetPhotosEvent;
import hu.boldizsartompe.photofeed.domain.events.main.comments.GetComments;
import hu.boldizsartompe.photofeed.domain.events.main.likes.GetLikesEvent;
import hu.boldizsartompe.photofeed.domain.events.main.myphoto.UploadPhotoEvent;
import hu.boldizsartompe.photofeed.domain.repository.DatabaseNames;
import hu.boldizsartompe.photofeed.domain.repository.PhotoRepository;


public class FirebasePhotoRepository implements PhotoRepository {

    private final String TAG = "FirebasePhotoRepository";

    private static PhotoRepository instance;

    private StorageReference storageRefToPhotos;
    private DatabaseReference dbReferenceToPhotos;
    private DatabaseReference dbReferenceToLikes;
    private DatabaseReference dbReferenceToComments;

    public static PhotoRepository getInstance(){
        if(instance == null) instance = new FirebasePhotoRepository();
        return instance;
    }

    private FirebasePhotoRepository() {
        storageRefToPhotos = FirebaseStorage.getInstance().getReference().child(DatabaseNames.PHOTOS);
        dbReferenceToPhotos = FirebaseDatabase.getInstance().getReference().child(DatabaseNames.PHOTOS);
        dbReferenceToPhotos.keepSynced(true);
        dbReferenceToLikes = FirebaseDatabase.getInstance().getReference().child(DatabaseNames.LIKES);
        dbReferenceToComments = FirebaseDatabase.getInstance().getReference().child(DatabaseNames.COMMENTS);
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
                photo.setId(newPhotosRef.getKey());
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

                final List<Photo> photos = new ArrayList<>();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    Photo photo = snap.getValue(Photo.class);
                    photos.add(photo);
                }

                dbReferenceToLikes.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot photoLikeSnap : dataSnapshot.getChildren()){
                            String photoId = photoLikeSnap.getKey();
                            Photo photo = getPhotoWithId(photos, photoId);
                            if(photo != null) {
                                List<String> usersWhoLiked = new ArrayList<>();
                                for (DataSnapshot likeSnap : photoLikeSnap.getChildren()) {
                                    usersWhoLiked.add(likeSnap.getKey());
                                }
                                photo.setWhoLikedThPhoto(usersWhoLiked);
                            }
                        }

                        EventBus.getDefault().post(new GetPhotosEvent(photos));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        EventBus.getDefault().post(new GetPhotosEvent(databaseError.toException()));
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                EventBus.getDefault().post(new GetPhotosEvent(databaseError.toException()));
            }
        });
    }

    private Photo getPhotoWithId(List<Photo> photos, String id){
        for (Photo photo : photos){
            if(photo.getId().equals(id)) return photo;
        }
        return null;
    }

    @Override
    public void likePhoto(Photo photo, String username) {
        if(photo.isDoILikeIt()) dbReferenceToLikes.child(photo.getId()).child(username).setValue(photo.isDoILikeIt());
        else dbReferenceToLikes.child(photo.getId()).child(username).setValue(null);
    }

    @Override
    public void commentPhoto(String photoId, Comment comment) {
        dbReferenceToComments.child(photoId).push().setValue(comment);
    }

    @Override
    public void getCommentsOfPhoto(String id) {
        dbReferenceToComments.child(id).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Comment> comments = new ArrayList<>();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    comments.add(snap.getValue(Comment.class));
                }

                EventBus.getDefault().post(new GetComments(comments));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                EventBus.getDefault().post(new GetComments(databaseError.toException()));
            }
        });
    }

    @Override
    public void getLikesOfPhoto(String id) {
        dbReferenceToLikes.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<String> usernames = new ArrayList<>();

                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    usernames.add(snap.getKey());
                }

                EventBus.getDefault().post(new GetLikesEvent(usernames));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                EventBus.getDefault().post(new GetLikesEvent(databaseError.toException()));
            }
        });
    }
}
