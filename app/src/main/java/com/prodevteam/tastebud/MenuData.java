package com.prodevteam.tastebud;

/**
 * Created by Nzechi Nwaokoro on 2/8/16.
 * this class will interface with the MySQL class as the the objects returned from get menu method
 * the data base; MenuData will also be utilized by different methods to before being passed into
 * A MenuIteam.
 */

public class MenuData {
    private String itemName;
    private String itemPrice;
    private String itemIng;
    private String itemImg;

    public MenuData(String name, String price, String ing, String img) {
        this.itemName = name;
        this.itemPrice = price;
        this.itemIng = ing;
        this.itemImg = img;
    }

    public String getName() {
        return itemName;
    }

    public String getPrice() {
        return itemPrice;
    }

    public String getIng() {
        return itemIng;
    }

    public String getImg() {
        return itemImg;
    }

    public void setName(String itemName) {
        this.itemName = itemName;
    }

    public void setPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setIng(String itemIng) {
        this.itemIng = itemIng;
    }

    public void setImg(String itemImg) {
        this.itemImg = itemImg;
    }

}
