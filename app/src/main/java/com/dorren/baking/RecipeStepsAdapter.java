package com.dorren.baking;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dorren.baking.models.Recipe;
import com.dorren.baking.models.Step;

/**
 * Created by dorrenchen on 6/18/17.
 */

public class RecipeStepsAdapter extends BaseAdapter {

    private Context mContext;
    private int recipeIndex;

    public RecipeStepsAdapter(Context context, int recipeIdx) {
        this.mContext = context;
        this.recipeIndex = recipeIdx;
    }

    private Recipe getRecipe(){
      return RecipeUtil.getCache(recipeIndex);
    }

    @Override
    public int getCount() {
        return getRecipe().getSteps().length;
    }

    @Override
    public Object getItem(int position) {
        return getRecipe().getSteps()[position];
    }

    @Override
    public long getItemId(int position) {
        Step step = (Step)getItem(position);
        long id = step.getId();
        return id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View cardView;
        if (convertView == null) {
            Context context = parent.getContext();
            int layoutIdForListItem = R.layout.step_card;
            LayoutInflater inflater = LayoutInflater.from(context);
            cardView = inflater.inflate(layoutIdForListItem, parent, false);
        } else {
            cardView = convertView;
        }

        TextView tvTitle = (TextView) cardView.findViewById(R.id.step_title);
        final String stepTitle = getRecipe().getSteps()[position].getShortDescription();
        tvTitle.setText(stepTitle);

        tvTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StepActivity.class);
                intent.putExtra(RecipeUtil.RECIPE_INDEX, recipeIndex);
                intent.putExtra(RecipeUtil.STEP_INDEX, position);

                mContext.startActivity(intent);
            }
        });
        return cardView;
    }
}
