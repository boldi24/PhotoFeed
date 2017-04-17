package hu.boldizsartompe.photofeed.presenter.view;

import android.os.Bundle;

public interface IView {

    void showLoading(String message);

    void hideLoading();

    void onStartActivityWithBundle(Class<?> cls, Bundle bundle);

    void onStartActivityWithoutBundle(Class<?> cls);

    int getContentView();

}
