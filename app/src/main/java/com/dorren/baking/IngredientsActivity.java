package com.dorren.baking;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dorren.baking.models.Recipe;

public class IngredientsActivity extends AppCompatActivity {
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
}
