package hu.boldizsartompe.photofeed.domain.repository;


public interface UserRepository {

    void doesUserExist(String username);

    void addFriend(String username);

    void verifyFriend(String username);

    void deleteFriend(String username);

}
