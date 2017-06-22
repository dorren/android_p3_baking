package com.dorren.baking.utils;

import android.content.Context;
import android.util.Log;

import com.dorren.baking.R;
import com.dorren.baking.models.Ingredient;
import com.dorren.baking.models.Recipe;
import com.dorren.baking.models.Step;
import com.dorren.baking.utils.NetworkUtil;

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
    private static int lastRecipeIndex = 0; // used by app widget

    public static void cache(Recipe[] recipes){
        cachedRecipes = recipes;
    }

    public static Recipe getCache(int position) {
        Recipe recipe = null;
        if (cachedRecipes != null) {
            recipe = cachedRecipes[position];
        }

        return recipe;
    }

    public static Recipe[] getCache() {
        return cachedRecipes;
    }

    public static void setLastRecipeIndex(int index){
        lastRecipeIndex = index;
    }

    public static int getLastRecipeIndex() { return lastRecipeIndex; }

    public static Recipe getLastRecipe(Context context){
        if(cachedRecipes == null){     // load cache
            try{
                String urlStr = context.getResources().getString(R.string.recipes_url);
                URL url = new URL(urlStr);
                cachedRecipes = fetchAll(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return cachedRecipes[lastRecipeIndex];
    }


    public static Recipe[] fetchAll(URL url){
        Log.d("RecipeUtil",  "fetchAll ");

        Recipe[] recipes;
        String mErrorMsg;

        if(cachedRecipes != null) {
            Log.d("RecipeUtil",  "fetchAll() found cache " + cachedRecipes.length);
            return cachedRecipes;
        }

        try {
            Log.d("RecipeUtil",  "fetchAll() loading");
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
                //Log.d("RecipeUtil", recipes[i].toString());
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
