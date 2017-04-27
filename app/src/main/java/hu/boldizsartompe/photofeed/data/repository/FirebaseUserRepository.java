package hu.boldizsartompe.photofeed.data.repository;

import com.google.android.gms.common.api.BooleanResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import hu.boldizsartompe.photofeed.domain.entity.Friend;
import hu.boldizsartompe.photofeed.domain.events.main.friends.UserExistsEvent;
import hu.boldizsartompe.photofeed.domain.repository.DatabaseNames;
import hu.boldizsartompe.photofeed.domain.repository.UserRepository;

public class FirebaseUserRepository implements UserRepository {

    private DatabaseReference dbRefToFriends;

    private static UserRepository instance;

    public static UserRepository getInstance(){
        if(instance == null) instance = new FirebaseUserRepository();
        return instance;
    }

    private FirebaseUserRepository() {
        dbRefToFriends = FirebaseDatabase.getInstance().getReference().child(DatabaseNames.FRIENDS);
    }

    @Override
    public void doesUserExist(final String username) {
        dbRefToFriends.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                EventBus.getDefault().post(new UserExistsEvent(dataSnapshot.exists(), new Friend(false, username)));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                EventBus.getDefault().post(new UserExistsEvent(databaseError.toException()));
            }
        });
    }


    //TODO: valami olyamsi kéen hogy három változat lehet émn jeletöltem be és visszaigazolt én
    //ejelöltem be és mgé ebn n sigazolt visszaq vagy ő jeletölt be engem de méág enm igazoltam vissza én
    @Override
    public void addFriend(String username) {

    }

    @Override
    public void verifyFriend(String username) {

    }

    @Override
    public void deleteFriend(String username) {

    }
}
