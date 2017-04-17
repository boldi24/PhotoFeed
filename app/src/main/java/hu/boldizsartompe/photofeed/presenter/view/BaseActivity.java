package hu.boldizsartompe.photofeed.presenter.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import hu.boldizsartompe.photofeed.presenter.view.presenter.IPresenter;


public abstract class BaseActivity extends AppCompatActivity implements IView {

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IPresenter presenter = getPresenter();
        if(presenter != null) presenter.attachView(this);
    }

    @Override
    protected void onStop() {
        IPresenter presenter = getPresenter();
        if(presenter != null) presenter.detachView();
        super.onStop();
    }

    @Override
    public void showLoading(String message) {
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(true);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        if(mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStartActivityWithBundle(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if(bundle != null) intent.putExtras(bundle);

        startActivity(intent);
        finish();
    }

    @Override
    public void onStartActivityWithoutBundle(Class<?> cls) {
        Intent intent = new Intent(this, cls);

        startActivity(intent);
        finish();
    }

    protected abstract IPresenter getPresenter();
}

