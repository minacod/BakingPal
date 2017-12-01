package com.example.shafy.bakingpal.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.shafy.bakingpal.databinding.IngredientListItemBinding;
import com.example.shafy.bakingpal.model.Ingredient;
import com.example.shafy.bakingpal.utils.ImgResUtils;

/**
 * Created by shafy on 23/11/2017.
 */

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientListViewHolder> {
    private Ingredient[] mIngredients;

    public IngredientListAdapter(Ingredient[] mIngredients) {
        this.mIngredients = mIngredients;
    }

    @Override
    public IngredientListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context= parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        IngredientListItemBinding binding = IngredientListItemBinding.inflate(inflater,parent,false);
        return new IngredientListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(IngredientListViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(mIngredients==null)
            return 0;
        return mIngredients.length;
    }

    public class IngredientListViewHolder extends RecyclerView.ViewHolder {
        private IngredientListItemBinding mBinding ;
        private IngredientListViewHolder(IngredientListItemBinding listItemBinding) {
            super(listItemBinding.getRoot());
            mBinding=listItemBinding;
        }

        void bind (int position){
            mBinding.tvIngredient.setText(mIngredients[position].getmIngredient());
            mBinding.tvQuantity.setText(String.valueOf(mIngredients[position].getmQuantity()));
            mBinding.ivUnit.setImageResource(ImgResUtils.getImgRes(mIngredients[position].getmMeasure()));
        }
    }
}
