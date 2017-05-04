package hu.boldizsartompe.photofeed.presenter.view.screens.main.comment;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Comment;
import hu.boldizsartompe.photofeed.presenter.view.IView;

public interface CommentsView extends IView{

    void showComments(List<Comment> comments);

    void addComment(Comment comment);

    void showLoadingComments();

}
