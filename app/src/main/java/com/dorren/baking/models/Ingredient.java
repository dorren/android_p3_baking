package com.dorren.baking.models;

/**
 * Created by dorrenchen on 6/17/17.
 */

public class Ingredient {
    public static final String COLUMN_RECIPE_NAME = "recipe_name";
    public static final String COLUMN_RECIPE_INDEX = "recipe_index";
    public static final String COLUMN_INGREDIENT  = "ingredient";

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
