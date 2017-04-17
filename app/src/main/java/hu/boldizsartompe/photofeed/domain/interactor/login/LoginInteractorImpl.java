package hu.boldizsartompe.photofeed.domain.interactor.login;

import hu.boldizsartompe.photofeed.data.manager.AuthManager;
import hu.boldizsartompe.photofeed.data.manager.AuthManagerImpl;

public class LoginInteractorImpl implements LoginInteractor {

    private AuthManager authManager;

    public LoginInteractorImpl() {
        authManager = AuthManagerImpl.getInstance();
    }

    @Override
    public void loginWithUsernameAndPassword(String username, String password) {
        authManager.loginWithUsernameAndPassword(username, password);
    }
}
