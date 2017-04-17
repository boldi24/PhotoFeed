package hu.boldizsartompe.photofeed.domain.events;

public abstract class BaseEvent {

    private Throwable throwable;

    public BaseEvent(Throwable throwable) {
        this.throwable = throwable;
    }

    public BaseEvent(){}

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
