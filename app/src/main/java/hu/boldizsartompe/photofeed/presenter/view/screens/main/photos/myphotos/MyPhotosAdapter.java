package hu.boldizsartompe.photofeed.presenter.view.screens.main.photos.myphotos;


import android.content.Context;
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

public class MyPhotosAdapter extends RecyclerView.Adapter<MyPhotosAdapter.ViewHolder> {

    private List<Photo> photos;
    private Context context;
    private IPhoto callback;

    interface IPhoto{

        void likeClicked(int position);

        void commentClicked(int position);
    }

    public MyPhotosAdapter(Context context, List<Photo> photos, IPhoto callback) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myphotos_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int myPosition = photos.size() - 1 - position;
        Photo currPhoto = photos.get(myPosition);
        Picasso.with(context).load(currPhoto.getDownloadRef()).resize(800,800).centerCrop().into(holder.photoIV);
        holder.dateTV.setText(currPhoto.getDate());
        holder.commentIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.commentClicked(myPosition);
            }
        });
        holder.likeTV.setText(context.getResources()
                .getString(R.string.text_num_likes,
                        currPhoto.getWhoLikedThPhoto() == null ? 0 : currPhoto.getWhoLikedThPhoto().size()));
        holder.likeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.likeClicked(myPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(photos == null) return 0;
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_myphotos_item)
        ImageView photoIV;
        @BindView(R.id.tv_myphotos_item_date)
        TextView dateTV;
        @BindView(R.id.iv_myphotos_item_comment)
        ImageView commentIV;
        @BindView(R.id.tv_myphotos_item_likes)
        TextView likeTV;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}
