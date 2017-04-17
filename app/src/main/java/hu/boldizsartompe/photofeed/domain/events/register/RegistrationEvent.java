package hu.boldizsartompe.photofeed.domain.events.register;

import hu.boldizsartompe.photofeed.domain.events.BaseEvent;

public class RegistrationEvent extends BaseEvent {

    private boolean isSuccessful;

    public RegistrationEvent(Throwable throwable, boolean isSuccessful) {
        super(throwable);
        this.isSuccessful = isSuccessful;
    }

    public RegistrationEvent(boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}
