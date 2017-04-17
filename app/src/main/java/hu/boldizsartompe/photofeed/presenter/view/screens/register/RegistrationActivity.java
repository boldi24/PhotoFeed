package hu.boldizsartompe.photofeed.presenter.view.screens.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.presenter.utils.MessageShower;
import hu.boldizsartompe.photofeed.presenter.view.BaseActivity;
import hu.boldizsartompe.photofeed.presenter.view.presenter.IPresenter;
import hu.boldizsartompe.photofeed.presenter.view.presenter.register.RegistrationPresenter;

public class RegistrationActivity extends BaseActivity implements RegistrationView {
    @BindView(R.id.et_register_username)
    EditText usernameET;
    @BindView(R.id.et_register_password)
    EditText passwordET;

    private RegistrationPresenter registrationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registrationPresenter = new RegistrationPresenter();
    }

    @Override
    protected IPresenter getPresenter() {
        if(registrationPresenter == null) registrationPresenter = new RegistrationPresenter();
        return registrationPresenter;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_registration;
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
    public void showRegistrationProgress() {
        showLoading(getString(R.string.text_registration_inprogress));
    }

    @OnClick(R.id.btn_register_register)
    public void onRegistrationButtonClicked(){
        registrationPresenter.registerWithUsernameAndPassword(
                usernameET.getText().toString().trim(),
                passwordET.getText().toString().trim()
        );
    }

    @OnClick(R.id.tv_register_gotologin)
    public void onGotoSignInClicked(){
        registrationPresenter.goToLogin();
    }
}
