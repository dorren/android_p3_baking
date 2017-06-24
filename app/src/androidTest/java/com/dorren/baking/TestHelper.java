package com.dorren.baking;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.DisplayMetrics;

import com.dorren.baking.models.Recipe;
import com.dorren.baking.utils.RecipeUtil;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by dorrenchen on 6/23/17.
 */

public class TestHelper {
    public static void setupRecipes() {
        // setup RecipeUtil cache
        if(RecipeUtil.getCache() == null) {
            URL recipesURL = null;
            try {
                recipesURL = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Recipe[] recipes = RecipeUtil.fetchAll(recipesURL);
            RecipeUtil.cache(recipes);
        }
    }

    public static String getResourceString(int id) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        return targetContext.getResources().getString(id);
    }

    public static boolean isScreenSw600dp(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float widthDp = displayMetrics.widthPixels / displayMetrics.density;
        float heightDp = displayMetrics.heightPixels / displayMetrics.density;
        float screenSw = Math.min(widthDp, heightDp);
        return screenSw >= 600;
    }
}
