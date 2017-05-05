package hu.boldizsartompe.photofeed.presenter.view.screens.main.friends;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.domain.entity.Friend;

public class MyFriendsAdapter extends RecyclerView.Adapter<MyFriendsAdapter.ViewHolder> {

    private List<Friend> friends;

    private IFriendItemCallback callback;

    private Context context;

    public MyFriendsAdapter(Context ctx, List<Friend> friends, IFriendItemCallback callback) {
        this.context = ctx;
        this.friends = friends;
        this.callback = callback;
    }

    interface IFriendItemCallback {

        void deleteFriend(String username);

        void acceptFriend(String username);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myfriends_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Friend friend = friends.get(position);

        switch (friend.getState()){
            case Friend.FRIEND_REQUEST_FROM_ME:
                holder.stateTV.setText("Barátnak jelölted");
                break;
            case Friend.FRIEND_REQUEST_FROM_OTHER:
                holder.stateTV.setText("Bejelölt barátnak");
                holder.acceptBTN.setVisibility(View.VISIBLE);
                holder.acceptBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callback.acceptFriend(friend.getUsername());
                        friend.setState(Friend.VERIFIED_FRIEND);
                        notifyItemChanged(position);
                    }
                });
                break;
            case Friend.VERIFIED_FRIEND:
                holder.stateTV.setText("Barátod");
                holder.deleteBTN.setVisibility(View.VISIBLE);
                holder.deleteBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callback.deleteFriend(friend.getUsername());
                    }
                });
                break;
            default:
                break;
        }

        holder.nameTV.setText(friend.getUsername());

    }

    @Override
    public int getItemCount() {
        if(friends == null) return 0;
        return friends.size();
    }

    public void addFriend(Friend friend){
        friends.add(friend);
        notifyDataSetChanged();
    }

    public void addFriends(List<Friend> friends){
        this.friends = friends;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_myfriends_item_username)
        TextView nameTV;
        @BindView(R.id.btn_myfriends_item_delete)
        Button deleteBTN;
        @BindView(R.id.tv_myfriends_item_state)
        TextView stateTV;
        @BindView(R.id.btn_myfriends_item_accept)
        Button acceptBTN;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}
