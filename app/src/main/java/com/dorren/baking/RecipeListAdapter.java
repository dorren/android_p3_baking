package com.dorren.baking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dorren.baking.models.Recipe;

/**
 * Created by dorrenchen on 6/18/17.
 */

public class RecipeListAdapter extends BaseAdapter {
    private Recipe[] mRecipes;
    private Context mContext;

    public RecipeListAdapter(Context context){
      this.mContext = context;
    }

    @Override
    public int getCount() {
        if(mRecipes != null){
            return mRecipes.length;
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mRecipes[position];
    }

    @Override
    public long getItemId(int position) {
        int id = mRecipes[position].getId();
        long longId = new Long(id);
        return longId;
    }

    public void setData(Recipe[] recipes){
        this.mRecipes = recipes;
        this.notifyDataSetChanged();
    }

    private Recipe getRecipe(int position){
        return (Recipe)getItem(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View cardView;
        if (convertView == null) {
            Context context = parent.getContext();
            int layoutIdForListItem = R.layout.recipe_card;
            LayoutInflater inflater = LayoutInflater.from(context);
            cardView = inflater.inflate(layoutIdForListItem, parent, false);
        } else {
            cardView = convertView;
        }

        TextView title = (TextView) cardView.findViewById(R.id.recipe_card_title);
        title.setText(getRecipe(position).getName());

        title.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, position);
                mContext.startActivity(intent);
            }
        });
        return cardView;
    }
}
