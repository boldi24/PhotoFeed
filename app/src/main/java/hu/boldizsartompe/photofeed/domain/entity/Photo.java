package hu.boldizsartompe.photofeed.domain.entity;


import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.List;

public class Photo {

    private String id;
    private String downloadRef;
    private String senderUserName;
    private String date;
    private boolean doILikeIt;
    private List<String> whoLikedThPhoto;

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

    @Exclude
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

    @Exclude
    public List<String> getWhoLikedThPhoto() {
        return whoLikedThPhoto;
    }

    public void setWhoLikedThPhoto(List<String> whoLikedThPhoto) {
        this.whoLikedThPhoto = whoLikedThPhoto;
    }

    public boolean doesUserLikePhoto(String uId){
        if(whoLikedThPhoto == null) return false;
        for(String whoLike : whoLikedThPhoto){
            if(uId.equals(whoLike)) return true;
        }
        return false;
    }
}
