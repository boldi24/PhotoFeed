package hu.boldizsartompe.photofeed.presenter.view.screens.main.photos;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import hu.boldizsartompe.photofeed.presenter.view.BaseActivity;

public abstract class BasePhotosFragment extends Fragment implements PhotosView {

    @Override
    public void showLoading(String message) {
        ((BaseActivity)getActivity()).showLoading(message);
    }

    @Override
    public void hideLoading() {
        ((BaseActivity)getActivity()).hideLoading();
    }

    @Override
    public void onStartActivityWithBundle(Class<?> cls, Bundle bundle) {

    }

    @Override
    public void onStartActivityWithoutBundle(Class<?> cls) {

    }
}
