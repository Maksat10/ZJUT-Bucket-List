package com.example.zjut_bucket_list;

import java.util.Date;
import java.util.UUID;

public class BucketList {
    private UUID mId;
    private String mName;
    private String mDescription;
    private double mLatitude;
    private double mLongitude;
    private Date mDate;
    private boolean mCompleted;

    public BucketList(String name, String description, double longitude, double latitude, Date date) {
        mId = UUID.randomUUID();
        mName = name;
        mDate = date;
        mDescription = description;
        mLatitude = latitude;
        mLongitude = longitude;
        mCompleted = false;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public UUID getId() {
        return mId;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public Date getDate() {
        return mDate;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean b) {
        mCompleted = b;
    }

    public void updateBucketList(BucketList bucketList) {
        mName = bucketList.getName();
        mDescription = bucketList.getDescription();
        mLatitude = bucketList.getLatitude();
        mLongitude = bucketList.getLongitude();
        mDate = bucketList.getDate();
    }
}
