package com.dorren.baking;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.dorren.baking.models.Ingredient;
import com.dorren.baking.models.Recipe;
import com.dorren.baking.models.Step;
import com.dorren.baking.utils.RecipeUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.mockito.Mockito.*;

/**
 * Created by dorrenchen on 6/22/17.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {
    private static final String KLASS="RecipeActivityTest";


    @Rule
    public ActivityTestRule<RecipeActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipeActivity.class, false, false); // don't launch


    public void setupCache() {
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

    @Before
    public void beforeTest() {
        setupCache();

        mActivityTestRule.launchActivity(new Intent()); // manually launch
    }

    @Test
    public void checkUI() {
        onView(withText("Recipe Ingredients")).check(matches(isDisplayed()));
    }


    @After
    public void afterTest() {
    }
}
