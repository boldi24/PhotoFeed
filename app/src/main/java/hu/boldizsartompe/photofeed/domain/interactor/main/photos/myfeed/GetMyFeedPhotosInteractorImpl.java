package hu.boldizsartompe.photofeed.domain.interactor.main.photos.myfeed;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import hu.boldizsartompe.photofeed.data.repository.FirebaseUserRepository;
import hu.boldizsartompe.photofeed.domain.entity.Friend;
import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.domain.events.main.GetPhotosEvent;
import hu.boldizsartompe.photofeed.domain.events.main.friends.GetFriendsEvent;
import hu.boldizsartompe.photofeed.domain.interactor.main.photos.GetPhotosBaseInteractor;
import hu.boldizsartompe.photofeed.domain.repository.UserRepository;

public class GetMyFeedPhotosInteractorImpl extends GetPhotosBaseInteractor {

    private UserRepository userRepository;

    private List<Friend> myFriends;

    public GetMyFeedPhotosInteractorImpl() {
        super();
        userRepository = FirebaseUserRepository.getInstance();
    }

    @Override
    public void getPhotos(GetPhotosCallback callback) {
        this.callback = callback;

        userRepository.getMyFriends(authManager.getUsername());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onGetFriends(GetFriendsEvent event){
        if(event.getFriends() != null) myFriends = event.getFriends();
        photoRepository.getPhotos();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetPhotos(GetPhotosEvent event){
        if (callback == null || myFriends == null) return;

        String myUsername = authManager.getUsername();

        List<Photo> photos = event.getPhotos();

        List<Photo> friendsPhotos = new ArrayList<>();
        if(photos != null){
            for(Photo photo : photos){
                if(isMyFriend(photo.getSenderUserName())) {
                    friendsPhotos.add(photo);
                }
            }

            callback.onGetMyPhotos(friendsPhotos);
        } else if(event.getThrowable() != null){
            callback.onError();
        }
    }

    private boolean isMyFriend(String username){
        for(Friend friend : myFriends){
            if(friend.getUsername().equals(username)) return true;
        }
        return false;
    }

}
