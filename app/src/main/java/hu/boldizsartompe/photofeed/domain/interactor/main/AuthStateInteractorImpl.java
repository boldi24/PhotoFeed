package hu.boldizsartompe.photofeed.domain.interactor.main;

import hu.boldizsartompe.photofeed.data.manager.AuthManager;
import hu.boldizsartompe.photofeed.data.manager.AuthManagerImpl;

public class AuthStateInteractorImpl implements AuthStateInteractor {

    private AuthManager authManager;

    public AuthStateInteractorImpl() {
        authManager = AuthManagerImpl.getInstance();
    }

    @Override
    public boolean isSignedIn() {
        return authManager.isSignedIn();
    }

    @Override
    public void signOut() {
        authManager.signOut();
    }


}
