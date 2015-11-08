package com.example.anku.flickrbrowser;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FlickrViewImageHolder extends RecyclerView.ViewHolder {

    private ImageView thumbnail;
    private TextView title;

    public FlickrViewImageHolder(View view) {
        super(view);
        thumbnail = (ImageView) view.findViewById(R.id.ivThumbnail);
        title = (TextView) view.findViewById(R.id.tvTitle);
    }
}
