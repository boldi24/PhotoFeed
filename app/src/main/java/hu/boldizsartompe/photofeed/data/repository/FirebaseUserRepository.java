package hu.boldizsartompe.photofeed.data.repository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import hu.boldizsartompe.photofeed.data.manager.AuthManagerImpl;
import hu.boldizsartompe.photofeed.domain.entity.Friend;
import hu.boldizsartompe.photofeed.domain.events.main.friends.GetFriendsEvent;
import hu.boldizsartompe.photofeed.domain.events.main.friends.UserExistsEvent;
import hu.boldizsartompe.photofeed.domain.repository.DatabaseNames;
import hu.boldizsartompe.photofeed.domain.repository.UserRepository;

public class FirebaseUserRepository implements UserRepository {

    private DatabaseReference dbRefToFriends;
    private DatabaseReference dbRefToUsers;

    private static UserRepository instance;

    public static UserRepository getInstance(){
        if(instance == null) instance = new FirebaseUserRepository();
        return instance;
    }

    private FirebaseUserRepository() {
        dbRefToFriends = FirebaseDatabase.getInstance().getReference().child(DatabaseNames.FRIENDS);
        dbRefToUsers = FirebaseDatabase.getInstance().getReference().child(DatabaseNames.USERS);
    }

    @Override
    public void doesUserExist(final String username) {
        dbRefToUsers.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EventBus.getDefault().post(new UserExistsEvent(dataSnapshot.exists(), username));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                EventBus.getDefault().post(new UserExistsEvent(databaseError.toException()));
            }
        });
    }

    @Override
    public void registerUser(String username) {
        dbRefToUsers.child(username).setValue(username);
    }

    @Override
    public void requestAddFriend(String usernameFriend) {
        String usernameMe = AuthManagerImpl.getInstance().getUsername();
        //My node
        dbRefToFriends.child(usernameMe).child(usernameFriend).setValue(Friend.FRIEND_REQUEST_FROM_ME);

        //Friend node
        dbRefToFriends.child(usernameFriend).child(usernameMe).setValue(Friend.FRIEND_REQUEST_FROM_OTHER);
    }

    @Override
    public void acceptFriend(String usernameFriend) {
        String usernameMe = AuthManagerImpl.getInstance().getUsername();
        //My node
        dbRefToFriends.child(usernameMe).child(usernameFriend).setValue(Friend.VERIFIED_FRIEND);

        //Friend node
        dbRefToFriends.child(usernameFriend).child(usernameMe).setValue(Friend.VERIFIED_FRIEND);
    }


    @Override
    public void deleteFriend(String username) {
        String usernameMe = AuthManagerImpl.getInstance().getUsername();

        //My node
        dbRefToFriends.child(usernameMe).child(username).setValue(null);

        //Friend node
        dbRefToFriends.child(username).child(usernameMe).setValue(null);

    }

    @Override
    public void getMyFriends(String username) {
        dbRefToFriends.child(username).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List<Friend> friends = new ArrayList<>();

                for(DataSnapshot snap : dataSnapshot.getChildren()){

                    String username = snap.getKey();
                    String state = snap.getValue(String.class);

                    friends.add( new Friend(state, username));
                }

                EventBus.getDefault().post(new GetFriendsEvent(friends));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                EventBus.getDefault().post(new GetFriendsEvent(databaseError.toException()));
            }
        });
    }
}
