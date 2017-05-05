package hu.boldizsartompe.photofeed.presenter.view.screens.main.likes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.presenter.view.BaseActivity;
import hu.boldizsartompe.photofeed.presenter.view.presenter.IPresenter;
import hu.boldizsartompe.photofeed.presenter.view.presenter.likes.LikesPresenter;

public class LikesActivity extends BaseActivity implements LikesView {

    public static final String EXTRA_PHOTOID = "EXTRA_PHOTOID";

    @BindView(R.id.tv_likes_numberOfLikes)
    TextView numberOfLikesTV;
    @BindView(R.id.rv_likes)
    RecyclerView recyclerView;

    private LikesAdapter adapter;
    private LikesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ButterKnife.bind(this);

        presenter = new LikesPresenter(getIntent().getStringExtra(EXTRA_PHOTOID));

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new LikesAdapter(new ArrayList<String>());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.getLikes();
    }

    @Override
    protected IPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showLikes(List<String> likes) {
        numberOfLikesTV.setText(getResources().getString(R.string.text_num_likes, likes.size()));
        adapter.addLikes(likes);
    }

    @Override
    public void showDownloadingLikes() {
        showLoading(getString(R.string.text_downloading_likes));
    }

    @Override
    public int getContentView() {
        return R.layout.activity_likes;
    }
}
