package hu.boldizsartompe.photofeed.domain.entity;

import com.google.firebase.database.Exclude;

public class Comment {

    private String text;
    private String username;
    private String date;
    private boolean isMe;

    public Comment() {
    }

    public Comment(String text, String username, String date) {
        this.text = text;
        this.username = username;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Exclude
    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }
}
