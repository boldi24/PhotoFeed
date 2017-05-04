package hu.boldizsartompe.photofeed.presenter.view.presenter.main.comments;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Comment;
import hu.boldizsartompe.photofeed.domain.interactor.main.comments.CommentInteractor;
import hu.boldizsartompe.photofeed.domain.interactor.main.comments.CommentsInteractorImpl;
import hu.boldizsartompe.photofeed.presenter.rx.JobExecutor;
import hu.boldizsartompe.photofeed.presenter.view.presenter.BasePresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.comment.CommentsView;

public class CommentPresenter extends BasePresenter<CommentsView> {

    private CommentInteractor commentInteractor;

    private String photoId;

    public CommentPresenter(String photoId) {
        this.photoId = photoId;
        commentInteractor = new CommentsInteractorImpl();
    }

    @Override
    public void attachView(CommentsView view) {
        super.attachView(view);
        commentInteractor.onResume();
    }

    @Override
    public void detachView() {
        commentInteractor.onDestroy();
        super.detachView();
    }

    public void sendComment(final String text){
        commentInteractor.commentPhoto(photoId, text, new CommentInteractor.OnCommentAddedCallback() {
            @Override
            public void onCommentAdded(Comment comment) {
                if(isViewNotNull()){
                    mView.addComment(comment);
                }
            }
        });
    }

    public void getComments(){
        if(isViewNotNull()) mView.showLoadingComments();

        commentInteractor.getCommentOfPhoto(photoId, new CommentInteractor.OnGetCommentsCallback() {
            @Override
            public void onGetComments(List<Comment> comments) {
                if(isViewNotNull()){
                    mView.hideLoading();

                    mView.showComments(comments);
                }
            }
        });
    }
}
