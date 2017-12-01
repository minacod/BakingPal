package com.example.shafy.bakingpal.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shafy on 22/11/2017.
 */

public class Step implements Parcelable{
    private int mId;
    private String mShortDescription;
    private String mDescription;
    private String mVideoURL;
    private String mThumbnailURL;

    public Step(int mId, String mShortDescription, String mDescription, String mVideoURL, String mThumbnailURL) {
        this.mId = mId;
        this.mShortDescription = mShortDescription;
        this.mDescription = mDescription;
        this.mVideoURL = mVideoURL;
        this.mThumbnailURL = mThumbnailURL;
    }

    public Step(Parcel source) {
        mId = source.readInt();
        mShortDescription = source.readString();
        mDescription = source.readString();
        mVideoURL = source.readString();
        mThumbnailURL = source.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoURL);
        dest.writeString(mThumbnailURL);
    }
    public static final Parcelable.Creator<Step> CREATOR
            = new Parcelable.Creator<Step>(){
        @Override
        public Step createFromParcel(Parcel source) {
            return new Step(source);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmShortDescription() {
        return mShortDescription;
    }

    public void setmShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmVideoURL() {
        return mVideoURL;
    }

    public void setmVideoURL(String mVideoURL) {
        this.mVideoURL = mVideoURL;
    }

    public String getmThumbnailURL() {
        return mThumbnailURL;
    }

    public void setmThumbnailURL(String mThumbnailURL) {
        this.mThumbnailURL = mThumbnailURL;
    }
}
