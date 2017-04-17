package hu.boldizsartompe.photofeed.domain.interactor.register;


import hu.boldizsartompe.photofeed.data.manager.AuthManager;
import hu.boldizsartompe.photofeed.data.manager.AuthManagerImpl;

public class RegistrationInteractorImpl implements RegistrationInteractor {

    private AuthManager authManager;

    public RegistrationInteractorImpl() {
        authManager = AuthManagerImpl.getInstance();
    }

    @Override
    public void registerUserWithUsernameAndPassword(String username, String password) {
        authManager.registerWithUsernamePassword(username, password);
    }
}
