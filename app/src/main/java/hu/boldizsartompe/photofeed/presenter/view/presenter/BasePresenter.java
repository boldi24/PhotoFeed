package hu.boldizsartompe.photofeed.presenter.view.presenter;

import org.greenrobot.eventbus.EventBus;

public class BasePresenter<V> implements IPresenter<V> {

    protected V mView;

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    protected boolean isViewNotNull(){
        return mView != null;
    }
}
