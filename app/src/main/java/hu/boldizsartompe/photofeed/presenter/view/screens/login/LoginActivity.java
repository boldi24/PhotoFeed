package hu.boldizsartompe.photofeed.presenter.view.screens.login;

import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.presenter.utils.MessageShower;
import hu.boldizsartompe.photofeed.presenter.view.BaseActivity;
import hu.boldizsartompe.photofeed.presenter.view.presenter.IPresenter;
import hu.boldizsartompe.photofeed.presenter.view.presenter.login.LoginPresenter;

public class LoginActivity extends BaseActivity implements LoginView {

    @BindView(R.id.et_login_username)
    EditText usernameET;
    @BindView(R.id.et_login_password)
    EditText passwordET;

    private LoginPresenter loginPresenter;

    private boolean isShowLoginProgressOn;
    private static String LOGIN_PROGRESS_ON = "LOGIN_PROGRESS_ON";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            if(savedInstanceState.getBoolean(LOGIN_PROGRESS_ON, false)){
                showLoginProgress();
            }
        }

        loginPresenter = new LoginPresenter();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(LOGIN_PROGRESS_ON, isShowLoginProgressOn);
    }

    @Override
    protected IPresenter getPresenter() {
        if(loginPresenter == null) loginPresenter = new LoginPresenter();
        return loginPresenter;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.tv_login_gotoregistration)
    public void onGoToRegistrationClicked(){
        loginPresenter.onGoToRegistration();
    }

    @OnClick(R.id.btn_login_signin)
    public void onGoTosdfClicked(){
        loginPresenter.loginWithUsernameAndPassword(
                usernameET.getText().toString(),
                passwordET.getText().toString()
        );
    }

    @Override
    public void showTypeUsername() {
        MessageShower.showSnackbarToastOnContextWithMessage(
                findViewById(android.R.id.content),
                getString(R.string.text_type_username));
    }

    @Override
    public void showTypePassword() {
        MessageShower.showSnackbarToastOnContextWithMessage(
                findViewById(android.R.id.content),
                getString(R.string.text_type_password));
    }

    @Override
    public void showLoginProgress() {
        isShowLoginProgressOn = true;
        showLoading(getString(R.string.text_signin_inprogress));
    }

    @Override
    public void hideLoading() {
        isShowLoginProgressOn = false;
        super.hideLoading();
    }

    @Override
    public void showErrorOnLogin() {
        MessageShower.showSnackbarToastOnContextWithMessage(
                findViewById(android.R.id.content),
                getString(R.string.text_error_when_login));
    }
}
