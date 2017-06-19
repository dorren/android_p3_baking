package com.dorren.baking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.dorren.baking.models.Recipe;
import com.dorren.baking.models.Step;

/**
 * display the recipe step page.
 */
public class StepActivity extends AppCompatActivity {
    private int recipeIndex;
    private int stepIndex;

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

            renderStep();
        }
    }

    private void renderStep() {
        Recipe recipe = RecipeUtil.getCache(recipeIndex);
        Step step = recipe.getSteps()[stepIndex];

        TextView tvDesc = (TextView)findViewById(R.id.step_long_description);
        tvDesc.setText(step.getDescription());
    }
}
