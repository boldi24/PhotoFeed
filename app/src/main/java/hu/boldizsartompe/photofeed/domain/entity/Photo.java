package hu.boldizsartompe.photofeed.domain.entity;


import java.util.Date;
import java.util.List;

public class Photo {

    private String downloadRef;
    private String senderUserName;
    private List<Comment> comments;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Photo(String senderUserName, Date date) {
        this.senderUserName = senderUserName;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
