package hu.boldizsartompe.photofeed.presenter.view.screens.main;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.presenter.view.BaseActivity;
import hu.boldizsartompe.photofeed.presenter.view.presenter.IPresenter;
import hu.boldizsartompe.photofeed.presenter.view.presenter.main.MainPresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.friends.FriendFragment;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.myfeed.MyFeedFragment;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.myphotos.MyPhotosFragment;

public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.vp_main)
    ViewPager viewPager;

    private MainPresenter mainPresenter;

    private MyPhotosFragment myPhotosFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);

        mainPresenter.checkIfUserSignedIn();
    }

    @Override
    protected void onDestroy() {
        mainPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_signout:
                mainPresenter.signOut();
                return true;

            default: return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected IPresenter getPresenter() {
        //Mainactivity has no presnter, fragments have them instead
        return mainPresenter;
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void setUpContent() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.tb_main);
        setSupportActionBar(myToolbar);
        viewPager.setAdapter(new MainPageAdapter(getSupportFragmentManager()));
    }

    private class MainPageAdapter extends FragmentPagerAdapter {

        private int NUM_PAGES = 3;

        public MainPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new MyFeedFragment();
                case 1: {
                    myPhotosFragment = new MyPhotosFragment();
                    return myPhotosFragment;
                }
                case 2: return new FriendFragment();
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return "MyFeed";
                case 1: return "MyPhotos";
                case 2: return "Friends";
                default: return null;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MyPhotosFragment.MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    myPhotosFragment.handleExternalStoragePermission();
                } else {
                    // permission denied! Disable the
                    // functionality that depends on this permission.
                }
            }
            case MyPhotosFragment.MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    myPhotosFragment.takePhoto();
                } else {
                    // permission denied! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }

}
