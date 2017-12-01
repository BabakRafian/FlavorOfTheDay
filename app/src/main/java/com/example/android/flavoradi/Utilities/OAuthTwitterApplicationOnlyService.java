package com.example.android.flavoradi.Utilities;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Babak on 11/10/2017.
 *
 * Establish a connection to Twitter API to get a bearer TOKEN
 *
 * Look at also to https://dev.twitter.com/docs/auth/application-only-auth
 *
 */

public class OAuthTwitterApplicationOnlyService {


    final static String TWITTER_CONSUMER_KEY = "kUlY7tH7LM3M8tp3paFJnMMzm";
    final static String TWITTER_CONSUMER_SECRET = "0GqIvlIFRw4SnPVvYgx5qgStzoaPTDKP53OzxIrDvI7wq15jnd";
    final static String URL_TWITTER_OAUTH2_TOKEN = "https://api.twitter.com/oauth2/token";
    //final static  String URL = "GET /1.1/statuses/user_timeline.json?count=100&screen_name=twitterapi HTTP/1.1";
    final static String USERAGENT = "Flavoradi";
    private String applicationOnlyBearerToken;
    private final String TAG = getClass().getSimpleName();

    public OAuthTwitterApplicationOnlyService() {

        try {
            applicationOnlyBearerToken = requestBearerToken();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getApplicationOnlyBearerToken() throws Exception {

        return applicationOnlyBearerToken;
    }

    // Constructs the request for requesting a bearer token and returns that token as a string

    private String requestBearerToken() throws Exception {

            return new SendPostRequest().execute(URL_TWITTER_OAUTH2_TOKEN,
                    TWITTER_CONSUMER_KEY,
                    TWITTER_CONSUMER_SECRET,
                    "grant_type=client_credentials").get();

    }
    /**
     * Async Task that connects to the specified server URL with the specified JSON string.
     */
    private static class SendPostRequest extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            // The server's JSON response in string form.
            String postData = "";
            String bearerToken="";
            String encodedCredentials = params[1] +":"+ params[2];
            byte[] encodedBytes = Base64.encode(encodedCredentials.getBytes(),Base64.NO_WRAP);
            String auth = "Basic " + new String(encodedBytes);

            HttpURLConnection httpConnection= null;
            try {
                // setup connection to server
                httpConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpConnection.setDoOutput(true);
                httpConnection.setDoInput(true);
                httpConnection.setRequestMethod("POST");
                httpConnection.setRequestProperty("Host", "api.twitter.com");
                httpConnection.setRequestProperty("User-Agent", USERAGENT);
                httpConnection.setRequestProperty("Authorization", auth);
                httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                httpConnection.setRequestProperty("Content-Length", "29");
                httpConnection.setUseCaches(false);
                httpConnection.connect();

                // send JSON packet to server
                OutputStream outputStream = httpConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                writer.write(params[3]);
                writer.close();
                outputStream.close();

                // read JSON packet

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                postData = sb.toString();
                JSONParser jp = new JSONParser();
                org.json.simple.JSONObject json = (org.json.simple.JSONObject) jp.parse(postData);
                bearerToken = (String)json.get("access_token");

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpConnection!= null) {
                    httpConnection.disconnect();
                }
            }
            return bearerToken;
        }

        @Override
        protected void onPostExecute(String result) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                super.onPostExecute(result);
            }
        }

    }

}
