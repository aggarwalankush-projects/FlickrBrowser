package com.example.anku.flickrbrowser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

public class FlickrRecyclerViewAdaptor extends RecyclerView.Adapter<FlickrViewImageHolder> {

    private Context context;
    private List<Photo> photoList;

    public FlickrRecyclerViewAdaptor(Context context, List<Photo> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @Override
    public FlickrViewImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, null);
        FlickrViewImageHolder flickrViewImageHolder = new FlickrViewImageHolder(view);
        return flickrViewImageHolder;
    }

    @Override
    public void onBindViewHolder(FlickrViewImageHolder holder, int position) {
        Photo photoItem=photoList.get(position);
        Picasso.with(context).load(photoItem.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);
        holder.title.setText(photoItem.getTitle());
    }

    @Override
    public int getItemCount() {
        return (photoList!=null?photoList.size():0);
    }

    public void loadNewData(List<Photo> newPhotos){
        this.photoList=newPhotos;
        notifyDataSetChanged();
    }
}
