package hu.boldizsartompe.photofeed.data.manager;

public interface AuthManager {

    void registerWithUsernamePassword(String username, String password);

    void loginWithUsernameAndPassword(String username, String password);

    boolean isSignedIn();

    void signOut();

}
