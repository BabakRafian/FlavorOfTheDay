package com.example.android.flavoradi;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.flavoradi.Utilities.ColorUtils;
import com.example.android.flavoradi.Utilities.OAuthTwitterApplicationOnlyService;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.maps.model.LatLng;


/**
 * Created by Babak on 11/5/2017.
 */

class MyAdapter extends RecyclerView.Adapter<MyAdapter.DetailViewHolder> {

    private static final String TAG = MyAdapter.class.getSimpleName();
    Intent tweetsPageActivity;

    private static int viewHolderCount;
    private PlaceLikelihoodBufferResponse mLikelyPlaces;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;

    /**
     * Constructor for MyAdapter that accepts a PlaceLikelihoodBufferResponse to display and the specification
     * for the ListItemClickListener.
     *
     * @param likelyPlaces object that has nearby businesses information
     */
    public MyAdapter(PlaceLikelihoodBufferResponse likelyPlaces) {
        mLikelyPlaceNames = new String[likelyPlaces.getCount()];
        mLikelyPlaceAddresses = new String[likelyPlaces.getCount()];
        mLikelyPlaceAttributions = new String[likelyPlaces.getCount()];
        mLikelyPlaceLatLngs = new LatLng[likelyPlaces.getCount()];
        viewHolderCount = 0;
        mLikelyPlaces = likelyPlaces;
        int i =0;
        for (PlaceLikelihood placeLikelihood : likelyPlaces) {
            // Build a list of likely places to show the user.
            mLikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
            mLikelyPlaceAddresses[i] = (String) placeLikelihood.getPlace().getAddress();
            mLikelyPlaceAttributions[i] = (String) placeLikelihood.getPlace().getAttributions();
            mLikelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();

            i++;
            if (i > (likelyPlaces.getCount() - 1)) {
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
        return mLikelyPlaces.getCount();
    }


    class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView listItemBusinessNameView;
        private TextView listItemBusinessAddressView;

        public DetailViewHolder(View itemView) {

            super(itemView);
            final Context context = itemView.getContext();
            final Intent tweetsPageActivityintent = new Intent(context , TweetsPageActivity.class);
            listItemBusinessNameView = (TextView) itemView.findViewById(R.id.tv_item_name);
            listItemBusinessAddressView = (TextView)itemView.findViewById(R.id.tv_item_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    OAuthTwitterApplicationOnlyService test = null;

                    test = new OAuthTwitterApplicationOnlyService();

                    String token=null;
                    try {
                         token = test.getApplicationOnlyBearerToken();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tweetsPageActivityintent.putExtra("placeName", listItemBusinessNameView.getText().toString());
                    tweetsPageActivityintent.putExtra("token",token);
                    context.startActivity(tweetsPageActivityintent);
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

