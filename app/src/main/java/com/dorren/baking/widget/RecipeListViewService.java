package com.dorren.baking.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dorren.baking.data.RecipeContentProvider;
import com.dorren.baking.R;
import com.dorren.baking.models.Ingredient;

/**
 * Created by dorrenchen on 6/21/17.
 */

public class RecipeListViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeListViewFactory(this.getApplicationContext(), intent);
    }
}

class RecipeListViewFactory implements RemoteViewsService.RemoteViewsFactory{
    private Context mContext;
    private int mAppWidgetId;
    private Cursor mCursor;

    public RecipeListViewFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        //mCursor = mContext.getContentResolver().query(RecipeContentProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onDataSetChanged() {
        mCursor = mContext.getContentResolver().query(RecipeContentProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onDestroy() {
        mCursor = null;
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        mCursor.moveToPosition(position);

        String txtRecipeName = mCursor.getString(mCursor.getColumnIndex(Ingredient.COLUMN_RECIPE_NAME));
        String txtIngredient = mCursor.getString(mCursor.getColumnIndex(Ingredient.COLUMN_INGREDIENT));
        int    recipeIndex   = mCursor.getInt(mCursor.getColumnIndex(Ingredient.COLUMN_RECIPE_INDEX));


        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        row.setTextViewText(R.id.appwidget_item_text, txtIngredient);

        // set fillInIntent
        Bundle extras = new Bundle();
        extras.putInt(Intent.EXTRA_TEXT, recipeIndex);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        row.setOnClickFillInIntent(R.id.appwidget_item_text, fillInIntent);

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}