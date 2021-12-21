package com.deepmodi.androidservice.myservices;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class SimpleAsyncTaskLoader extends AsyncTaskLoader<Bitmap> {

    String imageUrl;

    public SimpleAsyncTaskLoader(@NonNull Context context,String imageUrl) {
        super(context);
        this.imageUrl = imageUrl;
    }


    @Nullable
    @Override
    public Bitmap loadInBackground() {
        try {
            URL url = new URL(this.imageUrl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            return bmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
