package com.example.zjut_bucket_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;
import java.util.UUID;

public class BucketListPgActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<BucketList> mBucketList;

    public static Intent newIntent(Context packageContext, UUID id) {
        Intent intent = new Intent(packageContext,
                BucketListPgActivity.class);

        intent.putExtra("ITEM_ID", id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_list_pager);

        mViewPager = (ViewPager) findViewById(R.id.bucket_list_pager);
        mBucketList = Bucket_ToDo.get(this).getBucketList();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentStatePagerAdapter fragmentStatePagerAdapter = new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public int getCount() {
                return mBucketList.size();
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                UUID id = mBucketList.get(position).getId();
                EditItemActivity editItemActivity = EditItemActivity.newInstance(id);
                return editItemActivity;
            }
        };

        mViewPager.setAdapter(fragmentStatePagerAdapter);

        mViewPager.setOffscreenPageLimit(10);

        UUID id = (UUID) getIntent().getSerializableExtra("ITEM_ID");
        for(int i=0; i<mBucketList.size(); i++) {
            if(mBucketList.get(i).getId().equals(id)){
                mViewPager.setCurrentItem(i);
            }
        }
    }
}