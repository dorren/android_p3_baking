package com.dorren.baking;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dorren.baking.models.Recipe;

/** screen to show recipe detail
 *
 */
public class RecipeActivity extends AppCompatActivity {
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
        TextView detailStr = (TextView)findViewById(R.id.detail_recipe_str);
        //detailStr.setText(recipe.toString());

        renderDetailFragment();
    }

    private void renderDetailFragment() {
        mDetailFragment = RecipeDetailFragment.newInstance(recipeIndex);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.detail_fragment_holder, mDetailFragment)
                .commit();
    }
}
