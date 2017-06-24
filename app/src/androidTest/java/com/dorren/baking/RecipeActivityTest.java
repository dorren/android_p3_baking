package com.dorren.baking;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dorren.baking.models.Recipe;
import com.dorren.baking.utils.RecipeUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by dorrenchen on 6/22/17.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeActivityTest {
    private static final String KLASS="RecipeActivityTest";
    private RecipeActivity mActivity;
    private boolean isTablet;

    @Rule
    public ActivityTestRule<RecipeActivity> mActivityTestRule =
            new ActivityTestRule<>(RecipeActivity.class, false, false); // don't launch

    @Before
    public void beforeTest() {
        TestHelper.setupRecipes();
        Intents.init();   // for testing activity transitions

        mActivity = mActivityTestRule.launchActivity(new Intent()); // manually launch
        isTablet = TestHelper.isScreenSw600dp(mActivity);
    }


    @Test
    public void checkUI() {
        onView(withText("Recipe Ingredients")).check(matches(isDisplayed()));
    }

    @Test
    public void clickIngredientsButton() {
        String ingredients_txt = TestHelper.getResourceString(R.string.ingredients_text);
        onView(withText(ingredients_txt)).perform(click());


        if(isTablet){

        }else {
            intended(hasComponent(IngredientsActivity.class.getName()));
        }
    }

    @Test
    public void clickStepButton() {
        Recipe recipe = RecipeUtil.getCache(0);
        String step_name = recipe.getSteps()[0].getShortDescription();

        onView(withText(step_name)).perform(click());

        if(isTablet){

        }else {
            // should be on RecipeActivity
            intended(hasComponent(StepActivity.class.getName()));
        }
    }

    @After
    public void afterTest() {
        Intents.release();
    }
}
