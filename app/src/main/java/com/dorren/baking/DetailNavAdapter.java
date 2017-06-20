package com.dorren.baking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dorren.baking.models.Recipe;

/**
 * Created by dorrenchen on 6/20/17.
 */

public class DetailNavAdapter extends RecyclerView.Adapter<DetailNavAdapter.DetailNavViewHolder> {
    private Recipe mRecipe;
    private final OnNavItemClickListener mClickHandler;

    public interface OnNavItemClickListener {
        void onClickIngredient();
        void onClickStep(int position);
    }

    public DetailNavAdapter(Recipe recipe, OnNavItemClickListener clickHandler) {
        this.mRecipe = recipe;
        mClickHandler = clickHandler;
    }

    @Override
    public DetailNavAdapter.DetailNavViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.step_card;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new DetailNavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailNavAdapter.DetailNavViewHolder holder, int position) {
        TextView btn = holder.mNavBtn;
        Log.d("onBindViewHolder ", position + ", " + mRecipe.getSteps().length);

        if(position == 0){
            btn.setText("Recipe Ingredients");
        }else {
            String desc = mRecipe.getSteps()[position-1].getShortDescription();
            btn.setText(desc);
        }
    }


    @Override
    public int getItemCount() {
        Log.d("detail nav adapter", "getItemCount steps length " + mRecipe.getSteps().length);

        return mRecipe.getSteps().length + 1;
    }

    public class DetailNavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mNavBtn;

        public DetailNavViewHolder(View view) {
            super(view);
            mNavBtn = (TextView) view.findViewById(R.id.step_title);
            mNavBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int n = getAdapterPosition();
            Log.d("detail nav adapter", "clicked " + n);

            if(n==0) {
                mClickHandler.onClickIngredient();
            }else{
                mClickHandler.onClickStep(n - 1);
            }
        }
    }
}