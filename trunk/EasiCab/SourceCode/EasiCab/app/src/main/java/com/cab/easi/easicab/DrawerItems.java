package com.cab.easi.easicab;

/**
 * Created by sunayak on 10-08-2016.
 */
public class DrawerItems {

    private String title;
    private int icon;

    public DrawerItems(){}

    public DrawerItems(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }


    public void setTitle(String title){
        this.title = title;
    }

    public void setIcon(int icon){
        this.icon = icon;
    }

}
