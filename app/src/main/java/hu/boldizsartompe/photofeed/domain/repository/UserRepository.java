package hu.boldizsartompe.photofeed.domain.repository;


public interface UserRepository {

    void doesUserExist(String username);

    void registerUser(String username);

    void requestAddFriend(String usernameMe, String usernameFriend);

    void acceptFriend(String usernameMe, String usernameFriend);

    void deleteFriend(String username);

    void getMyFriends(String username);

}
