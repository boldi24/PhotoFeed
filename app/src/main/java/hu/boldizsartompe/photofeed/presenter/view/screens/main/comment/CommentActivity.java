package hu.boldizsartompe.photofeed.presenter.view.screens.main.comment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.domain.entity.Comment;
import hu.boldizsartompe.photofeed.domain.entity.Photo;
import hu.boldizsartompe.photofeed.presenter.view.BaseActivity;
import hu.boldizsartompe.photofeed.presenter.view.presenter.IPresenter;
import hu.boldizsartompe.photofeed.presenter.view.presenter.main.comments.CommentPresenter;
import hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.myfeed.MyFeedAdapter;

public class CommentActivity extends BaseActivity implements CommentsView {

    public static final String EXTRA_PHOTOID = "EXTRA_PHOTOID";

    @BindView(R.id.rv_comments)
    RecyclerView recyclerView;
    @BindView(R.id.srf_comments)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.et_comments_comment)
    EditText textET;

    private CommentPresenter commentPresenter;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_comments);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        commentPresenter = new CommentPresenter(getIntent().getStringExtra(EXTRA_PHOTOID));

        ButterKnife.bind(this);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);
        adapter = new CommentAdapter(new ArrayList<Comment>());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                commentPresenter.getComments();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        commentPresenter.getComments();
    }

    @Override
    protected IPresenter getPresenter() {
        return commentPresenter;
    }

    @OnClick(R.id.ibtn_send_comment)
    void onSendClicked(){
        commentPresenter.sendComment(textET.getText().toString());
        textET.setText("");
    }

    @Override
    public void showComments(List<Comment> comments) {
        swipeRefreshLayout.setRefreshing(false);

        adapter.showComments(comments);
    }

    @Override
    public void addComment(Comment comment) {
        adapter.addComment(comment);
    }

    @Override
    public void showLoadingComments() {
        showLoading(getString(R.string.text_loading_comments));
    }

    @Override
    public int getContentView() {
        return R.layout.activity_comment;
    }
}
