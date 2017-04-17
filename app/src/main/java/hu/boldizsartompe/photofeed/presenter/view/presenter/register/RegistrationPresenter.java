package hu.boldizsartompe.photofeed.presenter.view.presenter.register;

import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import hu.boldizsartompe.photofeed.domain.events.register.RegistrationEvent;
import hu.boldizsartompe.photofeed.domain.interactor.register.RegistrationInteractor;
import hu.boldizsartompe.photofeed.domain.interactor.register.RegistrationInteractorImpl;
import hu.boldizsartompe.photofeed.presenter.rx.JobExecutor;
import hu.boldizsartompe.photofeed.presenter.view.presenter.BasePresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.login.LoginActivity;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.MainActivity;
import hu.boldizsartompe.photofeed.presenter.view.screens.register.RegistrationView;

public class RegistrationPresenter extends BasePresenter<RegistrationView> {

    private RegistrationInteractor registrationInteractor;

    public RegistrationPresenter() {
        registrationInteractor = new RegistrationInteractorImpl();
    }

    @Override
    public void attachView(RegistrationView view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView() {
        EventBus.getDefault().unregister(this);
        super.detachView();
    }

    public void registerWithUsernameAndPassword(final String username, final String password){

        if(isViewNotNull()) {

            mView.showRegistrationProgress();
            if (TextUtils.isEmpty(username)) {
                mView.hideLoading();
                mView.showTypeUsername();
                return;
            }

            if(TextUtils.isEmpty(password)) {
                mView.hideLoading();
                mView.showTypePassword();
                return;
            }

            JobExecutor.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    registrationInteractor.registerUserWithUsernameAndPassword(username, password);
                }
            });
        }
    }

    public void goToLogin(){
        if(isViewNotNull()){
            mView.onStartActivityWithoutBundle(LoginActivity.class);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegistrationEvent(RegistrationEvent event){
        if(isViewNotNull()) {
            mView.hideLoading();
            if (event.isSuccessful()) {
                    mView.onStartActivityWithoutBundle(MainActivity.class);
            } else {
                Log.d("sdfs", event.getThrowable().getMessage());
            }
        }
    }
}
