package com.dorren.baking;

import android.util.Log;

import com.dorren.baking.models.Ingredient;
import com.dorren.baking.models.Recipe;
import com.dorren.baking.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dorrenchen on 6/17/17.
 */

public class RecipeUtil {
    public static final String RECIPE_INDEX = "com.dorren.baking.recipe_index";
    public static final String STEP_INDEX   = "com.dorren.baking.step_index";

    private static Recipe[] cachedRecipes;

    public static void cache(Recipe[] recipes){
        cachedRecipes = recipes;
    }

    public static Recipe getCache(int position) {
        if(cachedRecipes != null)
            return cachedRecipes[position];
        else
            return null;
    }

    public static Recipe[] all(URL url){
        Recipe[] recipes;
        String mErrorMsg;

        try {
            String response = NetworkUtil.getResponseFromHttpUrl(url);
            JSONArray jsonArray = new JSONArray(response);

            recipes = new Recipe[jsonArray.length()];

            Log.d("RecipeUtil", jsonArray.length() + "");
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                int    id   = item.getInt("id");
                String name = item.getString("name");
                Ingredient[] ingredients = parseIngredients(item.getJSONArray("ingredients"));
                Step[] steps = parseSteps(item.getJSONArray("steps"));
                int    servings = item.getInt("servings");
                String image = item.getString("image");

                recipes[i] = new Recipe(id, servings, name, image, ingredients, steps);
                Log.d("RecipeUtil", recipes[i].toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            mErrorMsg = e.getMessage();
            return null;
        }

        return recipes;
    }

    private static Ingredient[] parseIngredients(JSONArray jsonArray){
        Ingredient[] ingredients = new Ingredient[jsonArray.length()];

        try {
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                ingredients[i] = new Ingredient(item.getString("ingredient"),
                                                item.getString("quantity"),
                                                item.getString("measure"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ingredients;
    }

    private static Step[] parseSteps(JSONArray jsonArray){
        Step[] steps = new Step[jsonArray.length()];

        try {
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                URL videoURL = null;

                String str = item.getString("videoURL");
                if(!str.equals("")) {
                    try{
                        videoURL = new URL(item.getString("videoURL"));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }

                steps[i] = new Step(item.getInt("id"),
                                    item.getString("shortDescription"),
                                    item.getString("description"),
                                    videoURL);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return steps;
    }
}
