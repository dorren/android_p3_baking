package com.dorren.baking;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.dorren.baking.models.Recipe;
import com.dorren.baking.utils.RecipeUtil;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipeClickListener {
    private RecipeListFragment mFragment;

    // for testing
    CountingIdlingResource idlingResource;

    public CountingIdlingResource getIdlingResource(){
        if(idlingResource == null) {
            this.idlingResource = new CountingIdlingResource("MainActivity");

        }
        return idlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragment = (RecipeListFragment)getSupportFragmentManager().findFragmentById(R.id.recipes_list_fragment);
        loadRecipes();
    }

    // fetch recipe json from URL
    private void loadRecipes(){
        new FetchRecipesTask(this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, position);
        startActivity(intent);
    }

    public class FetchRecipesTask extends AsyncTask<String, Void, Recipe[]> {
        private Recipe[] mRecipes;
        private MainActivity mContext;
        private String mErrorMsg;

        public FetchRecipesTask(MainActivity context) {
            super();
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            getIdlingResource().increment(); // for testing
            super.onPreExecute();
        }

        @Override
        protected Recipe[] doInBackground(String... params) {
            try {
                String urlStr = mContext.getResources().getString(R.string.recipes_url);
                URL url = new URL(urlStr);
                mRecipes = RecipeUtil.fetchAll(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } finally {
                getIdlingResource().decrement(); // for testing
            }
            return mRecipes;
        }

        @Override
        protected void onPostExecute(Recipe[] recipes) {
            mFragment.setData(recipes);
            RecipeUtil.cache(recipes);
        }
    }
}
