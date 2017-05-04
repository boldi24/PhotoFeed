package hu.boldizsartompe.photofeed;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * TODO: Add a class header comment!
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
