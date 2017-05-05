package hu.boldizsartompe.photofeed.domain.interactor.register;


import hu.boldizsartompe.photofeed.data.manager.AuthManager;
import hu.boldizsartompe.photofeed.data.manager.AuthManagerImpl;
import hu.boldizsartompe.photofeed.data.repository.FirebaseUserRepository;
import hu.boldizsartompe.photofeed.domain.repository.UserRepository;

public class RegistrationInteractorImpl implements RegistrationInteractor {

    private AuthManager authManager;
    private UserRepository userRepository;

    public RegistrationInteractorImpl() {
        authManager = AuthManagerImpl.getInstance();
        userRepository = FirebaseUserRepository.getInstance();
    }

    @Override
    public void registerUserWithUsernameAndPassword(String username, String password) {
        userRepository.registerUser(username);
        authManager.registerWithUsernamePassword(username, password);
    }
}
