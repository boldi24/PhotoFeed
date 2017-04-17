package hu.boldizsartompe.photofeed.presenter.view.presenter;


public interface IPresenter<V> {

    void attachView(V view);

    void detachView();
}