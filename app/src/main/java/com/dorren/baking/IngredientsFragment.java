package com.dorren.baking;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dorren.baking.models.Recipe;
import com.dorren.baking.utils.RecipeUtil;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsFragment extends Fragment {
    private int recipeIndex;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    public void setRecipeIndex(int index){
        this.recipeIndex = index;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param index recipe index.
     * @return A new instance of fragment IngredientsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngredientsFragment newInstance(int index) {
        IngredientsFragment fragment = new IngredientsFragment();
        fragment.setRecipeIndex(index);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        Recipe recipe = RecipeUtil.getCache(recipeIndex);

        TextView tvIngredients = (TextView)rootView.findViewById(R.id.fragment_ingredients_txt);
        tvIngredients.setText(recipe.ingredientsText());

        return rootView;
    }

}
