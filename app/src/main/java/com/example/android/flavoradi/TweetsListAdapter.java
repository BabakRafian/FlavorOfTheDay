package com.example.android.flavoradi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.flavoradi.Utilities.ColorUtils;
import com.example.android.flavoradi.Utilities.GooglePlaceObject;
import com.example.android.flavoradi.Utilities.OAuthTwitterApplicationOnlyService;
import com.example.android.flavoradi.Utilities.TWITTERObject;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.maps.model.LatLng;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Babak on 11/5/2017.
 */

public class TweetsListAdapter extends RecyclerView.Adapter<TweetsListAdapter.DetailViewHolder> {

    private static final String TAG = TweetsListAdapter.class.getSimpleName();
    //Intent tweetsPageActivity;
    private static int viewHolderCount;
    private String[] mTweetMessage;
    //private String[] mLikelyPlaceNames;


    /**
     * Constructor for TweetsListAdapter that accepts a Tweets object to display and the specification
     * for the ListItemClickListener.
     *
     * @param tweets object that has all related tweets
     */
    public TweetsListAdapter(TWITTERObject tweets) {
        mTweetMessage = new String[tweets.getCount()];

        viewHolderCount = 0;
        int i =0;
        for (String tweet : tweets.getTweetBody()) {
            // Build a list of likely places to show the user.
            mTweetMessage[i] = tweet;


            i++;
            if (i > (tweets.getCount() - 1)) {
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
     *                  {@link RecyclerView.Adapter#getItemViewType(int)}
     *                  for more details.
     * @return A new DetailViewHolder that holds the View for each list item
     */
    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.tweets_list_item;
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
        return mTweetMessage.length;
    }


    class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView listItemBusinessNameView;
        private TextView listItemBusinessAddressView;
        private static final String MY_PREFS_NAME = "main_preferences";

        public DetailViewHolder(View itemView) {

            super(itemView);
            final Context context = itemView.getContext();
            //final Intent tweetsPageActivityintent = new Intent(context , TweetsPageActivity.class);
            listItemBusinessNameView = (TextView) itemView.findViewById(R.id.tv_item_name);
            //listItemBusinessAddressView = (TextView)itemView.findViewById(R.id.tv_item_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    Snackbar.make(v, "Click detected on item " + mTweetMessage[position],
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
        public void bind(int listIndex){
            String str = mTweetMessage[listIndex];
            listItemBusinessNameView.setText(str);
            //listItemBusinessAddressView.setText(mTweetMessage[listIndex]);
        }
    }
}

