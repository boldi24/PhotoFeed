package hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.myfeed;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.domain.entity.Photo;

public class MyFeedAdapter extends RecyclerView.Adapter<MyFeedAdapter.ViewHolder> {

    private List<Photo> photos;
    private Context context;
    private IPhoto callback;

    interface IPhoto{

        void likePhoto(int position);

        void commentClicked(int position);

    }

    public MyFeedAdapter(Context context, List<Photo> photos, IPhoto callback) {
        this.context = context;
        this.photos = photos;
        this.callback = callback;
    }

    public void addPhoto(Photo photo){
        photos.add(photo);
        notifyDataSetChanged();
    }

    public void addPhotos(List<Photo> photos){
        this.photos = photos;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myfeed_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final int myPosition = photos.size() - 1 - position;
        final Photo currPhoto = photos.get(myPosition);
        Picasso.with(context).load(currPhoto.getDownloadRef()).resize(800,800).centerCrop().into(holder.photoIV);
        holder.dateTV.setText(currPhoto.getDate());

        holder.nameTV.setText(currPhoto.getSenderUserName());

        holder.likeIV.setBackgroundResource(getLikeButtonId(currPhoto.isDoILikeIt()));
        holder.likeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currPhoto.setDoILikeIt(!currPhoto.isDoILikeIt());
                callback.likePhoto(myPosition);
                view.setActivated(currPhoto.isDoILikeIt());
            }
        });
        holder.commentIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.commentClicked(myPosition);
            }
        });

    }

    private int getLikeButtonId(boolean isLiked){
        if(isLiked) return R.drawable.ic_favorite_black_36dp;
        else return R.drawable.ic_favorite_border_black_36dp;
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_myfeed_item_photo)
        ImageView photoIV;
        @BindView(R.id.tv_myfeed_item_date)
        TextView dateTV;
        @BindView(R.id.tv_myfeed_item_username)
        TextView nameTV;
        @BindView(R.id.iv_myfeed_item_like)
        ImageView likeIV;
        @BindView(R.id.iv_myfeed_item_comment)
        ImageView commentIV;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
