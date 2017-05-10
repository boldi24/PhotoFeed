package hu.boldizsartompe.photofeed.domain.interactor.main.friend;

import hu.boldizsartompe.photofeed.data.manager.AuthManager;
import hu.boldizsartompe.photofeed.data.manager.AuthManagerImpl;
import hu.boldizsartompe.photofeed.data.repository.FirebaseUserRepository;
import hu.boldizsartompe.photofeed.domain.entity.Friend;
import hu.boldizsartompe.photofeed.domain.repository.UserRepository;

public class FriendsInteractorImpl implements FriendsInteractor {

    private UserRepository userRepository;

    private AuthManager authManager;

    public FriendsInteractorImpl() {
        userRepository = FirebaseUserRepository.getInstance();
        authManager = AuthManagerImpl.getInstance();
    }

    @Override
    public void findFriend(String username, friendsInteractorCallback callback) {
        if(username.equals(authManager.getUsername())) {
            callback.onLoggedInUsersName();
            return;
        }
        userRepository.doesUserExist(username);
    }

    @Override
    public void addFriend(String friendUsername) {

        userRepository.requestAddFriend(friendUsername);
    }

    @Override
    public void acceptFriend(String friendUsername) {
        userRepository.acceptFriend(friendUsername);
    }

    @Override
    public void getMyFriends() {
        userRepository.getMyFriends(authManager.getUsername());
    }

    @Override
    public void deleteFriend(String username) {
        userRepository.deleteFriend(username);
    }
}
