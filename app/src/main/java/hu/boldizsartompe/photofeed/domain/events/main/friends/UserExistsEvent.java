package hu.boldizsartompe.photofeed.domain.events.main.friends;

import java.util.Map;

import hu.boldizsartompe.photofeed.domain.entity.Friend;
import hu.boldizsartompe.photofeed.domain.events.BaseEvent;


public class UserExistsEvent extends BaseEvent {

    private boolean doesExist;
    private Friend friend;

    public UserExistsEvent(boolean doesExist, Friend friend) {
        this.doesExist = doesExist;
        this.friend = friend;
    }

    public UserExistsEvent(Throwable throwable) {
        super(throwable);
    }

    public boolean doesExist() {
        return doesExist;

    }

    public Friend getFriend() {
        return friend;
    }
}
