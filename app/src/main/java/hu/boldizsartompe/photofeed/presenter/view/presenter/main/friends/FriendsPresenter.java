package hu.boldizsartompe.photofeed.presenter.view.presenter.main.friends;

import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import hu.boldizsartompe.photofeed.domain.entity.Friend;
import hu.boldizsartompe.photofeed.domain.events.main.friends.GetFriendsEvent;
import hu.boldizsartompe.photofeed.domain.events.main.friends.UserExistsEvent;
import hu.boldizsartompe.photofeed.domain.interactor.main.friend.FriendsInteractor;
import hu.boldizsartompe.photofeed.domain.interactor.main.friend.FriendsInteractorImpl;
import hu.boldizsartompe.photofeed.presenter.rx.JobExecutor;
import hu.boldizsartompe.photofeed.presenter.view.presenter.BasePresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.friends.FriendView;


public class FriendsPresenter extends BasePresenter<FriendView> {

    private FriendsInteractor friendsInteractor;

    public FriendsPresenter() {
        friendsInteractor = new FriendsInteractorImpl();
    }

    List<Friend> friends;

    @Override
    public void attachView(FriendView view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView() {
        EventBus.getDefault().unregister(this);
        super.detachView();
    }

    public void findFriend(final String username){

        if(isViewNotNull()){

            if(TextUtils.isEmpty(username)){
                mView.showTypeFriendUsername();
                return;
            }

            mView.showSearchingUsername();

            JobExecutor.getInstance().execute(new Runnable() {
                @Override
                public void run() {

                    friendsInteractor.findFriend(username, new FriendsInteractor.friendsInteractorCallback() {
                        @Override
                        public void onLoggedInUsersName() {
                            if(isViewNotNull()){
                                mView.hideLoading();

                                mView.showCantAddYourself();
                            }
                        }
                    });
                }
            });
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFriendExists(UserExistsEvent event){
        if(isViewNotNull()){
            mView.hideLoading();

            if(event.doesExist()){
                mView.showAddFriend(event.getName());
            } else {
                mView.showUserNotFound();
            }

//            if(event.getThrowable() !=  null){
//                //ERROR
//            }
        }
    }


    public void addFriend(String username){
        friendsInteractor.addFriend(username);

        if(isViewNotNull()){
            mView.showFriend(new Friend(Friend.FRIEND_REQUEST_FROM_ME, username));
        }
    }


    public void getFriends(){
        if(isViewNotNull()){

            JobExecutor.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    friendsInteractor.getMyFriends();
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetFriends(GetFriendsEvent event){
        if(isViewNotNull()){

            if(event.getFriends() != null){
                friends = event.getFriends();
                mView.showFriends(friends);
            } else if(event.getThrowable() != null){
                //error
            }
        }
    }

    public void acceptFriend(String username) {
        friendsInteractor.acceptFriend(username);
    }

    public void deleteFriend(String username){
        friendsInteractor.deleteFriend(username);
    }
}
