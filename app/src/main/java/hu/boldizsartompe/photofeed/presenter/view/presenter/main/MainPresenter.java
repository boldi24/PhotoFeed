package hu.boldizsartompe.photofeed.presenter.view.presenter.main;

import hu.boldizsartompe.photofeed.domain.interactor.main.AuthStateInteractor;
import hu.boldizsartompe.photofeed.domain.interactor.main.AuthStateInteractorImpl;
import hu.boldizsartompe.photofeed.presenter.view.IView;
import hu.boldizsartompe.photofeed.presenter.view.presenter.BasePresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.login.LoginActivity;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.MainView;

public class MainPresenter extends BasePresenter<MainView> {

    private AuthStateInteractor authStateInteractor;

    public MainPresenter() {
        authStateInteractor = new AuthStateInteractorImpl();
    }

    public void checkIfUserSignedIn(){
        String username = authStateInteractor.getUsername();
        if(isViewNotNull()) {
            if (username == null) {
                mView.onStartActivityWithoutBundle(LoginActivity.class);
            } else {
                mView.setUpContent(username);
            }
        }
    }

    public void signOut(){
        authStateInteractor.signOut();
        if(isViewNotNull()) mView.onStartActivityWithoutBundle(LoginActivity.class);
    }

}
