package com.example.millennialnews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoadImageNews extends AsyncTask<String, Void, Bitmap> {

    ImageView imageView;

    public LoadImageNews(ImageView imageView) {
        this.imageView = imageView;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Log.i("ON DO IN BACKGROUND", "Starting...");
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Failed to connect");
            }
            InputStream is = con.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        } catch (Exception e) {
            Log.e("Image", "Failed to load image", e);
            Log.e("error", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.i("Post Execute", "Starting...");
        if (imageView != null && bitmap != null) {
            Log.i("Post Execute", "Load Image");
            imageView.setImageBitmap(bitmap);
        }
    }
}
