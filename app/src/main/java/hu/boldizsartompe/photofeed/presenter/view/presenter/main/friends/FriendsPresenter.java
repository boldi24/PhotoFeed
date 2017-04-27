package hu.boldizsartompe.photofeed.presenter.view.presenter.main.friends;

import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

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
                    friendsInteractor.findFriend(username);
                }
            });
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFriendExists(UserExistsEvent event){
        if(isViewNotNull()){
            mView.hideLoading();

            if(event.doesExist()){
                mView.showFriend(event.getFriend());
            } else {
                mView.showUserNotFound();
            }

//            if(event.getThrowable() !=  null){
//                //ERROR
//            }
        }
    }
}
