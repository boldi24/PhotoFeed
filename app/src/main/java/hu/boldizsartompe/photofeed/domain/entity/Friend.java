package hu.boldizsartompe.photofeed.domain.entity;


public class Friend {

    private boolean isVerified;
    private String username;

    public Friend(boolean isVerified, String username) {
        this.isVerified = isVerified;
        this.username = username;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public String getUsername() {
        return username;
    }
}
