package com.dorren.baking.widget;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dorren.baking.R;

/**
 * Created by dorrenchen on 6/21/17.
 */

public class IngredientsAdapter extends CursorAdapter {
    public IngredientsAdapter(Context context, Cursor c, boolean requery) {
        super(context, c, requery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int itemLayout = R.layout.widget_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(itemLayout, parent, false);
        return itemView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
