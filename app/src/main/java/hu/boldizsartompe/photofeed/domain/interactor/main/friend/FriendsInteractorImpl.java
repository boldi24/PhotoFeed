package hu.boldizsartompe.photofeed.domain.interactor.main.friend;

import hu.boldizsartompe.photofeed.data.manager.AuthManager;
import hu.boldizsartompe.photofeed.data.manager.AuthManagerImpl;
import hu.boldizsartompe.photofeed.data.repository.FirebaseUserRepository;
import hu.boldizsartompe.photofeed.domain.repository.UserRepository;

public class FriendsInteractorImpl implements FriendsInteractor {

    private UserRepository userRepository;

    private AuthManager authManager;

    public FriendsInteractorImpl() {
        userRepository = FirebaseUserRepository.getInstance();
        authManager = AuthManagerImpl.getInstance();
    }

    @Override
    public void findFriend(String username) {
        if(username.equals(authManager.getUsername())) return;
        userRepository.doesUserExist(username);
    }
}
