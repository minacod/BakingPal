package com.example.shafy.bakingpal.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shafy on 22/11/2017.
 */

public class Recipe implements Parcelable{
    int mId;
    String mName;
    Ingredient[] mIngredients;
    Step[] mSteps;
    int mServing;
    String mImage;

    public Recipe(int mId, String mName, Ingredient[] ingredients, Step[] steps, int mServing, String mImage) {
        this.mId = mId;
        this.mName = mName;
        this.mIngredients = ingredients;
        this.mSteps = steps;
        this.mServing = mServing;
        this.mImage = mImage;
    }

    public Recipe(Parcel source) {
        mId = source.readInt();
        mName = source.readString();
        mIngredients = source.createTypedArray(Ingredient.CREATOR);
        mSteps = source.createTypedArray(Step.CREATOR);
        mServing = source.readInt();
        mImage = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeTypedArray(mIngredients,0);
        dest.writeTypedArray(mSteps,0);
        dest.writeInt(mServing);
        dest.writeString(mImage);
    }
    public static final Parcelable.Creator<Recipe> CREATOR
            = new Parcelable.Creator<Recipe>(){
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Ingredient[] getmIngredients() {
        return mIngredients;
    }

    public void setmIngredients(Ingredient[] mIngredients) {
        this.mIngredients = mIngredients;
    }

    public Step[] getmSteps() {
        return mSteps;
    }

    public void setmSteps(Step[] mSteps) {
        this.mSteps = mSteps;
    }

    public int getmServing() {
        return mServing;
    }

    public void setmServing(int mServing) {
        this.mServing = mServing;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }


}
