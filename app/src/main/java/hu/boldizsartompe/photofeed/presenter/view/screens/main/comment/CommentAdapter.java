package hu.boldizsartompe.photofeed.presenter.view.screens.main.comment;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.domain.entity.Comment;


public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    List<Comment> comments;

    public CommentAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Comment comment = comments.get(position);

        if(comment.isMe()) {
            holder.linearLayout.setGravity(Gravity.END);
        } else {
            holder.linearLayout.setGravity(Gravity.LEFT);
        }
        holder.nameTV.setText(comment.getUsername());
        holder.dateTV.setText(comment.getDate());
        holder.commentTV.setText(comment.getText());

    }

    @Override
    public int getItemCount() {
        if(comments == null) return 0;
        return comments.size();
    }

    public void showComments(List<Comment> comments){
        this.comments = comments;
        notifyDataSetChanged();
    }

    public void addComment(Comment comment){
        this.comments.add(comment);

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_comment_item_name)
        TextView nameTV;
        @BindView(R.id.tv_comment_item_date)
        TextView dateTV;
        @BindView(R.id.tv_comment_item_message)
        TextView commentTV;
        @BindView(R.id.ll_comments_main)
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
