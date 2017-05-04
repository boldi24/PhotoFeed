package hu.boldizsartompe.photofeed.domain.events.main.friends;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Friend;
import hu.boldizsartompe.photofeed.domain.events.BaseEvent;

public class GetFriendsEvent extends BaseEvent {

    private List<Friend> friends;

    public GetFriendsEvent(Throwable throwable) {
        super(throwable);
    }

    public GetFriendsEvent(List<Friend> friends) {
        this.friends = friends;
    }

    public List<Friend> getFriends() {
        return friends;
    }
}
