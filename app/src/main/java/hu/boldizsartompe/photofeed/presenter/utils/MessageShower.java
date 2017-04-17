package hu.boldizsartompe.photofeed.presenter.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

public class MessageShower {

    public static void showSnackbarToastOnContextWithMessage(View view, String message){
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }
}
