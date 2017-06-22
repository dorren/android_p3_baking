package com.dorren.baking.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.SimpleCursorAdapter;

import com.dorren.baking.R;
import com.dorren.baking.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        RemoteViews rv = buildViews(context, appWidgetManager, appWidgetId);
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }

    private static RemoteViews buildViews(Context context, AppWidgetManager appWidgetManager,
                                   int appWidgetId){
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget);

        // setup listView
        Intent intent = new Intent(context, RecipeListViewService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        rv.setRemoteAdapter(R.id.appwidget_list_view, intent);

        // set intent for listView
        Intent appIntent = new Intent(context, RecipeActivity.class);
        appIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setPendingIntentTemplate(R.id.appwidget_list_view, appPendingIntent);

        return rv;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);

        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            int[] ids = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            updateWidgets(context, mgr, ids);
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        WidgetDataService.startActionUpdate(context);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static void updateWidgets(Context context, AppWidgetManager appWidgetManager,
                                          int[] appWidgetIds) {
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list_view);

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void updateRecipeName(Context context, AppWidgetManager appWidgetManager,
                                     int[] appWidgetIds, String recipeName) {
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_list_view);

        for (int appWidgetId : appWidgetIds) {
            RemoteViews rv = buildViews(context, appWidgetManager, appWidgetId);
            rv.setTextViewText(R.id.appwidget_title, recipeName);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static void setupClickHandler(RemoteViews views, Context context){
        Intent intent = new Intent(context, RecipeActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, 1);  //TODO where to save the recipe index?
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_list_view, pendingIntent);
    }
}

