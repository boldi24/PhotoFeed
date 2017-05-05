package hu.boldizsartompe.photofeed.presenter.view.screens.main.likes;

import java.util.List;

import hu.boldizsartompe.photofeed.presenter.view.IView;

public interface LikesView extends IView{

    void showLikes(List<String> likes);

    void showDownloadingLikes();

}
