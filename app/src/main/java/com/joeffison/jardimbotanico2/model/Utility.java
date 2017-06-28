package com.joeffison.jardimbotanico2.model;

import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by Joeffison on 20/06/2017.
 */

public class Utility {
    private final static String IMAGE_HOME_PATH = "https://joeffison.github.io/jb2/data/utility/";
    private final static Gson PARSER = new Gson();

    private int id;
    private String name;
    private String title;
    private String whatsapp;
    private String website;
    private String building;
    private String apto;
    private String category;
    private String[] images;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title.replace("Maravilhoso", "");
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public String getWebsite() {
        return website;
    }

    public String getBuilding() {
        return building;
    }

    public String getApto() {
        return apto;
    }

    public String getCategory() {
        return category;
    }

    public String[] getImages() {
        if(images != null) {
            String [] response = new String[images.length];
            for (int i = 0; i < images.length; i++) {
                response[i] = IMAGE_HOME_PATH + this.id + '/' + images[i];
            }
            return response;
        }

        return images;
    }

    public static Utility toUtility(String utility) {
        return PARSER.fromJson(utility, Utility.class);
    }
}
