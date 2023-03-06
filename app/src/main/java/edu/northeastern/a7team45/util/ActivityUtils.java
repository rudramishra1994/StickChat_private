package edu.northeastern.a7team45.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Patterns;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.northeastern.a7team45.R;

public class ActivityUtils {
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(activity.getCurrentFocus()!=null && activity.getCurrentFocus().getWindowToken()!=null)
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    static public boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static String validInput(String url) throws MalformedURLException {
        if (Patterns.WEB_URL.matcher(url).matches() || URLUtil.isValidUrl(url)) {
            if(!(url.startsWith("https://")||url.startsWith("http://"))){
                return "https://" + url;
            }
            return url;
        }

        throw new MalformedURLException("Invalid Input");
    }

    public static String convertStreamToString(InputStream inputStream){
        StringBuilder stringBuilder=new StringBuilder();
        try {
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String len;
            while((len=bufferedReader.readLine())!=null){
                stringBuilder.append(len);
            }
            bufferedReader.close();
            return stringBuilder.toString().replace(",", ",\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String httpResponse(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        conn.connect();

        // Read response.
        InputStream inputStream = conn.getInputStream();
        return convertStreamToString(inputStream);

    }
    
    public static Map<String,Integer> createStickerMap(){
        Map<String,Integer> stickerMap = new HashMap<>();
        stickerMap.put("sticker1", R.drawable.sticker1);
        stickerMap.put("sticker2", R.drawable.sticker2);
        stickerMap.put("sticker3", R.drawable.sticker3);
        stickerMap.put("sticker4", R.drawable.sticker4);
        stickerMap.put("sticker5", R.drawable.sticker5);
        stickerMap.put("sticker6", R.drawable.sticker6);
        stickerMap.put("sticker7", R.drawable.sticker7);
        stickerMap.put("sticker8", R.drawable.sticker8);
        stickerMap.put("sticker9", R.drawable.sticker9);
        stickerMap.put("sticker10", R.drawable.sticker10);
        stickerMap.put("sticker11", R.drawable.sticker11);
        stickerMap.put("sticker12", R.drawable.sticker12);
        stickerMap.put("sticker13", R.drawable.sticker13);
        return stickerMap;
    }

    public static List<String> getStickerKeys(){
        List<String> stickerKeys = new ArrayList<>();
        stickerKeys.add("sticker1");
        stickerKeys.add("sticker2");
        stickerKeys.add("sticker3");
        stickerKeys.add("sticker4");
        stickerKeys.add("sticker5");
        stickerKeys.add("sticker6");
        stickerKeys.add("sticker7");
        stickerKeys.add("sticker8");
        stickerKeys.add("sticker9");
        stickerKeys.add("sticker10");
        stickerKeys.add("sticker11");
        stickerKeys.add("sticker12");
        stickerKeys.add("sticker13");
        return stickerKeys;
    }
}
