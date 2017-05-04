package hu.boldizsartompe.photofeed.domain.interactor.main;

public interface AuthStateInteractor {

    boolean isSignedIn();

    String getUsername();

    void signOut();

}
