package com.example.shafy.bakingpal.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Toast;

import com.example.shafy.bakingpal.R;
import com.example.shafy.bakingpal.databinding.ActivityMainBinding;
import com.example.shafy.bakingpal.model.Recipe;
import com.example.shafy.bakingpal.utils.JsonUtils;
import com.example.shafy.bakingpal.utils.NetworkUtils;


import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Recipe[]>
        ,RecipesListAdapter.OnClickHandler{

    static String TAG = "lalala";
    private static final int RECIPES_LOADER_ID = 1;
    private static Recipe[] mRecipes;
    private RecipesListAdapter mAdapter;
    private ActivityMainBinding mBinding;
    private GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        RecyclerView rv = mBinding.rvRecipesList;

        if(NetworkUtils.isConnected(this)) {

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                manager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
            } else {
                manager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
            }
            mBinding.rvRecipesList.setVisibility(View.INVISIBLE);
            mBinding.pbMainActivity.setVisibility(View.VISIBLE);
            mAdapter = new RecipesListAdapter(mRecipes, this);
            rv.setLayoutManager(manager);
            rv.setAdapter(mAdapter);
            rv.setHasFixedSize(true);

            LoaderManager loaderManager = getSupportLoaderManager();
            Loader loader = loaderManager.getLoader(RECIPES_LOADER_ID);
            if (loader == null)
                getSupportLoaderManager().initLoader(RECIPES_LOADER_ID, null, this);
            else
                getSupportLoaderManager().restartLoader(RECIPES_LOADER_ID, null, this);
        }
        else {
            mBinding.rvRecipesList.setVisibility(View.VISIBLE);
            mBinding.pbMainActivity.setVisibility(View.INVISIBLE);
            Toast i = Toast.makeText(this,"You Are Offline Now",Toast.LENGTH_SHORT);
            i.show();
        }
    }

    @Override
    public Loader<Recipe[]> onCreateLoader(int id, Bundle args) {
        return new RecipesLoader(this);
    }
    
    @Override
    public void onLoadFinished(Loader<Recipe[]> loader, Recipe[] data) {
        mBinding.rvRecipesList.setVisibility(View.VISIBLE);
        mBinding.pbMainActivity.setVisibility(View.INVISIBLE);
        mAdapter.swapRecipes(data);
        mRecipes=data;
    }

    @Override
    public void onLoaderReset(Loader<Recipe[]> loader) {

    }

    @Override
    public void onClick(Recipe recipe) {
        Intent i =new Intent(this,RecipeActivity.class);
        i.putExtra("recipe",recipe);
        startActivity(i);
    }

    private static class RecipesLoader extends AsyncTaskLoader<Recipe[]>{
        private RecipesLoader(Context context) {
            super(context);
        }

        @Override
        protected void onStartLoading() {
            if(mRecipes==null)
                forceLoad();
            else deliverResult(mRecipes);
        }
        
        @Override
        public Recipe[] loadInBackground() {
            Log.d(TAG,"bg");
            String json = "";
            try {
                json=NetworkUtils.getResponseFromHttpUrl();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Recipe[] recipes=null;
            if(json!=null&&!json.equals("")){
                try {
                    recipes = JsonUtils.getRecipes(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return recipes;
        }

        @Override
        public void deliverResult(Recipe[] data) {
            super.deliverResult(data);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            manager.setSpanCount(3);
        }
        else {
            manager.setSpanCount(1);
        }
    }

}