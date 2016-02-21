package com.prodevteam.tastebud;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.MenuItem;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nzechi Nwaokoro on 2/8/16.
 * this class will interface with the MySQL class as the the objects returned from get menu method
 * the data base; MenuData will also be utilized by different methods to before being passed into
 * A MenuIteam.
 */

public class MenuData {
    private String itemName;
    private String itemPrice;

    private String majorItemIngs;
    private String minorItemIngs;
    private Drawable itemImg;

    public MenuData(MenuScreen.MenuItem item) {
        itemName = item.getName();
        itemPrice = item.getPrice();
        //TODO: itemIng must be the concatenation of majorItemIngs and minorItemIngs.
        //itemIng = item.getIngredients();
        itemImg = item.getImage();
    }

    public MenuData(String name, String price, String minor_ings, String major_Ings, String img) {
        this.itemName = name;
        this.itemPrice = price;
        this.minorItemIngs = minor_ings;
        this.majorItemIngs = major_Ings;

        new AsyncTask<String, Void, Drawable>() {

            @Override
            protected Drawable doInBackground(String... params) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(params[0]).openConnection();
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    return new BitmapDrawable(App.myApp.getApplicationContext().getResources(), BitmapFactory.decodeStream(input));
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Drawable result) {
                itemImg = result;
            }
        }.execute(img);
    }

    public String getName() {
        return itemName;
    }

    public String getPrice() {
        return itemPrice;
    }

    public Drawable getImg() {
        return itemImg;
    }

    public void setName(String itemName) {
        this.itemName = itemName;
    }

    public void setPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getMajorIngs(){
        return majorItemIngs;
    }

    public String getMinorIngs(){
        return minorItemIngs;
    }

    public void setMinorIngs (String minorIng){
        this.minorItemIngs = minorIng;
    }

    public void setMajorIngs (String majorIng) {
        this.majorItemIngs = majorIng;
    }

    public void setImg(String itemImg) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(itemImg).openConnection();
            connection.connect();
            InputStream input = connection.getInputStream();
            this.itemImg = (new BitmapDrawable(App.myApp.getApplicationContext().getResources(), BitmapFactory.decodeStream(input)));
        } catch (Exception e) {
            this.itemImg = null;
        }
    }

    public void setImg(Drawable img) {
        this.itemImg = img;
    }

}
