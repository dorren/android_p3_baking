package com.dorren.baking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.dorren.baking.models.Recipe;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailFragment.DetailFragmentListener} interface
 * to handle interaction events.
 * Use the {@link RecipeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailFragment extends Fragment implements DetailNavAdapter.OnNavItemClickListener {
    private RecipeStepsAdapter stepsAdapter;
    private RecyclerView mRecyclerView;
    private int recipeIndex;

    private DetailFragmentListener mListener;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    public void setRecipeIndex(int index){
        this.recipeIndex = index;
    }

    public Recipe getRecipe(){
        return RecipeUtil.getCache(recipeIndex);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param index recipe index.
     * @return A new instance of fragment RecipeDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeDetailFragment newInstance(int index) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
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
        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        //renderIngredients(rootView);
        //renderRecipeSteps(rootView);
        renderNav(rootView);
        return rootView;
    }

    private void renderIngredients(View rootView){
        final Context ctx = getActivity();

        TextView tvIngredients = (TextView) rootView.findViewById(R.id.detail_ingredients);
        tvIngredients.setText("Recipe Ingredients");
        tvIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IngredientsActivity.class);
                intent.putExtra(RecipeUtil.RECIPE_INDEX, recipeIndex);

                ctx.startActivity(intent);
            }
        });
    }

    private void renderRecipeSteps(View rootView){
        GridView gridView = (GridView) rootView.findViewById(R.id.detail_steps_grid_view);
        stepsAdapter = new RecipeStepsAdapter(getActivity(), recipeIndex);
        gridView.setAdapter(stepsAdapter);
    }

    private void renderNav(View rootView){
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.detail_nav);

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        DetailNavAdapter navAdapter = new DetailNavAdapter(getRecipe(), this);

        mRecyclerView.setAdapter(navAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int position) {
        if (mListener != null) {
            mListener.onFragmentItemClicked(position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DetailFragmentListener) {
            mListener = (DetailFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(int index) {
        Log.d("detail fragment", " clicked " + index);
        mListener.onFragmentItemClicked(index);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface DetailFragmentListener {
        // TODO: Update argument type and name
        void onFragmentItemClicked(int position);
    }
}
