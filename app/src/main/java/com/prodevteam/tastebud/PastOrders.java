package com.prodevteam.tastebud;

/**
 * Created by yazin on 06-Mar-16.
 */

public class PastOrders {

    private String itemName;
    private float itemRating;

    public PastOrders(AccountSettingsScreen.PastOrder order) {
        itemName = order.getName().getText().toString();
        itemRating = order.getRating().getRating();
    }

    public PastOrders() {
        itemName = "";
        itemRating = 0;
    }

    public String getName(){
        return itemName;
    }

    public float getRating(){
        return itemRating;
    }

    public void setName(String name){
        itemName = name;
    }

    public void setRating(float item_rating) {
        itemRating = item_rating;
    }
}

