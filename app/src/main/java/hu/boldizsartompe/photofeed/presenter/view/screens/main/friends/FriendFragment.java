package hu.boldizsartompe.photofeed.presenter.view.screens.main.friends;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.domain.entity.Friend;
import hu.boldizsartompe.photofeed.presenter.utils.MessageShower;
import hu.boldizsartompe.photofeed.presenter.view.BaseActivity;
import hu.boldizsartompe.photofeed.presenter.view.presenter.main.friends.FriendsPresenter;

public class FriendFragment extends Fragment implements FriendView, MyFriendsAdapter.IFriendItemCallback {


    @BindView(R.id.et_friends_findfriend)
    EditText usernameET;

    @BindView(R.id.rv_myfriends)
    RecyclerView recyclerView;

    private MyFriendsAdapter adapter;

    private FriendsPresenter friendsPresenter;

    public FriendFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        friendsPresenter = new FriendsPresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
        friendsPresenter.attachView(this);
        friendsPresenter.getFriends();
    }

    @Override
    public void onStop() {
        friendsPresenter.detachView();
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new MyFriendsAdapter(getContext(), new ArrayList<Friend>(), this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @OnClick(R.id.btn_friends_findfriend)
    public void onSearchButtonClicked(){
        String username = usernameET.getText().toString();

        friendsPresenter.findFriend(username);
    }

    @Override
    public void showSearchingUsername() {
        ((BaseActivity)getActivity()).showLoading(getString(R.string.text_searching_friend));
    }

    @Override
    public void showUserNotFound() {
        MessageShower.showSnackbarToastOnContextWithMessage(
                getActivity().findViewById(android.R.id.content),
                getString(R.string.text_there_is_no_username));
    }

    @Override
    public void showTypeFriendUsername() {
        MessageShower.showSnackbarToastOnContextWithMessage(
                getActivity().findViewById(android.R.id.content),
                getString(R.string.text_type_username));
    }

    @Override
    public void showCantAddYourself() {
        MessageShower.showSnackbarToastOnContextWithMessage(
                getActivity().findViewById(android.R.id.content),
                getString(R.string.text_you_cant_add_yourself));
    }

    @Override
    public void showFriends(List<Friend> friends) {
        adapter.addFriends(friends);
    }

    @Override
    public void showFriend(Friend friend) {
        adapter.addFriend(friend);
    }

    @Override
    public void showAddFriend(final String username) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle(R.string.dialog_title_add_friend);
        alertDialogBuilder
                .setMessage(getString(R.string.text_do_you_want_to_add_friend, username))
                .setCancelable(false)
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.Igen, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        friendsPresenter.addFriend(username);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void showLoading(String message) {
        ((BaseActivity)getActivity()).showLoading(message);
    }

    @Override
    public void hideLoading() {
        ((BaseActivity)getActivity()).hideLoading();
    }

    @Override
    public void onStartActivityWithBundle(Class<?> cls, Bundle bundle) {

    }

    @Override
    public void onStartActivityWithoutBundle(Class<?> cls) {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_friend;
    }

    @Override
    public void deleteFriend(String username) {
        friendsPresenter.deleteFriend(username);
    }

    @Override
    public void acceptFriend(String username) {
        friendsPresenter.acceptFriend(username);
    }
}
