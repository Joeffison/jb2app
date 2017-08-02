package com.joeffison.jardimbotanico2.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.joeffison.jardimbotanico2.model.Utility;

/**
 * Created by Joeffison on 20/06/2017.
 */

public class UtilityService {
    private Context context;

    private static final String API_URL = "https://joeffison.github.io/jb2/data/utility.json";
    private Utility[] utilities;

    public UtilityService(Context context){
        this.context = context;
        utilities = new Utility[2];
    }

    public Utility[] list(Response.Listener<String> onSuccess, Response.ErrorListener onError) {
        RequestQueue queue = Volley.newRequestQueue(this.context);

        // Request a string response from the provided API_URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.API_URL,
                onSuccess, onError);
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        return utilities;
    }

    public Utility[] list(Response.Listener<String> onSuccess) {
        return list(onSuccess, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Log.ERROR+"", "Error on fetching Utility list!");
            }
        });
    }

    public Utility[] list() {
        return list(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                Gson gson = new Gson();
                utilities = gson.fromJson(response, Utility[].class);
            }
        });
    }

}
