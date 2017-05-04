package hu.boldizsartompe.photofeed.domain.interactor.main.friend;


public interface FriendsInteractor {

    interface friendsInteractorCallback{
        void onLoggedInUsersName();
    }

    void findFriend(String username, friendsInteractorCallback callback);

    void addFriend(String friendUsername);

    void acceptFriend(String username);

    void getMyFriends();

}
