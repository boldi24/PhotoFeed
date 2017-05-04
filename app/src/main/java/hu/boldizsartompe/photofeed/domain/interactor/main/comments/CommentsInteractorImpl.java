package hu.boldizsartompe.photofeed.domain.interactor.main.comments;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import hu.boldizsartompe.photofeed.data.manager.AuthManager;
import hu.boldizsartompe.photofeed.data.manager.AuthManagerImpl;
import hu.boldizsartompe.photofeed.data.repository.FirebasePhotoRepository;
import hu.boldizsartompe.photofeed.domain.entity.Comment;
import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.events.main.comments.GetComments;
import hu.boldizsartompe.photofeed.domain.repository.PhotoRepository;
import hu.boldizsartompe.photofeed.domain.util.DateManager;

public class CommentsInteractorImpl implements CommentInteractor {

    private AuthManager authManager;
    private PhotoRepository photoRepository;

    private  OnGetCommentsCallback callback;

    public CommentsInteractorImpl() {
        authManager = AuthManagerImpl.getInstance();
        photoRepository = FirebasePhotoRepository.getInstance();
    }

    @Override
    public void onResume() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void commentPhoto(String photoId, String text, OnCommentAddedCallback callback) {
        Comment comment = new Comment(text, authManager.getUsername(), DateManager.getCurrentDate());
        comment.setMe(true);

        photoRepository.commentPhoto(photoId, comment);
        callback.onCommentAdded(comment);
    }

    @Override
    public void getCommentOfPhoto(String photoId, OnGetCommentsCallback callback) {
        this.callback = callback;
        photoRepository.getCommentsOfPhoto(photoId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetPhotos(GetComments event){

        if(callback != null) {
            List<Comment> comments = event.getComments();

            if (comments != null) {
                String myUsername = authManager.getUsername();

                for (Comment comment : comments) {
                    if (comment.getUsername().equals(myUsername)) comment.setMe(true);
                }

                callback.onGetComments(comments);
            }
        }
    }
}
