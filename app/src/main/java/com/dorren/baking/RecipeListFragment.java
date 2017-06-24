package com.dorren.baking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dorren.baking.models.Recipe;

/**
 * Created by dorrenchen on 6/18/17.
 */

public class RecipeListFragment extends Fragment {
    private RecipeListAdapter mRecipeListAdapter;
    private RecyclerView mRecyclerView;

    public RecipeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_recipes_list, container, false);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipes_grid_view);

        int columns = isTablet() ? 3 : 1;
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), columns);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecipeListAdapter = new RecipeListAdapter((RecipeListAdapter.RecipeClickListener) getActivity());
        mRecyclerView.setAdapter(mRecipeListAdapter);

        // Return the root view
        return rootView;
    }

    public void setData(Recipe[] recipes){
        mRecipeListAdapter.setData(recipes);
    }

    private boolean isTablet(){
        return getResources().getBoolean(R.bool.isTablet);
    }


}