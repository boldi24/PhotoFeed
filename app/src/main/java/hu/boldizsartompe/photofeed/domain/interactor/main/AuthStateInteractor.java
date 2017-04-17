package hu.boldizsartompe.photofeed.domain.interactor.main;

public interface AuthStateInteractor {

    boolean isSignedIn();

    void signOut();

}
