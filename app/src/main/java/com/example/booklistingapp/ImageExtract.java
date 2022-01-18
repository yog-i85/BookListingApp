package com.example.booklistingapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public final class ImageExtract {
    private ImageExtract(){
    }
    private static final String TAG=ImageExtract.class.getName();
    public static Bitmap fetchImage(String stringUrl){
        Bitmap bitmap=null;
        try{

            Log.i(TAG, "Thumbnail==============: "+stringUrl);
            InputStream inputStream=new URL(stringUrl).openStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            Log.e("      ","MalformedURL not functioning",e);
        } catch (IOException e) {
            Log.e("     ","Io exeption",e);
        }
        return bitmap;
    }
}
