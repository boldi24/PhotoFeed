package hu.boldizsartompe.photofeed.presenter.view.screens.main.friends;

import java.util.List;
import java.util.Map;

import hu.boldizsartompe.photofeed.domain.entity.Friend;
import hu.boldizsartompe.photofeed.presenter.view.IView;

/**
 * TODO: Add a class header comment!
 */

public interface FriendView extends IView {

    void showSearchingUsername();

    void showUserNotFound();

    void showTypeFriendUsername();

    void showCantAddYourself();

    void showFriends(List<Friend> friends);

    void showFriend(Friend friend);

    void showAddFriend(String username);
}
