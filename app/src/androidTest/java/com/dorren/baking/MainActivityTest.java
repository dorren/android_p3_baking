package com.dorren.baking;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

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
import static android.support.test.espresso.matcher.ViewMatchers.hasSibling;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;

/**
 * Created by dorrenchen on 6/19/17.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    private IdlingResource mIdlingResource;


    // Registers any resource that needs to be synchronized with Espresso before the test is run.
    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();;
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);

        Intents.init();   // for testing activity transitions
    }

    @Test
    public void checkLoadingRecipes() {
        String recipeName = "Nutella Pie";

        // has recipe Nutella Pie
        onView(withText(recipeName)).check(matches(isDisplayed()));

        // recipe has default cake image.
        onView(allOf(withText(recipeName), hasSibling(withTagValue(is((Object) R.drawable.cake)))));
    }

    @Test
    public void clickRecipe() {
        String recipeName = "Nutella Pie";
        onView(withText(recipeName)).perform(click());

        // should be on RecipeActivity
        intended(hasComponent(RecipeActivity.class.getName()));
    }

    // Remember to unregister resources when not needed to avoid malfunction.
    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }

        Intents.release();
    }
}
