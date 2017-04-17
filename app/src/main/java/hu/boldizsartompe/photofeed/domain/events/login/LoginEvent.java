package hu.boldizsartompe.photofeed.domain.events.login;

import hu.boldizsartompe.photofeed.domain.events.BaseEvent;

public class LoginEvent extends BaseEvent {

    private boolean isSuccessful;

    public LoginEvent(Throwable throwable, boolean isSuccessful) {
        super(throwable);
        this.isSuccessful = isSuccessful;
    }

    public LoginEvent(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
