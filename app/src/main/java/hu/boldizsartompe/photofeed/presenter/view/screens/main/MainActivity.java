package hu.boldizsartompe.photofeed.presenter.view.screens.main;

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
import hu.boldizsartompe.photofeed.presenter.view.screens.main.myfeed.MyFeedFragment;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.myphotos.MyPhotosFragment;

public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.vp_main)
    ViewPager viewPager;

    private MainPresenter mainPresenter;

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

    private static class MainPageAdapter extends FragmentPagerAdapter {

        private static int NUM_PAGES = 2;

        public MainPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new MyFeedFragment();
                case 1: return new MyPhotosFragment();
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
                default: return null;
            }
        }
    }

}
