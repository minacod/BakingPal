package com.example.shafy.bakingpal.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.shafy.bakingpal.R;
import com.example.shafy.bakingpal.data.IngredientContentProvider;
import com.example.shafy.bakingpal.data.IngredientContract;
import com.example.shafy.bakingpal.databinding.ActivityRecipeBinding;
import com.example.shafy.bakingpal.databinding.ActivityRecipeBindingSw600dpImpl;
import com.example.shafy.bakingpal.model.Ingredient;
import com.example.shafy.bakingpal.model.Recipe;
import com.example.shafy.bakingpal.model.Step;
import com.example.shafy.bakingpal.widget.WidgetServices;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeActivity extends AppCompatActivity implements StepsListAdapter.StepClickHandler{

    private StepsListFragment mStepsFragment;
    private IngredientFragment mBasicsFragment;
    private Recipe mRecipe;
    private boolean isTablet;
    private boolean misInWidget;
    private StepDetailsFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        isTablet=getResources().getBoolean(R.bool.is_tablet);
        Intent openedThis= getIntent();
        mRecipe = openedThis.getParcelableExtra("recipe");
        misInWidget=isInWidget();
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(mRecipe.getmName());
            getSupportActionBar().setElevation(0);
        }
        if(isTablet){
            ActivityRecipeBindingSw600dpImpl mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);
            mBinding.tvPersonsNumber.setText(String.valueOf(mRecipe.getmServing()));
        }
        else {
            ActivityRecipeBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_recipe);
            mBinding.tvPersonsNumber.setText(String.valueOf(mRecipe.getmServing()));
        }

        mBasicsFragment =new IngredientFragment();
        mBasicsFragment.setIngredients(mRecipe.getmIngredients());
        FragmentManager basicFragmentManager = getSupportFragmentManager();
        basicFragmentManager.beginTransaction()
                .add(R.id.fl_recipe_info, mBasicsFragment)
                .commit();

        mStepsFragment =new StepsListFragment();
        mStepsFragment.setHandler(this);
        mStepsFragment.setSteps(mRecipe.getmSteps());
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fl_recipe_steps, mStepsFragment)
                .commit();
    }

    boolean isInWidget(){
        Uri uri = IngredientContentProvider.Ingredients.INGREDIENTS;
        Cursor c =getContentResolver().query(uri,null,null,null,null);
        int id=-1;
        if (c != null&&c.getCount()!=0) {
            c.moveToFirst();
            int tmp =c.getColumnIndex(IngredientContract.IngredientEntry.RECIPE_ID);
            id =c.getInt(tmp);
            c.close();
        }
        return id==mRecipe.getmId();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_menu,menu);
        MenuItem item = menu.getItem(0);
        if(misInWidget)item.setIcon(R.drawable.ic_favorite_black_48px);
        else item.setIcon(R.drawable.ic_favorite_border_black_48px);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id==R.id.add){
            Uri uri = IngredientContentProvider.Ingredients.INGREDIENTS;
            if(!misInWidget){
                addToDatabase(uri);
                item.setIcon(R.drawable.ic_favorite_black_48px);
                misInWidget=true;
            }else {
                emptyDatabase(uri);
                item.setIcon(R.drawable.ic_favorite_border_black_48px);
                WidgetServices.startActionUpdateWidget(this);
                misInWidget=false;
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void emptyDatabase(Uri uri){
        getContentResolver().delete(uri,null,null);
    }
    private void addToDatabase(Uri uri) {
        emptyDatabase(uri);
        Ingredient[] ingredients =mRecipe.getmIngredients();
        int length=ingredients.length;
        ContentValues[] cvs = new ContentValues[length];
        for (int i=0 ; i< length ; i++) {
            cvs[i] = new ContentValues();
            cvs[i].put(IngredientContract.IngredientEntry.RECIPE_ID, mRecipe.getmId());
            cvs[i].put(IngredientContract.IngredientEntry.RECIPE_NAME, mRecipe.getmName());
            cvs[i].put(IngredientContract.IngredientEntry.INGREDIENT, ingredients[i].getmIngredient());
            cvs[i].put(IngredientContract.IngredientEntry.MEASURE, ingredients[i].getmMeasure());
            cvs[i].put(IngredientContract.IngredientEntry.QUANTITY, ingredients[i].getmQuantity());
        }
        getContentResolver().bulkInsert(uri, cvs);
        WidgetServices.startActionUpdateWidget(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(int position) {
        if(isTablet)
        {
            if(mFragment!=null)
                getSupportFragmentManager().beginTransaction().remove(mFragment).commit();

            mFragment =new StepDetailsFragment();
            mFragment.setmDescription(mRecipe.getmSteps()[position].getmDescription());
            mFragment.setmImageUrl(mRecipe.getmSteps()[position].getmThumbnailURL());
            mFragment.setmVideoUrl(mRecipe.getmSteps()[position].getmVideoURL());
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .add(R.id.fl_step_details_container,mFragment)
                    .commit();
        }
        else {
            Intent intent = new Intent(this,StepActivity.class);
            ArrayList<Step> steps=new ArrayList<Step>();
            steps.addAll(Arrays.asList(mRecipe.getmSteps()));
            intent.putExtra("steps",steps);
            intent.putExtra("length",mRecipe.getmSteps().length);
            intent.putExtra("position",position);
            startActivity(intent);
        }
    }
}
