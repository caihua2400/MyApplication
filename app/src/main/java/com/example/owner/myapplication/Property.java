package com.example.owner.myapplication;

/**
 * Created by Owner on 21/03/2018.
 */

public class Property {
    private int mPropertyID;
    private String mAdress;
    private int mPrice, mBedrooms;
    private String mPath;

    public Property() {

    }


    public int getmPropertyID() {
        return mPropertyID;
    }

    public String getmAdress() {
        return mAdress;
    }

    public int getmPrice() {
        return mPrice;
    }

    public int getmBedrooms() {
        return mBedrooms;
    }

    public void setmPropertyID(int mPropertyID) {
        this.mPropertyID = mPropertyID;
    }

    public void setmAdress(String mAdress) {
        this.mAdress = mAdress;
    }

    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public void setmBedrooms(int mBedrooms) {
        this.mBedrooms = mBedrooms;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }
}
