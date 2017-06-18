package com.dorren.baking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.dorren.baking.models.Recipe;

import org.w3c.dom.Text;

/** screen to show recipe detail
 *
 */
public class RecipeActivity extends AppCompatActivity {
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                int position = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
                recipe = RecipeUtil.getCache(position);
                renderRecipe();
            }
        }
    }

    private void renderRecipe(){
        TextView detailStr = (TextView)findViewById(R.id.detail_recipe_str);
        detailStr.setText(recipe.toString());
    }
}
