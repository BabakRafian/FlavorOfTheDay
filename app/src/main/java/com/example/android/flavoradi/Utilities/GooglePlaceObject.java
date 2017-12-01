package com.example.android.flavoradi.Utilities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.maps.model.LatLng;
import com.example.android.flavoradi.Utilities.TWITTERObject;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Created by Babak on 11/29/2017.
 */

public class GooglePlaceObject implements Serializable{

    private ArrayList<Pair> placeList;
    private Pair<PlaceObject,TWITTERObject> placeTuple;

    private static final String TAG = GooglePlaceObject.class.getSimpleName();
    private static final int RADIUS = 10; // Radius in miles for twitter geocode

    public GooglePlaceObject(Context context, PlaceLikelihoodBufferResponse likelyPlaces){
        placeList = new ArrayList<Pair>();
        int i =0;
        for (PlaceLikelihood placeLikelihood : likelyPlaces) {
            Log.d(TAG,"place ID:"+placeLikelihood.getPlace().getPlaceTypes().get(0));
            // Build a list of likely places to show the user.
            //for(int j =0; j<placeLikelihood.getPlace().getPlaceTypes().size(); j++){
                //int placeType = placeLikelihood.getPlace().getPlaceTypes().get(j);
                //if(placeType == Place.TYPE_RESTAURANT ||
                        //placeType==Place.TYPE_CAFE){
                    PlaceObject placeObject = new PlaceObject();
                    String placeName = (String)placeLikelihood.getPlace().getName();
                    placeObject.setPlaceName(placeName);
                    placeObject.setPlaceAddress((String) placeLikelihood.getPlace().getAddress());
                    placeObject.setPlaceAttribution((String) placeLikelihood.getPlace().getAttributions());
                    LatLng latLng = placeLikelihood.getPlace().getLatLng();
                    placeObject.setPlaceLatLng(latLng);
                    String geocode = getGeocode(latLng);
                    TwitterHelper twitterHelper = new TwitterHelper(context,placeName,geocode);
                    String response = twitterHelper.getTweetsJson();
                    TWITTERObject tweetList = new TWITTERObject(response);
                    placeTuple = Pair.create(placeObject,tweetList);
                    placeList.add(placeTuple);
                //}
            //}
            i++;
            if (i > (likelyPlaces.getCount() - 1)) {
                break;
            }
        }
    }


    public ArrayList<Pair> getPlaceList(){
        sortList();
        return this.placeList;
    }

    private void sortList() {

        int length = this.placeList.size()-1;
        for(int i=0; i<length ;i++){
            for(int j=length; j>i; j--){
                TWITTERObject tObject1 = (TWITTERObject)this.placeList.get(i).second;
                int count1 = tObject1.getCount();
                TWITTERObject tObject2 = (TWITTERObject)this.placeList.get(j).second;
                int count2 = tObject2.getCount();
                if(count2 > count1){
                    PlaceObject f = (PlaceObject) this.placeList.get(i).first;
                    TWITTERObject s = (TWITTERObject) this.placeList.get(i).second;
                    Pair<PlaceObject,TWITTERObject> temp = new Pair<>(f,s);
                    this.placeList.set(i,this.placeList.get(j));
                    this.placeList.set(j,temp);
                }
            }
        }
    }

    private String getGeocode(LatLng latLng){
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        StringBuilder locatoin = new StringBuilder();
        locatoin.append(latitude);
        locatoin.append(",");
        locatoin.append(longitude);
        locatoin.append(",");
        locatoin.append(RADIUS);
        locatoin.append("mi");
        String geocode=locatoin.toString();
        return geocode;
    }

    public class PlaceObject{
        private String mLikelyPlaceName;
        private String mLikelyPlaceAddress;
        private String mLikelyPlaceAttribution;
        private LatLng mLikelyPlaceLatLng;
        PlaceObject(){
            mLikelyPlaceName = "";
            mLikelyPlaceAddress = "";
            mLikelyPlaceAttribution = "";
            mLikelyPlaceLatLng = null;
        }
        public void setPlaceName(String placeName){
            this.mLikelyPlaceName = placeName;
        }
        public void setPlaceAddress(String address){
            this.mLikelyPlaceAddress = address;
        }
        public void setPlaceAttribution(String attribution){
            this.mLikelyPlaceAttribution = attribution;
        }
        public void setPlaceLatLng(LatLng latLngn){
            this.mLikelyPlaceLatLng = latLngn;
        }
        public String getPlaceName() {
            return this.mLikelyPlaceName;
        }
        public String getPlaceAddress() {
            return this.mLikelyPlaceAddress;
        }
        public String getPlaceAttribution() {
            return this.mLikelyPlaceAttribution;
        }
        public LatLng getPlaceLatlng() {
            return this.mLikelyPlaceLatLng;
        }
    }
}
