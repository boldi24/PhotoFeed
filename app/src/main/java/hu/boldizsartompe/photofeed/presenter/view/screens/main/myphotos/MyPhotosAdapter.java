package hu.boldizsartompe.photofeed.presenter.view.screens.main.myphotos;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.boldizsartompe.photofeed.R;
import hu.boldizsartompe.photofeed.domain.entity.Photo;

public class MyPhotosAdapter extends RecyclerView.Adapter<MyPhotosAdapter.ViewHolder> {

    private List<Photo> photos;
    private Context context;

    public MyPhotosAdapter(Context context, List<Photo> photos) {
        this.context = context;
        this.photos = photos;
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
        position = photos.size() - 1 - position;
        Photo currPhoto = photos.get(position);
        Picasso.with(context).load(currPhoto.getDownloadRef()).resize(800,800).centerCrop().into(holder.photoIV);
        holder.dateTV.setText(currPhoto.getDate());

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_myphotos_item)
        ImageView photoIV;
        @BindView(R.id.tv_myphotos_item_date)
        TextView dateTV;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}
