package com.example.shafy.bakingpal.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.shafy.bakingpal.databinding.FragmentStepsListBinding;
import com.example.shafy.bakingpal.model.Step;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by shafy on 22/11/2017.
 */

public class StepsListFragment extends Fragment{

    private Step[] mSteps;
    private StepsListAdapter.StepClickHandler mHandler;
    private StepsListAdapter mAdapter;

    public StepsListFragment() {
    }

    public void setSteps(Step[] mSteps) {
        this.mSteps = mSteps;
    }

    public void setHandler(StepsListAdapter.StepClickHandler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentStepsListBinding StepsListBinding = FragmentStepsListBinding.inflate(inflater,container,false);
        RecyclerView rv = StepsListBinding.rvStepsList;
        mAdapter = new StepsListAdapter(mSteps,mHandler);
        LinearLayoutManager manager =new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rv.setHasFixedSize(true);
        rv.setLayoutManager(manager);
        rv.setAdapter(mAdapter);
        return StepsListBinding.getRoot();
    }
}
