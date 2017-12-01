package com.example.shafy.bakingpal.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by shafy on 22/11/2017.
 */

public final class NetworkUtils {


    private NetworkUtils() {
    }

    private static String mUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static String getResponseFromHttpUrl() throws IOException {
        URL url = new URL(mUrl);
        HttpURLConnection urlConnection =(HttpURLConnection)url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();

            Scanner scanner =new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput){
                return scanner.next();
            }
            else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }

    public static boolean isConnected(Context c) {
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;

    }

}
