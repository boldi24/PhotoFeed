package hu.boldizsartompe.photofeed.domain.entity;


public class Friend {

    public static final String VERIFIED_FRIEND = "VERIFIED_FRIEND";
    public static final String FRIEND_REQUEST_FROM_ME = "FRIEND_REQUEST_FROM_ME";
    public static final String FRIEND_REQUEST_FROM_OTHER = "FRIEND_REQUEST_FROM_OTHER";

    private String state;
    private String username;

    public Friend(String username) {
        this.username = username;
    }

    public Friend(String state, String username) {
        this.state = state;
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }
}
