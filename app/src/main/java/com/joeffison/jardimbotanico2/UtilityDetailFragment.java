package com.joeffison.jardimbotanico2;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.joeffison.jardimbotanico2.model.Utility;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class UtilityDetailFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_utility_detail, container, false);
        Utility utility = Utility.toUtility(getArguments().getString("utility"));
        CarouselView card_thumbnail = ((CarouselView)view.findViewById(R.id.card_thumbnail));
        String[] images = utility.getImages();
        card_thumbnail.setPageCount(images.length < 1? 1 : images.length);
        card_thumbnail.setImageListener(new RecyclerImageListener(utility));
        ((TextView)view.findViewById(R.id.card_title)).setText(utility.getTitle());
        ((TextView)view.findViewById(R.id.card_name)).setText(utility.getName());
        setVisibilityFor(R.id.card_whatsapp, utility.getWhatsapp());
        TextView websiteTextView = (TextView) view.findViewById(R.id.card_website);
        websiteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CLICK", ((TextView)v).getText().toString());
            }
        });
        setVisibilityFor(websiteTextView, utility.getWebsite());
        setVisibilityFor(R.id.card_dweller, (utility.getBuilding() == null || utility.getApto() == null) ? null : "Morador");

        return view;
    }

    private boolean setVisibilityFor(int viewId, String value) {
        return setVisibilityFor((TextView)view.findViewById(viewId), value);
    }

    private boolean setVisibilityFor(TextView view, String value) {
        if(value == null || value.isEmpty()) {
            view.setVisibility(View.GONE);
            return false;
        } else {
            view.setText(value);
            view.setVisibility(View.VISIBLE);
            return true;
        }
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
                Glide.with(view.getContext()).load(images[position]).into(imageView);
            } else {
                Glide.with(view.getContext()).load(R.mipmap.ic_launcher_jb2).into(imageView);
            }

        }
    }
}
