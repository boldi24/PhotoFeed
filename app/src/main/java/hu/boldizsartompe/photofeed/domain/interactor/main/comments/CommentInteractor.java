package hu.boldizsartompe.photofeed.domain.interactor.main.comments;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Comment;
import hu.boldizsartompe.photofeed.domain.entity.Photo;

public interface CommentInteractor {

    interface OnGetCommentsCallback{

        void onGetComments(List<Comment> comments);
    }

    interface OnCommentAddedCallback {

        void onCommentAdded(Comment comment);

    }

    void commentPhoto(String photoId, String comment, OnCommentAddedCallback callback);

    void getCommentOfPhoto(String photoId, OnGetCommentsCallback callback);

    void onResume();

    void onDestroy();

}
