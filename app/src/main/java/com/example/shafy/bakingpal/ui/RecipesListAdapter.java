package com.example.shafy.bakingpal.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shafy.bakingpal.R;
import com.example.shafy.bakingpal.databinding.RecipeListItemBinding;
import com.example.shafy.bakingpal.model.Recipe;
import com.squareup.picasso.Picasso;

/**
 * Created by shafy on 22/11/2017.
 */

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipesListViewHolder> {

    private Recipe[] mRecipes;
    private OnClickHandler mClickHandler;

    interface OnClickHandler{
        void onClick(Recipe recipe);
    }

    public RecipesListAdapter(Recipe[] mRecipes, OnClickHandler mClickHandler) {
        this.mRecipes = mRecipes;
        this.mClickHandler = mClickHandler;
    }

    public void swapRecipes(Recipe[] recipes){
        this.mRecipes=recipes;
        notifyDataSetChanged();
    }

    @Override
    public RecipesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        RecipeListItemBinding binding = RecipeListItemBinding.inflate(inflater,parent, false);
        return new RecipesListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecipesListViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(mRecipes==null)
            return 0;
        else
            return mRecipes.length;
    }

    public class RecipesListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        RecipeListItemBinding mBinding;

        private RecipesListViewHolder(RecipeListItemBinding binding) {
            super(binding.getRoot());
            mBinding=binding;
        }

        private void bind(int itemNumber){
            mBinding.tvRecipeName.setText(mRecipes[itemNumber].getmName());
            mBinding.getRoot().setOnClickListener(this);
            if(!mRecipes[itemNumber].getmImage().equals(""))
            Picasso.with(mBinding.getRoot().getContext())
                    .load(mRecipes[itemNumber].getmImage())
                    .error(R.drawable.ic_bakingapplogo)
                    .into(mBinding.ivRecipe);
            else
                mBinding.ivRecipe.setImageResource(R.drawable.ic_bakingapplogo);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mClickHandler.onClick(mRecipes[position]);
        }
    }
}
