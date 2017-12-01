package com.example.shafy.bakingpal.ui;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shafy.bakingpal.databinding.FragmentIngredientBinding;
import com.example.shafy.bakingpal.model.Ingredient;

/**
 * Created by shafy on 23/11/2017.
 */

public class IngredientFragment extends Fragment {

    private Ingredient[] ingredients;
    private GridLayoutManager manager;

    public IngredientFragment() {
    }
    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentIngredientBinding ingredientBinding = FragmentIngredientBinding.inflate(inflater,container,false);
        RecyclerView rv = ingredientBinding.rvIngredients;
        IngredientListAdapter adapter = new IngredientListAdapter(ingredients);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            manager =new GridLayoutManager(this.getContext(),2,GridLayoutManager.VERTICAL,false){
                @Override
                public boolean canScrollVertically() {return false;}
            };
        }
        else {
            manager =new GridLayoutManager(this.getContext(),1,GridLayoutManager.VERTICAL,false){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
        }

        rv.setHasFixedSize(true);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        return ingredientBinding.getRoot();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            manager.setSpanCount(2);
        }
        else {
            manager.setSpanCount(1);
        }
    }
}
