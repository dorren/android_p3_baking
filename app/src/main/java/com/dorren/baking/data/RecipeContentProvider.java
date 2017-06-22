package com.dorren.baking.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import com.dorren.baking.R;
import com.dorren.baking.models.Ingredient;
import com.dorren.baking.models.Recipe;
import com.dorren.baking.utils.RecipeUtil;

import java.net.MalformedURLException;
import java.net.URL;

public class RecipeContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.dorren.baking";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_INGREDIENTS = "ingredients";
    public static final Uri CONTENT_URI =  BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENTS).build();
    public static final int CODE_INGREDIENTS = 100;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, PATH_INGREDIENTS, CODE_INGREDIENTS);
        return matcher;
    }

    public RecipeContentProvider() {

    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        String result;

        switch (match){
            case CODE_INGREDIENTS:{
                result = ContentResolver.CURSOR_DIR_BASE_TYPE;
                break;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        return result;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)) {
            case CODE_INGREDIENTS : {
                Recipe recipe = fetchLastRecipe();
                int recipeIndex = getRecipeIndex(recipe);

                String[] columns = new String[]{Ingredient.COLUMN_RECIPE_NAME,
                                                Ingredient.COLUMN_INGREDIENT,
                                                Ingredient.COLUMN_RECIPE_INDEX};
                MatrixCursor mCursor = new MatrixCursor(columns);
                for(Ingredient item : recipe.getIngredients()){
                    mCursor.newRow().add(Ingredient.COLUMN_RECIPE_NAME, recipe.getName())
                                    .add(Ingredient.COLUMN_INGREDIENT, item.toString())
                                    .add(Ingredient.COLUMN_RECIPE_INDEX, recipeIndex);
                }

                cursor = mCursor;
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return cursor;
    }

    private Recipe fetchLastRecipe(){
        Recipe lastRecipe = RecipeUtil.getLastRecipe(getContext());
        return lastRecipe;
    }

    private int getRecipeIndex(Recipe recipe){
        Recipe[] recipes = RecipeUtil.getCache();
        int index = java.util.Arrays.asList(recipes).indexOf(recipe);
        return index;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
