package com.example.shafy.bakingpal.ui;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.shafy.bakingpal.R;
import com.example.shafy.bakingpal.databinding.ActivityStepBinding;
import com.example.shafy.bakingpal.model.Step;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity {

    private Step[] mSteps;
    private int mIndex;
    private ActivityStepBinding mBinding;
    private StepDetailsFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_step);
        Intent openedThis = getIntent();


        ArrayList<Step> steps = openedThis.getParcelableArrayListExtra("steps");
        int length = openedThis.getIntExtra("length",0);
        mSteps= steps.toArray(new Step[length]);
        mIndex = openedThis.getIntExtra("position",0);

        if(savedInstanceState!=null)
            mIndex=savedInstanceState.getInt("index");

        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle(mSteps[mIndex].getmShortDescription());

        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_step);

        if(mIndex==0){
            mBinding.btnBack.setEnabled(false);
        }
        else if(mIndex==mSteps.length-1){
            mBinding.btnNext.setEnabled(false);
        }


        makeFragment();

        mBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
            }
        });
        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousStep();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index",mIndex);

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFragment();
    }

    private void makeFragment(){
        if(mFragment!=null)
            getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
        mFragment =new StepDetailsFragment();
        mFragment.setmDescription(mSteps[mIndex].getmDescription());
        mFragment.setmImageUrl(mSteps[mIndex].getmThumbnailURL());
        mFragment.setmVideoUrl(mSteps[mIndex].getmVideoURL());
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.fl_step_details_container,mFragment)
                .commit();
    }



    private void updateFragment() {
        mFragment.setmDescription(mSteps[mIndex].getmDescription());
        mFragment.setmImageUrl(mSteps[mIndex].getmThumbnailURL());
        mFragment.setmVideoUrl(mSteps[mIndex].getmVideoURL());
    }


    private void nextStep(){
        mIndex++;
        if(mIndex==mSteps.length-1){
            mBinding.btnNext.setEnabled(false);
        }
        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle(mSteps[mIndex].getmShortDescription());
        mBinding.btnBack.setEnabled(true);
        makeFragment();
    }

    private void previousStep(){
        mIndex--;
        if(mIndex==0){
            mBinding.btnBack.setEnabled(false);
        }
        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle(mSteps[mIndex].getmShortDescription());
        mBinding.btnNext.setEnabled(true);
            makeFragment();
    }

}
