package hu.boldizsartompe.photofeed.domain.entity;


import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.List;

public class Photo {

    private String id;
    private String downloadRef;
    private String senderUserName;
    private String date;
    @Exclude
    private boolean doILikeIt;

    public Photo() {
    }

    public Photo(String senderUserName, String date) {
        this.senderUserName = senderUserName;
        this.date = date;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDownloadRef() {
        return downloadRef;
    }

    public void setDownloadRef(String downloadRef) {
        this.downloadRef = downloadRef;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public boolean isDoILikeIt() {
        return doILikeIt;
    }

    public void setDoILikeIt(boolean doILikeIt) {
        this.doILikeIt = doILikeIt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
