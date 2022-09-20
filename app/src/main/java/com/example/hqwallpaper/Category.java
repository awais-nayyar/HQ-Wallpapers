package com.example.hqwallpaper;

import com.android.volley.toolbox.StringRequest;

import java.io.Serializable;

public class Category implements Serializable {

    String name;
    String imageURL;

    public Category() {
    }

    public Category(String name, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
