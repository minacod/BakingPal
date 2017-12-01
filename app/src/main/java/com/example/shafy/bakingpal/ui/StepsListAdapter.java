package com.example.shafy.bakingpal.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shafy.bakingpal.R;
import com.example.shafy.bakingpal.databinding.StepsListItemBinding;
import com.example.shafy.bakingpal.model.Step;
import com.squareup.picasso.Picasso;

/**
 * Created by shafy on 22/11/2017.
 */

public class StepsListAdapter extends RecyclerView.Adapter<StepsListAdapter.StepsListViewHolder> {

    private Step[] mSteps;
    private StepClickHandler mHandler;

    interface StepClickHandler{
        public void onClick(int position);
    }

    public StepsListAdapter(Step[] mSteps, StepClickHandler mHandler) {
        this.mSteps = mSteps;
        this.mHandler = mHandler;
    }

    @Override
    public StepsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        StepsListItemBinding binding = StepsListItemBinding.inflate(inflater,parent,false);
        return new StepsListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(StepsListViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(mSteps==null)
            return 0;
        return mSteps.length;
    }

    public class StepsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private StepsListItemBinding mBinding ;
        private StepsListViewHolder(StepsListItemBinding listItemBinding) {
                super(listItemBinding.getRoot());
                mBinding=listItemBinding;
            }

        void bind (int position){
            mBinding.tvStepShortDiscreption.setText(mSteps[position].getmShortDescription());
            String s =mSteps[position].getmDescription();
            if(s.length()>45){
                s = s.substring(0, Math.min(s.length(), 45))+"...";
            }
            mBinding.tvDescription.setText(s);
            if(mSteps[position].getmThumbnailURL()!=null&&!mSteps[position].getmThumbnailURL().equals(""))
                Picasso.with(mBinding.getRoot().getContext())
                        .load(mSteps[position].getmThumbnailURL())
                        .error(R.drawable.ic_bakingapplogo)
                        .into(mBinding.imageView);
            if(position==mSteps.length-1)
                mBinding.vDivider.setVisibility(View.INVISIBLE);
            mBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mHandler.onClick(position);
        }
    }

}
