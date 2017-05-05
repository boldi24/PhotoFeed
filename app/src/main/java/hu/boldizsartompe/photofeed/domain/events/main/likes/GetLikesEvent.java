package hu.boldizsartompe.photofeed.domain.events.main.likes;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.events.BaseEvent;

public class GetLikesEvent extends BaseEvent {

    private List<String> usernames;

    public GetLikesEvent(List<String> usernames) {
        this.usernames = usernames;
    }

    public GetLikesEvent(Throwable throwable) {
        super(throwable);
    }

    public List<String> getUsernames() {
        return usernames;
    }
}
