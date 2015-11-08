package com.example.anku.flickrbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewPhotoDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        Photo photo= (Photo) intent.getSerializableExtra("photo_transfer");
        TextView photo_title= (TextView) findViewById(R.id.photo_title);
        TextView photo_tags= (TextView) findViewById(R.id.photo_tags);
        TextView photo_author= (TextView) findViewById(R.id.photo_author);
        photo_title.setText(photo.getTitle());
        photo_tags.setText(photo.getTags());
        photo_author.setText(photo.getAuthor());

        ImageView photoImage = (ImageView) findViewById(R.id.photo_image);
        Picasso.with(this).load(photo.getLink())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(photoImage);
    }

}
