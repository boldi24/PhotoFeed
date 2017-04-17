package hu.boldizsartompe.photofeed.presenter.view.screens.login;

import hu.boldizsartompe.photofeed.presenter.view.IView;

public interface LoginView extends IView {

    void showTypeUsername();

    void showTypePassword();

    void showLoginProgress();

    void showErrorOnLogin();

}
