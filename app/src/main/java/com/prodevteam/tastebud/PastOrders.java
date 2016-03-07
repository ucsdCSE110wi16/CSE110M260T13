package com.prodevteam.tastebud;

/**
 * Created by yazin on 06-Mar-16.
 */

public class PastOrders {

    private String itemName;
    private double itemRating;

    public String getName(){
        return itemName;
    }

    public double getRating(){
        return itemRating;
    }

    public void setName(String name){
        itemName = name;
    }

    public void setRating(double rating){
        itemRating = rating;
    }
}

