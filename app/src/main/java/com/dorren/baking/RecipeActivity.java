package com.dorren.baking;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.dorren.baking.models.Recipe;

/** screen to show recipe detail
 *
 */
public class RecipeActivity extends AppCompatActivity implements RecipeDetailFragment.DetailFragmentListener {
    private int recipeIndex;
    private RecipeDetailFragment mDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                recipeIndex = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
                renderRecipe();
            }
        }
    }

    private void renderRecipe(){
        renderDetailFragment();
    }

    private void renderDetailFragment() {
        mDetailFragment = RecipeDetailFragment.newInstance(recipeIndex);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.detail_fragment_holder, mDetailFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFragmentIngredientClicked() {
        Intent intent = new Intent(this, IngredientsActivity.class);
        intent.putExtra(RecipeUtil.RECIPE_INDEX, recipeIndex);

        startActivity(intent);
    }

    @Override
    public void onFragmentStepClicked(int stepIndex) {
        Intent intent = new Intent(this, StepActivity.class);
        intent.putExtra(RecipeUtil.RECIPE_INDEX, recipeIndex);
        intent.putExtra(RecipeUtil.STEP_INDEX, stepIndex);

        startActivity(intent);
    }
}
