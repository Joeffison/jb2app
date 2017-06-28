package com.joeffison.jardimbotanico2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.joeffison.jardimbotanico2.model.Utility;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.List;

/**
 * Created by joeff on 27/06/2017.
 */

public class UtilityRVAdapter extends RecyclerView.Adapter<UtilityRVAdapter.ViewHolder> {
    private final Context mContext;
    private List<Utility> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView cardTitle;
        public TextView cardName;
        public CarouselView cardThumbnail;
        public CardView cardView;
        public Utility utility;

        public ViewHolder(View view) {
            super(view);
            cardTitle = (TextView) view.findViewById(R.id.card_title);
            cardName = (TextView) view.findViewById(R.id.card_name);
            cardThumbnail = (CarouselView) view.findViewById(R.id.card_thumbnail);
            cardView = (CardView) view.findViewById(R.id.card_view);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UtilityDetailFragment utilityDetailFragment = new UtilityDetailFragment();

//                    switchToFragment(utilityDetailFragment);
                    Log.d("Click", v.getClass().getName());
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UtilityRVAdapter(Context context, List<Utility> myDataset) {
        mContext = context;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public UtilityRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout view = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_utility_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        // ...
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Utility utility = mDataset.get(position);
        holder.utility = utility;

        if(utility != null) {
            holder.cardTitle.setText(utility.getTitle());
            holder.cardName.setText(utility.getName());
            if(utility.getImages() != null && utility.getImages().length > 0) {
//                Glide.with(mContext).load(utility.getImages()[0]).into(holder.cardThumbnail);

                holder.cardThumbnail.setPageCount(utility.getImages().length);
                holder.cardThumbnail.setImageListener(new RecyclerImageListener(utility));
            } else {
//                holder.cardThumbnail.setImageResource(android.R.color.transparent);
                holder.cardThumbnail.setImageListener(new RecyclerImageListener(utility));
                holder.cardThumbnail.setPageCount(1);
//                holder.cardThumbnail.setVisibility(View.GONE);
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private class RecyclerImageListener implements ImageListener {
        private final String[] images;
        private final Utility utility;

        public RecyclerImageListener(Utility utility){
            this.utility = utility;
            if(utility != null) {
                this.images = utility.getImages();
            } else {
                this.images = null;
            }
        }

        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            if(images != null && images.length > 0) {
                Glide.with(mContext).load(images[position]).into(imageView);
            } else {
                Glide.with(mContext).load(R.mipmap.ic_launcher_jb2).into(imageView);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UtilityDetailFragment utilityDetailFragment = new UtilityDetailFragment();
                    Bundle b = ((Activity)mContext).getIntent().getExtras();
                    b = (b == null) ? new Bundle() : b;
                    b.putString("utility", new Gson().toJson(utility));
                    utilityDetailFragment.setArguments(b);
                    switchToFragment(utilityDetailFragment);
                    Log.d("Click", "CARD VIEW");
                }
            });
//            imageView.setImageResource(sampleImages[position]);
        }
    }

    private void switchToFragment(Fragment newFragment) {
        FragmentTransaction transaction = ((AppCompatActivity)this.mContext).getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.main_fragment_container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
