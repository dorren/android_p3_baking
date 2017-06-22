package com.dorren.baking.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;

import com.dorren.baking.data.RecipeContentProvider;
import com.dorren.baking.R;
import com.dorren.baking.models.Ingredient;

/**
 * This is used to set the recipe name in the widget. Not the best way, but...
 *
 *
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 */
public class WidgetDataService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_UPDATE = "com.dorren.baking.widget.action.update";


    public WidgetDataService() {
        super("WidgetDataService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionUpdate(Context context) {
        Intent intent = new Intent(context, WidgetDataService.class);
        intent.setAction(ACTION_UPDATE);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE.equals(action)) {
                handleActionUpdate();
            }
        }
    }

    /**
     * fetch ingredients data
     */
    private void handleActionUpdate() {
        Cursor cursor = getContentResolver().query(RecipeContentProvider.CONTENT_URI, null, null, null, null);
        cursor.moveToFirst();
        String recipeName = cursor.getString(cursor.getColumnIndex(Ingredient.COLUMN_RECIPE_NAME));

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, AppWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_title);
        AppWidget.updateRecipeName(this, appWidgetManager, appWidgetIds, recipeName);
    }

}
