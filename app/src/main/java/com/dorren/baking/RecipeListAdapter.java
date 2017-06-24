package com.dorren.baking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dorren.baking.models.Recipe;
import com.squareup.picasso.Picasso;

/**
 * Created by dorrenchen on 6/18/17.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> {
    private static String KLASS = "RecipeListAdapter";
    private Recipe[] mRecipes;
    private RecipeClickListener mClickHandler;
    private Context mContext;

    public interface RecipeClickListener{
        void onClick(int position);
    }

    public RecipeListAdapter(RecipeClickListener handler){
        this.mClickHandler = handler;
    }

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_card;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeListViewHolder holder, int position) {
        Recipe recipe = getRecipe(position);
        holder.mTitle.setText(recipe.getName());

        // set image
        String imagePath = recipe.getImage();
        if(imagePath == null || imagePath.equals("")){ // set default
            Picasso.with(mContext).load(R.drawable.cake).into(holder.recipeImage);
        }else{
            Picasso.with(mContext).load(imagePath).into(holder.recipeImage);
        }
    }

    @Override
    public int getItemCount() {
        if(mRecipes != null){
            return mRecipes.length;
        }else {
            return 0;
        }
    }

    public void setData(Recipe[] recipes){
        this.mRecipes = recipes;
        this.notifyDataSetChanged();
    }

    private Recipe getRecipe(int position){
        return mRecipes[position];
    }


    public class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitle;
        public ImageView recipeImage;

        public RecipeListViewHolder(View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.recipe_card_title);
            recipeImage = (ImageView) itemView.findViewById(R.id.recipe_card_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int n = getAdapterPosition();
            mClickHandler.onClick(n);
        }
    }
}
