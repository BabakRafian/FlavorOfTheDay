package com.example.android.flavoradi.Utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Babak on 11/26/2017.
 */

public class TWITTERObject {
    private static final String TAG = TWITTERObject.class.getSimpleName();
    private String[] mTweetBody;
    private int mCount;


    public TWITTERObject(String response){

        JSONObject tObject;
        JSONArray arr= new JSONArray();
        try {
            tObject = new JSONObject(response);
            arr = tObject.getJSONArray("statuses");
            mCount = arr.length();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mTweetBody = new String[arr.length()];
        for (int i = 0; i < arr.length(); i++)
        {
            try {
                mTweetBody[i] = arr.getJSONObject(i).getString("text");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG, mTweetBody[i]);
        }

    }
    public String[] getTweetBody(){return this.mTweetBody;}
    public int getCount(){return this.mCount;}
}
