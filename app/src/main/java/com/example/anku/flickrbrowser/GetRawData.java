package com.example.anku.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


enum DownloadStatus {
    IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK
}

public class GetRawData {
    private final String LOG_TAG = GetRawData.class.getSimpleName();
    private String dataUrl;
    private String rawData;
    private DownloadStatus downloadStatus;

    public GetRawData(String dataUrl) {
        this.dataUrl = dataUrl;
        this.downloadStatus = DownloadStatus.IDLE;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public void reset() {
        this.rawData = null;
        this.dataUrl = null;

        this.downloadStatus = DownloadStatus.IDLE;
    }

    public String getRawData() {
        return rawData;
    }

    public DownloadStatus getDownloadStatus() {
        return downloadStatus;
    }

    public void execute() {
        downloadStatus = DownloadStatus.PROCESSING;
        DownloadRawData downloadRawData = new DownloadRawData();
        downloadRawData.execute(dataUrl);
    }

    public class DownloadRawData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String rawData) {
            GetRawData.this.rawData = rawData;
            if (rawData == null) {
                if (dataUrl == null)
                    downloadStatus = DownloadStatus.NOT_INITIALIZED;
                else
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY;
            } else {
                downloadStatus = DownloadStatus.OK;
            }
        }

        @Override
        protected String doInBackground(String... params) {
            if (params == null)
                return null;

            try {
                BufferedReader reader = null;
                HttpURLConnection connection = null;
                StringBuilder buffer = new StringBuilder();
                try {
                    URL url = new URL(params[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    int responseCode = connection.getResponseCode();
                    Log.d(LOG_TAG, "Response code " + responseCode);
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String tempBuffer;
                    while ((tempBuffer = reader.readLine()) != null) {
                        buffer.append(tempBuffer);
                    }
                    return buffer.toString();
                } finally {
                    connection.disconnect();
                    reader.close();
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error " + e.getMessage());
            }

            return null;
        }
    }
}
