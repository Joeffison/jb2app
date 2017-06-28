package com.joeffison.jardimbotanico2;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.joeffison.jardimbotanico2.model.Utility;
import com.joeffison.jardimbotanico2.service.UtilityService;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UtilityFragment extends Fragment {
    private View main_view;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Utility> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.main_view = inflater.inflate(R.layout.fragment_utility, container, false);

        mRecyclerView = (RecyclerView) main_view.findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        // mLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, true);
        mLayoutManager = new GridLayoutManager(main_view.getContext(), 2);
        // mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));

        // specify an adapter (see also next example)
        this.list = new ArrayList<Utility>();

        Utility[] mockData = new UtilityService(main_view.getContext()).list(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
//                Log.d("Utilities received", response);
                Utility[] utilities = gson.fromJson(response, Utility[].class);
                UtilityFragment.this.list.clear();
                UtilityFragment.this.list.addAll(Arrays.asList(utilities));

//                Log.d("Utilities received", utilities.length+"");
//                for (Utility u: utilities) {
//                    Log.d("Utility received", u+"");
//                    UtilityFragment.this.list.add(u);
//                }

                UtilityFragment.this.mAdapter.notifyDataSetChanged();
            }
        });
        UtilityFragment.this.list.addAll(Arrays.asList(mockData));
        mAdapter = new UtilityRVAdapter(this.getContext(), this.list);
        mRecyclerView.setAdapter(mAdapter);

        return main_view;
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = this.main_view.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}
