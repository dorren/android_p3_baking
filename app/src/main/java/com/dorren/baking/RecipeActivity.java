package com.dorren.baking;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.dorren.baking.models.Recipe;
import com.dorren.baking.utils.RecipeUtil;
import com.dorren.baking.widget.AppWidget;

/** screen to show recipe detail
 *
 */
public class RecipeActivity extends AppCompatActivity implements RecipeDetailFragment.DetailFragmentListener {
    private static final String KLASS = "RecipeActivity";
    private static final String RECIPE_INDEX_KEY = "recipe_index_key";

    private int recipeIndex;
    private RecipeDetailFragment mDetailFragment;
    private View mRightView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        if ((intent != null) && (intent.hasExtra(Intent.EXTRA_TEXT))) {
            recipeIndex = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
            RecipeUtil.setLastRecipeIndex(recipeIndex);
        }else{
            recipeIndex = RecipeUtil.getLastRecipeIndex();
        }

        mRightView = findViewById(R.id.detail_right_holder);
    }

    @Override
    protected void onStart() {
        super.onStart();

        renderDetailFragment();  // left side
        if(isTablet()){  // show ingredients by default
            renderIngredients();
        }

        setToolbarTitle();
        updateWidgets(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(RECIPE_INDEX_KEY, recipeIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        recipeIndex = savedInstanceState.getInt(RECIPE_INDEX_KEY);
    }

    private void renderDetailFragment() {
        mDetailFragment = RecipeDetailFragment.newInstance(recipeIndex);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.detail_fragment_holder, mDetailFragment)
                .commit();
    }

    private void renderIngredients() {
        IngredientsFragment ingredientsFragment = IngredientsFragment.newInstance(recipeIndex);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detail_right_holder, ingredientsFragment)
                .commit();
    }

    private void renderStepFragment(int stepIndex) {
        StepFragment stepFragment = StepFragment.newInstance(recipeIndex, stepIndex);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detail_right_holder, stepFragment)
                .commit();
    }

    public boolean isTablet() {
        return mRightView != null;
    }

    private void setToolbarTitle(){
        Recipe recipe = RecipeUtil.getCache(recipeIndex);
        getSupportActionBar().setTitle(recipe.getName());
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
        if(isTablet()){
            renderIngredients();
        }else {
            Intent intent = new Intent(this, IngredientsActivity.class);
            intent.putExtra(RecipeUtil.RECIPE_INDEX, recipeIndex);

            startActivity(intent);
        }
    }

    @Override
    public void onFragmentStepClicked(int stepIndex) {
        if(isTablet()){
            renderStepFragment(stepIndex);
        }else {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(RecipeUtil.RECIPE_INDEX, recipeIndex);
            intent.putExtra(RecipeUtil.STEP_INDEX, stepIndex);

            startActivity(intent);
        }
    }

    public static void updateWidgets(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), AppWidget.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);
        int[] ids = widgetManager.getAppWidgetIds(new ComponentName(context, AppWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            widgetManager.notifyAppWidgetViewDataChanged(ids, android.R.id.list);
        }
        context.sendBroadcast(intent);
    }
}
