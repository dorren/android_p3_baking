package com.dorren.baking;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dorren.baking.models.Recipe;
import com.dorren.baking.models.Step;
import com.dorren.baking.utils.RecipeUtil;

/**
 * display the recipe step page.
 */
public class StepActivity extends AppCompatActivity {
    private int recipeIndex;
    private int stepIndex;
    private StepFragment mStepFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(RecipeUtil.RECIPE_INDEX))
                recipeIndex = intent.getIntExtra(RecipeUtil.RECIPE_INDEX, 0);
            if (intent.hasExtra(RecipeUtil.STEP_INDEX))
                stepIndex = intent.getIntExtra(RecipeUtil.STEP_INDEX, 0);


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        render();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(Recipe.RECIPE_INDEX_KEY, recipeIndex);
        outState.putInt(Step.STEP_INDEX_KEY, stepIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        recipeIndex = savedInstanceState.getInt(Recipe.RECIPE_INDEX_KEY);
        stepIndex   = savedInstanceState.getInt(Step.STEP_INDEX_KEY);
    }

    public void render(){
        renderStepFragment();
        setToolbarTitle();
    }

    private void renderStepFragment() {
        mStepFragment = StepFragment.newInstance(recipeIndex, stepIndex);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.step_fragment_holder, mStepFragment)
                .commit();
    }

    private void setToolbarTitle(){
        Recipe recipe = RecipeUtil.getCache(recipeIndex);
        getSupportActionBar().setTitle(recipe.getName());
    }

    public void previousStep(View view){
        mStepFragment.previousStep(view);
    }


    public void nextStep(View view){
        mStepFragment.nextStep(view);
    }
}
