package hu.boldizsartompe.photofeed.domain.events.main.friends;

import java.util.Map;

import hu.boldizsartompe.photofeed.domain.entity.Friend;
import hu.boldizsartompe.photofeed.domain.events.BaseEvent;


public class UserExistsEvent extends BaseEvent {

    private boolean doesExist;
    private String name;

    public UserExistsEvent(boolean doesExist, String name) {
        this.doesExist = doesExist;
        this.name = name;
    }

    public UserExistsEvent(Throwable throwable) {
        super(throwable);
    }

    public boolean doesExist() {
        return doesExist;
    }

    public String getName() {
        return name;
    }
}
