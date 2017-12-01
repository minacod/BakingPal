package com.example.shafy.bakingpal.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shafy on 22/11/2017.
 */

public class Ingredient implements Parcelable {
    int mQuantity;
    String mMeasure;
    String mIngredient;

    public Ingredient(int mQuantity, String mMeasure, String mIngredient) {
        this.mQuantity = mQuantity;
        this.mMeasure = mMeasure;
        this.mIngredient = mIngredient;
    }

    public Ingredient(Parcel source) {
        mQuantity = source.readInt();
        mMeasure = source.readString();
        mIngredient = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mQuantity);
        dest.writeString(mMeasure);
        dest.writeString(mIngredient);
    }
    public static final Parcelable.Creator<Ingredient> CREATOR
            = new Parcelable.Creator<Ingredient>(){
        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public int getmQuantity() {
        return mQuantity;
    }
    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getmMeasure() {
        return mMeasure;
    }

    public void setmMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public String getmIngredient() {
        return mIngredient;
    }

    public void setmIngredient(String mIngredient) {
        this.mIngredient = mIngredient;
    }

}
