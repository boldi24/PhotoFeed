package hu.boldizsartompe.photofeed.presenter.view.screens.main.friends;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.domain.entity.Friend;

public class MyFriendsAdapter extends RecyclerView.Adapter<MyFriendsAdapter.ViewHolder> {

    private List<Friend> friends;

    private UserInteractionCallback callback;

    private Context context;

    public MyFriendsAdapter(Context ctx, List<Friend> friends, UserInteractionCallback callback) {
        this.context = ctx;
        this.friends = friends;
        this.callback = callback;
    }

    interface UserInteractionCallback{

        void deleteFriend(String username);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myfriends_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Friend friend = friends.get(position);

        if(friend.isVerified()){
            holder.linerLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.verfied));
        } else {
            holder.linerLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.unverfied));
        }
        holder.nameTV.setText(friend.getUsername());
        holder.deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
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

        @BindView(R.id.ll_myfriends_item)
        LinearLayout linerLayout;
        @BindView(R.id.tv_myfriends_item_username)
        TextView nameTV;
        @BindView(R.id.btn_myfriends_item_delete)
        Button deleteBTN;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}
