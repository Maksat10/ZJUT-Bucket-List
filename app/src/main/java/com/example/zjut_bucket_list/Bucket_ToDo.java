package com.example.zjut_bucket_list;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Bucket_ToDo {
    private static Bucket_ToDo sBucketToDo;
    private List<BucketList> mBucketList;

    public  static Bucket_ToDo get(Context context) {
        if(sBucketToDo == null) {
            sBucketToDo = new Bucket_ToDo(context);
        }
        return sBucketToDo;
    }

    public List<BucketList> getBucketList() {
        return mBucketList;
    }

    public BucketList getBucket(UUID id) {
        for(BucketList bucketList: mBucketList) {
            if(bucketList.getId().equals(id)) {
                return  bucketList;
            }
        }
        return  null;
    }

    public void deleteItem(UUID id) {
        for(BucketList bucketList: mBucketList) {
            if(bucketList.getId().equals(id)) {
                mBucketList.remove(bucketList);
                return;
            }
        }
    }

    private Bucket_ToDo(Context context) {
        mBucketList = new ArrayList<>();
        mBucketList.add(new BucketList("Android Class", "Show Project", 10.2, 24.1, new Date()));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, 01,19);
    }

}
