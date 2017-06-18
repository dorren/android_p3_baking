package com.dorren.baking.models;

/**
 * Created by dorrenchen on 6/17/17.
 */

public class Ingredient {
    private String mMeasure, mName;
    private String mQty;

    public Ingredient(String name, String qty, String measure){
        this.mName = name;
        this.mQty = qty;
        this.mMeasure = measure;
    }

    public String getName()    { return mName; }
    public String getQty()     { return mQty;  }
    public String getMeasure() { return mMeasure; }

    public String toString() {
        return mQty + " " + mMeasure + " " + mName;
    }
}
