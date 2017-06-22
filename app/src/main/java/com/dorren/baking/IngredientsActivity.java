package com.dorren.baking;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dorren.baking.models.Recipe;
import com.dorren.baking.utils.RecipeUtil;

public class IngredientsActivity extends AppCompatActivity {
    private static final String RECIPE_INDEX_KEY = "recipe_index_key";
    private int recipeIndex;
    private IngredientsFragment ingredientsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(RecipeUtil.RECIPE_INDEX)){
                recipeIndex = intent.getIntExtra(RecipeUtil.RECIPE_INDEX, 0);
                render();
            }
        }
    }

    private void render() {
        ingredientsFragment = IngredientsFragment.newInstance(recipeIndex);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.ingredients_fragment_holder, ingredientsFragment)
                .commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setToolbarTitle();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(RECIPE_INDEX_KEY, recipeIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        recipeIndex = savedInstanceState.getInt(RECIPE_INDEX_KEY);
    }

    private void setToolbarTitle(){
        Recipe recipe = RecipeUtil.getCache(recipeIndex);
        getSupportActionBar().setTitle(recipe.getName());
    }
}
