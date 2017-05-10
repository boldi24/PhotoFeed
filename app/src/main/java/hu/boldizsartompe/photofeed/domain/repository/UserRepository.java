package hu.boldizsartompe.photofeed.domain.repository;


public interface UserRepository {

    void doesUserExist(String username);

    void registerUser(String username);

    void requestAddFriend(String usernameFriend);

    void acceptFriend(String usernameFriend);

    void deleteFriend(String username);

    void getMyFriends(String username);

}
