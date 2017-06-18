package com.dorren.baking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.dorren.baking.models.Recipe;

/**
 * Created by dorrenchen on 6/18/17.
 */

public class RecipeListFragment extends Fragment {
    private RecipeListAdapter mRecipeListAdapter;

    public RecipeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipes_list, container, false);
        GridView gridView = (GridView) rootView.findViewById(R.id.recipes_grid_view);

        mRecipeListAdapter = new RecipeListAdapter(getContext());
        gridView.setAdapter(mRecipeListAdapter);

        // Return the root view
        return rootView;
    }

    public void setData(Recipe[] recipes){
        mRecipeListAdapter.setData(recipes);
        Log.d("Fragment", "count " + recipes.length + ", adapter" + mRecipeListAdapter.getCount());
    }

}