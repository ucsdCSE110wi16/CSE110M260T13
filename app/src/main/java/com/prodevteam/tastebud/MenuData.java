package com.prodevteam.tastebud;

/**
 * Created by Nzechi Nwaokoro on 2/8/16.
 * this classe will interface with the MySQL class as the the objects returned from get menu method
 * the data base; MenuData will also be utilized by different methods to before being passed into
 * A MenuIteam.
 */

public class MenuData {
    private String itemIcon;
    private String iteamName;
    private String iteamPrice;
    private String iteamIng;

    // constrcuctor to create new menudata
    public MenuData(String icon, String name, String price, String ing){
        this.itemIcon = icon;
        this.iteamName = name;
        this.iteamPrice = price;
        this.iteamIng = ing;
    }


    // get method to retrieve the data in MenuData
    public String getIteamIng() {
        return iteamIng;
    }

    public void setIteamName(String iteamName) {
        this.iteamName = iteamName;
    }

    public void setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
    }

    public void setIteamPrice(String iteamPrice) {
        this.iteamPrice = iteamPrice;
    }
}