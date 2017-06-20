package com.dorren.baking.models;

/**
 * Created by dorrenchen on 6/17/17.
 */

public class Recipe {
    private int mId, mServings;
    private String mName, mImage;
    private Ingredient[] mIngredients;
    private Step[] mSteps;

    public Recipe(int id, int servings, String name, String image, Ingredient[] ingredients, Step[] steps){
        this.mId = id;
        this.mName = name;
        this.mImage = image;
        this.mIngredients = ingredients;
        this.mSteps = steps;
    }

    public int getId() { return mId; }
    public int getServings() { return  mServings; }
    public String getName() { return mName; }
    public String getImage() { return mImage; }
    public Ingredient[] getIngredients() { return mIngredients; }
    public Step[] getSteps() { return mSteps; }

    public String stepHeading(int stepIndex){
        String result = "Step " + (stepIndex+1) + "/" + getSteps().length;
        return result;
    }
    /**
     * @return ingredients text
     */
    public String ingredientsText() {
        String result = "";

        for(Ingredient ingredient : mIngredients) {
            result += ingredient.toString() + "\n";
        }

        return result;
    }

    public String toString(){

        String result = "recipe " + mName + "\n";

        result += "  --- Ingredients ---\n";
        for(Ingredient ingredient : mIngredients) {
            result += "  " + ingredient.toString() + "\n";
        }

        result += "  --- Steps ---\n";
        for(Step step : mSteps) {
            result += "  " + step.toString() + "\n";
        }
        return result;
    }
}
