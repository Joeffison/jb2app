package com.joeffison.jardimbotanico2.model;

import com.google.gson.Gson;

/**
 * Created by Joeffison on 20/06/2017.
 */

public class Utility {
    private String name;
    private String title;
    private String whatsapp;
    private String website;
    private String building;
    private String apto;
    private String category;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
