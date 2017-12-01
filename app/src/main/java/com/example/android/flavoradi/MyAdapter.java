package com.example.android.flavoradi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.flavoradi.Utilities.ColorUtils;
import com.example.android.flavoradi.Utilities.GooglePlaceObject;
import com.example.android.flavoradi.Utilities.TWITTERObject;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Babak on 11/5/2017.
 */

class MyAdapter extends RecyclerView.Adapter<MyAdapter.DetailViewHolder> {

    private static final String TAG = MyAdapter.class.getSimpleName();

    private static int viewHolderCount;
    //private PlaceLikelihoodBufferResponse mLikelyPlaces;
    private ArrayList<Pair> mPlaceList;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;
    //private GooglePlaceObject pO;


    /**
     * Constructor for MyAdapter that accepts a PlaceLikelihoodBufferResponse to display and the specification
     * for the ListItemClickListener.
     *
     * @param likelyPlaces object that has nearby businesses information
     */
    public MyAdapter(GooglePlaceObject likelyPlaces) {
        mPlaceList = likelyPlaces.getPlaceList();
        mLikelyPlaceNames = new String[mPlaceList.size()];
        mLikelyPlaceAddresses = new String[mPlaceList.size()];
        mLikelyPlaceAttributions = new String[mPlaceList.size()];
        mLikelyPlaceLatLngs = new LatLng[mPlaceList.size()];
        viewHolderCount = 0;
        int i =0;
        for (Pair tuple : mPlaceList) {
            GooglePlaceObject.PlaceObject placeObject = (GooglePlaceObject.PlaceObject) tuple.first;
            mLikelyPlaceNames[i] = placeObject.getPlaceName();
            mLikelyPlaceAddresses[i] = placeObject.getPlaceAddress();
            mLikelyPlaceAttributions[i] = placeObject.getPlaceAttribution();
            mLikelyPlaceLatLngs[i] = placeObject.getPlaceLatlng();
            i++;
            if (i > (mPlaceList.size()- 1)) {
                break;
            }
        }
    }

    /**
     *
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item you
     *                  can use this viewType integer to provide a different layout. See
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new DetailViewHolder that holds the View for each list item
     */
    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trending_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        DetailViewHolder viewHolder = new DetailViewHolder(view);

        // Use ColorUtils.getViewHolderBackgroundColorFromInstance and pass in a Context and the viewHolderCount
        int backgroundColorForViewHolder = ColorUtils
                .getViewHolderBackgroundColorFromInstance(context, viewHolderCount);
        // et the background color of viewHolder.itemView with the color from above
        viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);
        viewHolderCount++;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);

    }

    @Override
    public int getItemCount() {
        return this.mPlaceList.size();
    }


    class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView listItemBusinessNameView;
        private TextView listItemBusinessAddressView;
        private Button tweetListItemButton;
        private Button addToFavoriteButton;
        //private static final String MY_PREFS_NAME = "main_preferences";

        public DetailViewHolder(View itemView) {

            super(itemView);
            final Context context = itemView.getContext();
            final Intent tweetsPageActivityintent = new Intent(context, TweetsListActivity.class);
            listItemBusinessNameView = (TextView) itemView.findViewById(R.id.tv_item_name);
            listItemBusinessAddressView = (TextView) itemView.findViewById(R.id.tv_item_address);
            tweetListItemButton = (Button) itemView.findViewById(R.id.view_tweets_button);
            addToFavoriteButton = (Button) itemView.findViewById(R.id.add_to_favorite_button);

            final DatabaseHelper mDatabaseHelper = new DatabaseHelper(context, null, null, 1);  //
            SharedPreferences preferences = context.getSharedPreferences("FLAVORADI", MODE_PRIVATE);           //
            final String user = preferences.getString("currentUser", "<user>");                         //

            //View.OnClickListener onClickListener = null;
            //tweetListItemButton.setOnClickListener(onClickListener);
            tweetListItemButton.setOnClickListener(new View.OnClickListener() {
                //itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    TWITTERObject twitterObject = (TWITTERObject) mPlaceList.get(position).second;
                    if (twitterObject.getCount() != 0) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("tweets", twitterObject);
                        tweetsPageActivityintent.putExtras(bundle);
                        context.startActivity(tweetsPageActivityintent);
                    } else {
                        Snackbar.make(v, "No Tweets found for " + mLikelyPlaceNames[position],
                                Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });

            addToFavoriteButton.setOnClickListener(new View.OnClickListener() {
                //itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    GooglePlaceObject.PlaceObject placeObject = (GooglePlaceObject.PlaceObject) mPlaceList.get(position).first;
                    Snackbar.make(v, placeObject.getPlaceName()+" added to your favorite list",
                                Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    String restname = placeObject.getPlaceName();                                   //
                    if (mDatabaseHelper.isFavorite(user, restname)) {                               //
                        mDatabaseHelper.addFavorite(user, restname);                                //
                        addToFavoriteButton.setText("Remove favorite");                             //
                    } else {                                                                        //
                        mDatabaseHelper.deleteFavorite(user, restname);                             //
                        addToFavoriteButton.setText("Add to favorites");                            //
                    }
                }
            });
        }
        public void bind(int listIndex){
            String str = mLikelyPlaceNames[listIndex];
            listItemBusinessNameView.setText(str);
            listItemBusinessAddressView.setText(mLikelyPlaceAddresses[listIndex]);
        }
    }
}

