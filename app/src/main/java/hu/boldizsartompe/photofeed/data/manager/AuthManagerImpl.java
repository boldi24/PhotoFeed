package hu.boldizsartompe.photofeed.data.manager;


import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;

import hu.boldizsartompe.photofeed.domain.events.login.LoginEvent;
import hu.boldizsartompe.photofeed.domain.events.register.RegistrationEvent;

public class AuthManagerImpl implements AuthManager {

    public static final String TAG = "AUTH_MANAGER_IMPL";

    private FirebaseAuth mAuth;

    private final String customDomain = "@photofeed.hu";

    private static AuthManager instance;

    private AuthManagerImpl(){
        mAuth = FirebaseAuth.getInstance();
    }

    public static AuthManager getInstance(){
        if(instance ==  null) instance = new AuthManagerImpl();
        return instance;
    }

    @Override
    public void registerWithUsernamePassword(String username, String password) {
        mAuth.createUserWithEmailAndPassword(username+customDomain, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            EventBus.getDefault().post(new RegistrationEvent(true));
                        } else {
                            EventBus.getDefault().post(new RegistrationEvent(task.getException(), false));
                        }
                    }
                });
    }

    @Override
    public void loginWithUsernameAndPassword(String username, String password) {
        mAuth.signInWithEmailAndPassword(username+customDomain, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            EventBus.getDefault().post(new LoginEvent(true));
                        } else {
                            EventBus.getDefault().post(new LoginEvent(task.getException(), false));
                        }
                    }
                });
    }

    @Override
    public boolean isSignedIn() {
        return mAuth.getCurrentUser() != null;
    }

    @Override
    public void signOut() {
        mAuth.signOut();
    }

    @Override
    public String getUsername() {
        return mAuth.getCurrentUser().getEmail().split("@")[0];
    }

    @Override
    public String getUserId() {
        return mAuth.getCurrentUser().getUid();
    }
}
