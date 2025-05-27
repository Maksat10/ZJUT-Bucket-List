package com.example.zjut_bucket_list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class BucketListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new BucketListFragment();
    }
}