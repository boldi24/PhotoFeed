package hu.boldizsartompe.photofeed.domain.events.main.comments;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Comment;
import hu.boldizsartompe.photofeed.domain.events.BaseEvent;

public class GetComments extends BaseEvent {

    private List<Comment> comments;

    public GetComments(List<Comment> comments) {
        this.comments = comments;
    }

    public GetComments(Throwable throwable) {
        super(throwable);
    }

    public List<Comment> getComments() {
        return comments;
    }

}
