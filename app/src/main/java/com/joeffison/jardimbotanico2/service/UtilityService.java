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

    private static final String API_URL = "joeffison.github.io/jb2/data/utility.json";

    public UtilityService(Context context){
        this.context = context;
    }

    public void list() {
        RequestQueue queue = Volley.newRequestQueue(this.context);

        // Request a string response from the provided API_URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, this.API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Gson gson = new Gson();
                        Utility[] utility = gson.fromJson(response, Utility[].class);
                        Log.i("Utility received", utility.length+"");
                        for (Utility u: utility) {
                            Log.i("Utility received", u+"");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(Log.ERROR+"", "That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
