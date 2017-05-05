package hu.boldizsartompe.photofeed.presenter.view.screens.main.likes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.boldizsartompe.photofeed.R;

public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.ViewHolder>{

    List<String> usernames;

    public LikesAdapter(List<String> usernames) {
        this.usernames = usernames;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.like_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String username = usernames.get(position);

        holder.usernameTV.setText(username);
    }

    @Override
    public int getItemCount() {
        return usernames.size();
    }

    public void addLikes(List<String> users){
        this.usernames = users;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_like_item_username)
        TextView usernameTV;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
