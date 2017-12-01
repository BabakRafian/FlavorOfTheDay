package com.example.android.flavoradi.Utilities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Babak on 11/25/2017.
 */

public class TwitterHelper {
    private static final String TAG = TwitterHelper.class.getSimpleName();
    private static final String MY_PREFS_NAME = "main_preferences";
    private Context mContext;
    private String mPlaceName;
    private String mURL;
    private TWITTERObject tObject;
    private String twitterResponse;

    final static String TWITTER_BASE_URL = "https://api.twitter.com/1.1/search/tweets.json";
    final static String PARAM_QUERY = "q";
    final static String GEOCODE = "geocode";
    final static String COUNT = "count";
    private String mtweetText;

    //Constructor
    public TwitterHelper(Context context, String placeName, String geocode){
        mContext = context;
        mPlaceName = placeName;
        Uri builtUri = Uri.parse(TWITTER_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, placeName)
                .appendQueryParameter(GEOCODE,geocode)
                .build();
        mURL = builtUri.toString();
        String bearerToken = getBearerToken(context);
        twitterResponse = getTweets(mURL, bearerToken,context);
        Log.d(TAG,twitterResponse);
        //TODO I am working on this!!!
        tObject = new TWITTERObject(twitterResponse);
    }
    public String getTweetText(){return mtweetText;}
    public TWITTERObject getTwitterObject(){return this.tObject;}
    public String getTweetsJson(){return this.twitterResponse;}
    //gets the bearer Token from Teitter
    private static String getBearerToken(Context context){

        // Retrieving the stored bearer Token
        SharedPreferences prefs = context.getApplicationContext().getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String bearerToken = prefs.getString("bearerToken","");

        if(bearerToken.equals("")) {
            OAuthTwitterApplicationOnlyService twitterApplicationOnlyService = new OAuthTwitterApplicationOnlyService();
            try {
                bearerToken = twitterApplicationOnlyService.getApplicationOnlyBearerToken();
                Log.d(TAG, "I got the token: " + bearerToken);
                editor.putString("bearerToken",bearerToken);
                editor.apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bearerToken;
    }

    private static String getTweets(String url, String bearerToken, Context context){
        String response= null;

            try {
                response = new SendPostRequest().execute(url, bearerToken).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            return response;
    }

    /**
     * Async Task that connects to the specified server URL with the specified JSON string.
     */
    private static class SendPostRequest extends AsyncTask<String, Void, String> {
        final static String USERAGENT = "Flavoradi";

        @Override
        protected String doInBackground(String... params) {

            // The server's JSON response in string form.
            String response = "";
            HttpURLConnection httpConnection= null;
            try {
                // setup connection to server
                httpConnection= (HttpURLConnection) new URL(params[0]).openConnection();
                httpConnection.setRequestMethod("GET");
                //httpConnection.setRequestProperty("Host", "api.twitter.com");
                httpConnection.setRequestProperty("User-Agent", USERAGENT);
                httpConnection.setRequestProperty("Authorization", "Bearer " + params[1]);
                //httpConnection.setDoOutput(true);
                httpConnection.setUseCaches(false);
                httpConnection.connect();

                InputStream in = httpConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String line;
                StringBuffer sb = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                response = sb.toString();

        } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

            @Override
        protected void onPostExecute(String result) {super.onPostExecute(result);}

    }

}
