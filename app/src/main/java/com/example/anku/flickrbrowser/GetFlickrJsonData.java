package com.example.anku.flickrbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetFlickrJsonData extends GetRawData {

    private final String LOG_TAG = GetFlickrJsonData.class.getSimpleName();
    private List<Photo> photos;
    private Uri destinationUri;

    public GetFlickrJsonData(String searchCriteria, boolean matchAll) {
        super(null);
        createAndUpdateUri(searchCriteria, matchAll);
        photos = new ArrayList<>();
    }

    @Override
    public void execute() {
        super.setDataUrl(destinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "URI " + destinationUri.toString());
        downloadJsonData.execute(destinationUri.toString());
    }

    public boolean createAndUpdateUri(String searchCriteria, boolean matchAll) {
        final String BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS = "tags";
        final String TAGMODE = "tagmode";
        final String FORMAT = "format";
        final String NO_JSON_CALLBACk = "nojsoncallback";

        destinationUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(TAGS, searchCriteria)
                .appendQueryParameter(TAGMODE, matchAll ? "ALL" : "ANY")
                .appendQueryParameter(FORMAT, "json")
                .appendQueryParameter(NO_JSON_CALLBACk, "1")
                .build();

        return destinationUri != null;
    }


    public void processResult() {
        if (getDownloadStatus() != DownloadStatus.OK) {
            Log.d(LOG_TAG, "Error download file");
            return;
        }

        final String FLICR_ITEMS = "items";
        final String FLICR_TITLE = "title";
        final String FLICR_MEDIA = "media";
        final String FLICR_PHOTO_URL = "m";
        final String FLICR_AUTHOR = "author";
        final String FLICR_AUTHOR_ID = "author_id";
        final String FLICR_LINK = "link";
        final String FLICR_TAGS = "tags";

        try {
            JSONObject jsonData = new JSONObject(getRawData());
            JSONArray items = jsonData.getJSONArray(FLICR_ITEMS);
            for (int i = 0; i < items.length(); i++) {
                JSONObject photo = items.getJSONObject(i);
                String title = photo.getString(FLICR_TITLE);
                String link = photo.getString(FLICR_LINK);
                String author = photo.getString(FLICR_AUTHOR);
                String author_id = photo.getString(FLICR_AUTHOR_ID);
                String tags = photo.getString(FLICR_TAGS);
                String photoUrl = photo.getJSONObject(FLICR_MEDIA).getString(FLICR_PHOTO_URL);

                photos.add(new Photo(title, author, author_id, link, tags, photoUrl));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error parsing json data");
        }

        for (Photo photo : photos) {
            Log.v(LOG_TAG, photo.toString());
        }
    }

    public class DownloadJsonData extends DownloadRawData {
        @Override
        protected void onPostExecute(String rawData) {
            super.onPostExecute(rawData);
            processResult();
        }

        @Override
        protected String doInBackground(String... params) {
            return super.doInBackground(params);
        }
    }

}
