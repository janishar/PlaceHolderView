package com.mindorks.demo;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mindorks.demo.feed.data.Feed;
import com.mindorks.demo.infinite.InfiniteFeedInfo;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by janisharali on 21/08/16.
 */
public class Utils {

    private static final String TAG = "Utils";

    public static List<Image> loadImages(Context context){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "images.json"));
            List<Image> imageList = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                Image image = gson.fromJson(array.getString(i), Image.class);
                imageList.add(image);
            }
            return imageList;
        }catch (Exception e){
            Log.d(TAG,"seedGames parseException " + e);
            e.printStackTrace();
            return null;
        }
    }

    public static List<Feed> loadFeeds(Context context){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "news.json"));
            List<Feed> feedList = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                Feed feed = gson.fromJson(array.getString(i), Feed.class);
                feedList.add(feed);
            }
            return feedList;
        }catch (Exception e){
            Log.d(TAG,"seedGames parseException " + e);
            e.printStackTrace();
            return null;
        }
    }

    public static List<InfiniteFeedInfo> loadInfiniteFeeds(Context context){
        try{
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            JSONArray array = new JSONArray(loadJSONFromAsset(context, "infinite_news.json"));
            List<InfiniteFeedInfo> feedList = new ArrayList<>();
            for(int i=0;i<array.length();i++){
                InfiniteFeedInfo feed = gson.fromJson(array.getString(i), InfiniteFeedInfo.class);
                feedList.add(feed);
            }
            return feedList;
        }catch (Exception e){
            Log.d(TAG,"seedGames parseException " + e);
            e.printStackTrace();
            return null;
        }
    }

    private static String loadJSONFromAsset(Context context, String jsonFileName) {
        String json = null;
        InputStream is=null;
        try {
            AssetManager manager = context.getAssets();
            Log.d(TAG,"path "+jsonFileName);
            is = manager.open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static Point getNavigationBarSize(WindowManager windowManager){
        try {
            if(Build.VERSION.SDK_INT > 16) {
                Display display = windowManager.getDefaultDisplay();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                display.getMetrics(displayMetrics);
                return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
            }else{
                return new Point(0, 0);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Point(0, 0);
        }
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
