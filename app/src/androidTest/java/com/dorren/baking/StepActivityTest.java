package com.dorren.baking;

import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.dorren.baking.models.Recipe;
import com.dorren.baking.models.Step;
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
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

/**
 * Created by dorrenchen on 6/24/17.
 */
@RunWith(AndroidJUnit4.class)
public class StepActivityTest {
    private static final String KLASS="StepActivityTest";
    private StepActivity mActivity;
    private boolean isTablet;
    private Recipe mRecipe;

    @Rule
    public ActivityTestRule<StepActivity> mActivityTestRule =
            new ActivityTestRule<>(StepActivity.class, false, false); // don't launch

    @Before
    public void beforeTest() {
        TestHelper.setupRecipes();
        Intents.init();   // for testing activity transitions

        Intent intent = new Intent();
        intent.putExtra(RecipeUtil.RECIPE_INDEX, 0);
        intent.putExtra(RecipeUtil.STEP_INDEX, 0);
        mActivity = mActivityTestRule.launchActivity(intent); // manually launch

        isTablet = TestHelper.isScreenSw600dp(mActivity);
        mRecipe = RecipeUtil.getCache(0);
    }


    @Test
    public void checkUI() {
        String item_text = mRecipe.getSteps()[0].getDescription();
        onView(withText(item_text)).check(matches(isDisplayed()));

        onView(withId(R.id.prev_btn)).check(matches(not(isEnabled())));
        onView(withId(R.id.next_btn)).check(matches(isEnabled()));
    }

    @Test
    public void clickNext() {
        Step[] steps = mRecipe.getSteps();

        for(int i=0; i< steps.length - 1; i++) {
            onView(withId(R.id.next_btn)).perform(click()); // click to last step
        }

        onView(withId(R.id.next_btn)).check(matches(not(isEnabled())));
    }

    @After
    public void afterTest() {
        Intents.release();
    }
}