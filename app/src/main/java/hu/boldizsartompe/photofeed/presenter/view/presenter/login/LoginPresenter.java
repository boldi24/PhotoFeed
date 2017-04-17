package hu.boldizsartompe.photofeed.presenter.view.presenter.login;

import android.text.TextUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import hu.boldizsartompe.photofeed.domain.events.login.LoginEvent;
import hu.boldizsartompe.photofeed.domain.interactor.login.LoginInteractor;
import hu.boldizsartompe.photofeed.domain.interactor.login.LoginInteractorImpl;
import hu.boldizsartompe.photofeed.presenter.rx.JobExecutor;
import hu.boldizsartompe.photofeed.presenter.view.presenter.BasePresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.login.LoginView;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.MainActivity;
import hu.boldizsartompe.photofeed.presenter.view.screens.register.RegistrationActivity;

public class LoginPresenter extends BasePresenter<LoginView>{

    private LoginInteractor loginInteractor;

    public LoginPresenter() {
        loginInteractor = new LoginInteractorImpl();
    }

    public void onGoToRegistration(){
        if(isViewNotNull()){
            mView.onStartActivityWithoutBundle(RegistrationActivity.class);
        }
    }

    @Override
    public void attachView(LoginView view) {
        super.attachView(view);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView() {
        EventBus.getDefault().unregister(this);
        super.detachView();
    }

    public void loginWithUsernameAndPassword(final String username, final String password){
        if(isViewNotNull()){

            mView.showLoginProgress();

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
                    loginInteractor.loginWithUsernameAndPassword(username, password);
                }
            });

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event){
        if(isViewNotNull()){
            mView.hideLoading();
            if(event.isSuccessful()){
                mView.onStartActivityWithoutBundle(MainActivity.class);
            } else {
                mView.showErrorOnLogin();
            }
        }
    }

}
